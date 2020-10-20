package com.remisiewicz.cp.infrastructure;

import org.testcontainers.containers.KafkaContainer;

enum KafkaContainerInstance {

    INSTANCE;

    private final KafkaContainer kafkaContainer = new KafkaContainer();

    KafkaContainerInstance() {
        kafkaContainer.start();
    }

    String getBootstrapServers() {
        return kafkaContainer.getBootstrapServers();
    }
}
