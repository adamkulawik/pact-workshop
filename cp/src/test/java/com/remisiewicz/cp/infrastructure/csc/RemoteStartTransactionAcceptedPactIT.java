package com.remisiewicz.cp.infrastructure.csc;

import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.messaging.Message;
import au.com.dius.pact.core.model.messaging.MessagePact;
import com.remisiewicz.cp.infrastructure.IntegrationTestWithContainers;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.pactfoundation.consumer.dsl.LambdaDsl;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

import static org.awaitility.Awaitility.await;

@MicronautTest
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "pw-gr1-csc-asynch", providerType = ProviderType.ASYNCH)
class RemoteStartTransactionAcceptedPactIT implements IntegrationTestWithContainers {

    private static final String STATION_NAME = "CP-10a7664ecf7f";
    private static final String MESSAGE_ID = "9efef95e-11af-4317-8c7c-10a7664ecf7f";

    @Inject
    @KafkaClient("csc-producer")
    private Producer<String, Object> cscProducer;

    @Inject
    private StationResponsesListener listener;

    private final DslPart remoteStartTransactionResponseJsonBody = LambdaDsl.newJsonBody(
            body -> {
                body.stringValue("chargePoint", STATION_NAME);
                body.stringValue("messageId", MESSAGE_ID);
            }
    ).build();

    @Pact(consumer = "pw-gr1-cp-asynch")
    MessagePact remoteStartTransactionAcceptedPact(MessagePactBuilder builder) {
        return builder
                .given("remote start transaction accepted event")
                .expectsToReceive("remote start transaction accepted event")
                .withContent(remoteStartTransactionResponseJsonBody)
                .withMetadata(Map.of(
                        "kafka_messageKey", STATION_NAME,
                        "kafka_topic", "emobility-ocpp-responses"))
                .toPact();
    }

    @Test
    void shouldAcceptRemoteStartTransaction(List<Message> messages) {
        //when
        messages.forEach(message -> cscProducer.send(new ProducerRecord<>(
                (String) message.getMetaData().get("kafka_topic"),
                (String) message.getMetaData().get("kafka_messageKey"),
                message.contentsAsBytes()
        )));
        //then
        assertThatRemoteStartTransactionIsAccepted();
    }

    private void assertThatRemoteStartTransactionIsAccepted() {
        await().untilAsserted(() -> Assertions.assertThat(listener.isAccepted(MESSAGE_ID)).isTrue());
    }
}