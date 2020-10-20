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
import java.time.Instant;
import java.util.Date;

import static java.lang.String.format;
import static java.util.UUID.fromString;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "pw-gr1-csc", port = "9004")
@MicronautTest
@Property(name = "csc.url", value = "http://localhost:9004")
class RemoteTransactionRequestsPactTest {

    private static final String STATION_NAME = "CS-c6be1cd5a384";
    private static final int CONNECTOR_ID = 1;
    private static final String ID_TAG = "c6be1cd5a384";
    private static final int TRANSACTION_ID = 2;

    @Inject
    CscRestClient cscRestClient;

    @Pact(consumer = "pw-gr1-cp")
    RequestResponsePact remoteStartTransactionPact(PactDslWithProvider builder) {
        return builder
                .uponReceiving("Remote start transaction")
                .path(format("/chargingpoints/%s/actions/%s", STATION_NAME, "RemoteStartTransaction"))
                .method("POST")
                .body(new PactDslJsonBody()
                        .numberValue("connectorId", CONNECTOR_ID)
                        .stringValue("idTag", ID_TAG)
                        .date("when", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Date.from(Instant.parse("2019-07-03T10:15:30.001Z")))
                )
                .willRespondWith()
                .status(201)
                .body(new PactDslJsonBody().uuid("messageId"))
                .toPact();
    }

    @Pact(consumer = "pw-gr1-cp")
    RequestResponsePact remoteStopTransactionPact(PactDslWithProvider builder) {
        return builder
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
    @PactTestFor(pactMethod = "remoteStartTransactionPact")
    void shouldSendRemoteStartTransactionRequestToCsc() {
        //when
        MessageId messageId = cscRestClient.remoteStartTransaction(STATION_NAME, CONNECTOR_ID, ID_TAG, Instant.now());
        //then
        assertThatNoException().isThrownBy(() -> fromString(messageId.getId()));
    }

    @Test
    @PactTestFor(pactMethod = "remoteStopTransactionPact")
    void shouldSendRemoteStopTransactionRequestToCsc() {
        //when
        MessageId messageId = cscRestClient.remoteStopTransaction(STATION_NAME, TRANSACTION_ID);
        //then
        assertThatNoException().isThrownBy(() -> fromString(messageId.getId()));
    }
}
