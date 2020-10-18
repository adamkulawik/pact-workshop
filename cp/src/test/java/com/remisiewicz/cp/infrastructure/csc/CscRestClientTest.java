package com.remisiewicz.cp.infrastructure.csc;

import io.micronaut.http.client.netty.DefaultHttpClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.StringJoiner;

import static org.assertj.core.api.Assertions.assertThat;

class CscRestClientTest {

    private final String chargingPointName = "cp-name";
    private final Integer connectorId = 1;
    private final String idTag = "3243243287";
    private final Instant when = Instant.now();

    private CscRestClient cscRestClient;
    private CscStub cscStub;

    @BeforeEach
    void setUp() throws MalformedURLException {
        cscRestClient = new CscRestClient(new DefaultHttpClient(new URL("http://localhost:9003")));
        cscStub = new CscTestConfig().cscStub();
        cscStub.start();
    }

    @AfterEach
    void tearDown() {
        cscStub.stop();
    }

    @Test
    void shouldSendRemoteStartToCsc() {
        //given
        String expectedMessageId = new StringJoiner("-")
                .add(chargingPointName)
                .add(String.valueOf(connectorId))
                .add(idTag)
                .add(when.toString()).toString();
        cscStub.stubRemoteStartTransaction(chargingPointName, expectedMessageId);
        //when
        MessageId messageId = cscRestClient.remoteStartTransaction(chargingPointName, connectorId, idTag, when);
        //then
        assertThat(messageId.getId()).isEqualTo(expectedMessageId);
    }
}