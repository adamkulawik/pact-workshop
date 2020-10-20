package com.remisiewicz.cp.infrastructure;

import io.micronaut.test.support.TestPropertyProvider;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.TestInstance;

import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public interface IntegrationTestWithContainers extends TestPropertyProvider {

    @Override
    default @NotNull Map<String, String> getProperties() {
        return Map.of(
                "kafka.bootstrap.servers", KafkaContainerInstance.INSTANCE.getBootstrapServers()
        );
    }
}
