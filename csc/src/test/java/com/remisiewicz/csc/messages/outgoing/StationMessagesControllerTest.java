package com.remisiewicz.csc.messages.outgoing;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.net.URI;
import java.time.Instant;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class StationMessagesControllerTest {

    private final String chargingPointName = "cp-name";
    private final Integer connectorId = 1;
    private final String rfid = "3243243287";
    private final Instant when = Instant.now();

    @Inject
    @Client("/")
    private HttpClient httpClient;

    @Test
    void shouldHandleRemoteStartTransactionMessage() {
        //when
        HttpResponse<MessageId> response = httpClient.toBlocking().exchange(remoteStartTransactionRequest(), MessageId.class);
        //then
        assertThat(response.getBody()).isPresent();
        assertThat(response.getBody().get().getId()).isNotBlank();
    }

    private MutableHttpRequest<RemoteStartTransactionRequest> remoteStartTransactionRequest() {
        URI uri = UriBuilder.of("/chargingpoints/{chargingPointName}/actions/{action}").expand(Map.of(
                "chargingPointName", chargingPointName,
                "action", "RemoteStartTransaction"));
        return HttpRequest.POST(uri, new RemoteStartTransactionRequest(connectorId, rfid, when));
    }
}