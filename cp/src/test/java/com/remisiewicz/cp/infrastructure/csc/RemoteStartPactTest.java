package com.remisiewicz.cp.infrastructure.csc;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.core.model.RequestResponsePact;
import io.micronaut.http.client.netty.DefaultHttpClient;
import org.junit.jupiter.api.Test;

import static java.util.UUID.fromString;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;

class RemoteStartPactTest {

    private static final String STATION_NAME = "CS-c6be1cd5a384";
    private static final int CONNECTOR_ID = 1;
    private static final String ID_TAG = "c6be1cd5a384";

    RequestResponsePact remoteStartTransactionPact(PactDslWithProvider builder) {
        return null;
//        return builder
//                .uponReceiving("Remote start transaction");
    }

    @Test
    void shouldSendRemoteStartTransactionRequestToCsc() {
        //given
        CscRestClient cscRestClient = new CscRestClient(new DefaultHttpClient());
        //when
        String messageId = null;
        //then
        assertThatNoException().isThrownBy(() -> fromString(messageId));
    }
}
