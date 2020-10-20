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
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "pw-gr1-csc", port = "9006")
@MicronautTest
@Property(name = "csc.url", value = "http://localhost:9006")
class ClearCacheRequestPactTest {

    private static final String STATION_NAME = "CS-c6be1cd5a384";

    @Inject
    CscRestClient cscRestClient;

    @Pact(consumer = "pw-gr1-cp")
    RequestResponsePact clearCachePact(PactDslWithProvider builder) {
        return builder
                .uponReceiving("Clear cache")
                .path(format("/chargingpoints/%s/actions/%s", STATION_NAME, "ClearCache"))
                .method("POST")
                .body(new PactDslJsonBody().date("from", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Date.from(Instant.parse("2019-07-03T10:15:30.001Z"))))
                .willRespondWith()
                .status(201)
                .body(new PactDslJsonBody().stringValue("status", "Accepted"))
                .toPact();
    }

    @Test
    void shouldSendRemoteStopTransactionRequestToCsc() {
        //when
        String status = cscRestClient.clearCache(STATION_NAME);
        //then
        assertThat(status).isEqualTo("{\"status\":\"Accepted\"}");
    }
}
