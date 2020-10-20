package com.remisiewicz.csc.messages.incoming;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.messaging.annotation.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@KafkaListener(offsetReset = OffsetReset.EARLIEST)
public class TestStationResponsesListener {

    private static final Logger log = LoggerFactory.getLogger(TestStationResponsesListener.class);

    private final Map<String, Map<String, String>> messages = new ConcurrentHashMap<>();

    @Topic("emobility-ocpp-responses")
    void handle(@KafkaKey String stationName, @Body Map<String, String> message) {
        log.info("Received message {}: {}", stationName, message);
        messages.put(stationName, message);
    }

    public Map<String, String> get(String stationName) {
        return messages.getOrDefault(stationName, Map.of());
    }
}
