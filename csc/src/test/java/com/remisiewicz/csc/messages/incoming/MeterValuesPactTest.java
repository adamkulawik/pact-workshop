package com.remisiewicz.csc.messages.incoming;

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

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "pw-gr1-cp", port = "9007")
@MicronautTest
@Property(name = "cp.url", value = "http://localhost:9007")
class MeterValuesPactTest {

    private static final String CHARGE_POINT_NAME = "charge-point-name";

    @Inject
    private CpRestClient cpRestClient;

    @Pact(consumer = "pw-gr-1-csc")
    RequestResponsePact meterValuesMessagePact(PactDslWithProvider builder) {
        return builder
                .given("meter values message")
                .uponReceiving("meter values message")
                .path(format("/cp/%s/transactions", CHARGE_POINT_NAME))
                .headers("Content-Type", "application/json")
                .method("PUT")
                .body(new PactDslJsonBody()
                        .integerType("connectorId", 1)
                        .integerType("transactionId", 43958743)
                        .eachLike("meterValue")
                            .date("timestamp", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Date.from(Instant.parse("2019-07-03T10:15:30.001Z")))
                            .eachLike("sampledValue")
                                .stringValue("value", "103")
                                .stringValue("context", "Sample.Periodic")
                                .stringValue("format", "Raw")
                                .stringValue("measurand", "Energy.Active.Import.Register")
                                .stringValue("phase", "L1")
                                .stringValue("location", "Outlet")
                                .stringValue("unit", "Wh")
                            .closeArray()
                        .closeArray())
                .willRespondWith()
                .status(200)
                .body(new PactDslJsonBody().asBody())
                .toPact();
    }

    @Test
    void shouldSendMeterValuesToCp() {
        //when
        cpRestClient.meterValues(CHARGE_POINT_NAME);
    }
}