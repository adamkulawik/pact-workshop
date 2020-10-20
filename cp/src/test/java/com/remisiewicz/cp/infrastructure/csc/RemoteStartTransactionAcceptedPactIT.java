package com.remisiewicz.cp.infrastructure.csc;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.core.model.messaging.Message;
import com.remisiewicz.cp.infrastructure.IntegrationTestWithContainers;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.pactfoundation.consumer.dsl.LambdaDsl;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

@MicronautTest
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
                body.stringValue("action", "RemoteStartTransaction");
                body.stringValue("type", "ACCEPTED");
                body.stringValue("messageId", MESSAGE_ID);
                body.stringValue("payload", "[3,\"" + MESSAGE_ID + "\",{\"status\":\"Accepted\"}]");
            }
    ).build();

    @Test
    void shouldNotifyRoamingPartnerAboutResultOfRemoteStartTransaction() {
        //given
        List<Message> messages = null;
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
        Assertions.assertThat(listener.isAccepted(MESSAGE_ID)).isTrue();
    }
}