package com.remisiewicz.cp.infrastructure.csc;

import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.messaging.annotation.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@KafkaListener(offsetReset = OffsetReset.EARLIEST)
public class StationResponsesListener {

    private final static Logger log = LoggerFactory.getLogger(StationResponsesListener.class);

    private final Map<String, String> responses = new ConcurrentHashMap<>();

    @Topic("emobility-ocpp-responses")
    public void handle(@Body Map<String, String> response) {
        responses.put(response.get("messageId"), "Accepted");
    }

    public boolean isAccepted(String messageId) {
        return "Accepted".equals(responses.get(messageId));
    }
}
