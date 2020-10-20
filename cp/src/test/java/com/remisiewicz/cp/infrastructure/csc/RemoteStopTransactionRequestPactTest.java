package com.remisiewicz.cp.infrastructure.csc;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.micronaut.context.annotation.Property;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;

import static java.lang.String.format;
import static java.util.UUID.fromString;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "pw-gr1-csc", port = "9005")
@MicronautTest
@Property(name = "csc.url", value = "http://localhost:9005")
class RemoteStopTransactionRequestPactTest {

    private static final String STATION_NAME = "CS-c6be1cd5a384";
    private static final int TRANSACTION_ID = 2;

    @Inject
    CscRestClient cscRestClient;

    @Pact(consumer = "pw-gr1-cp")
    RequestResponsePact remoteStopTransactionPact(PactDslWithProvider builder) {
        return builder
                .given("Remote stop transaction")
                .uponReceiving("Remote stop transaction")
                .path(format("/chargingpoints/%s/actions/%s", STATION_NAME, "RemoteStopTransaction"))
                .method("POST")
                .body(new PactDslJsonBody()
                        .numberValue("transactionId", TRANSACTION_ID)
                )
                .willRespondWith()
                .status(201)
                .body(new PactDslJsonBody().uuid("messageId"))
                .toPact();
    }

    @Test
    void shouldSendRemoteStopTransactionRequestToCsc() {
        //when
        MessageId messageId = cscRestClient.remoteStopTransaction(STATION_NAME, TRANSACTION_ID);
        //then
        assertThatNoException().isThrownBy(() -> fromString(messageId.getId()));
    }
}
