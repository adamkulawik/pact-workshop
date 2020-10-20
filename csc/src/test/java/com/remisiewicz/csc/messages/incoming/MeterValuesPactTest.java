package com.remisiewicz.csc.messages.incoming;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.micronaut.context.annotation.Property;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.pactfoundation.consumer.dsl.LambdaDsl;
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
                .body(LambdaDsl.newJsonBody(body -> {
                    body.numberValue("connectorId", 1);
                    body.numberValue("transactionId", 43958743);
                    body.eachLike("meterValue", meterValue -> {
                        meterValue.date("timestamp", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Date.from(Instant.parse("2019-07-03T10:15:30.001Z")));
                        meterValue.eachLike("sampledValue", sampledValue -> {
                            sampledValue.stringValue("value", "103");
                            sampledValue.stringValue("context", "Sample.Periodic");
                            sampledValue.stringValue("format", "Raw");
                            sampledValue.stringValue("measurand", "Energy.Active.Import.Register");
                            sampledValue.stringValue("phase", "L1");
                            sampledValue.stringValue("location", "Outlet");
                            sampledValue.stringValue("unit", "Wh");
                        });
                    });
                }).build())
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