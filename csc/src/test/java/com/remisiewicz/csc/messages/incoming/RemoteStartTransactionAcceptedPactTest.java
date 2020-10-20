package com.remisiewicz.csc.messages.incoming;

import au.com.dius.pact.provider.MessageAndMetadata;
import au.com.dius.pact.provider.PactVerifyProvider;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.loader.PactFilter;
import au.com.dius.pact.provider.junit5.AmpqTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@MicronautTest
@Provider("pw-gr1-csc-asynch")
@PactBroker(scheme = "https", host = "emob-pact.azurewebsites.net")
@PactFilter("remote start transaction accepted event")
public class RemoteStartTransactionAcceptedPactTest {

    private static final String STATION_NAME = "CP-10a7664ecf7f";
    private static final String MESSAGE_ID = "9efef95e-11af-4317-8c7c-10a7664ecf7f";

    @Inject
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUpTarget(PactVerificationContext context) {
        context.setTarget(new AmpqTestTarget(List.of("com.remisiewicz.csc.messages.incoming")));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("remote start transaction accepted event")
    public void remoteStartTransactionAccepted() {
    }

    @PactVerifyProvider("remote start transaction accepted event")
    public MessageAndMetadata assertThatRemoteStartTransactionAcceptedEventIsSent() throws JsonProcessingException {
        Map<String, String> message = Map.of(
                "chargePoint", STATION_NAME,
                "messageId", MESSAGE_ID
        );
        return new MessageAndMetadata(
                objectMapper.writeValueAsBytes(message),
                Map.of(
                        "kafka_messageKey", STATION_NAME,
                        "kafka_topic", "emobility-ocpp-responses"
                )
        );
    }
}
