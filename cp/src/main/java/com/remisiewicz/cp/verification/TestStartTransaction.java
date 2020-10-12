package com.remisiewicz.cp.verification;

import com.remisiewicz.cp.infrastructure.csc.CscRestClient;
import io.micronaut.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.time.Instant;
import java.util.Date;

@Singleton
class TestStartTransaction {

    private static final Logger log = LoggerFactory.getLogger(TestStartTransaction.class);

    private final CscRestClient cscRestClient;

    TestStartTransaction(CscRestClient cscRestClient) {
        this.cscRestClient = cscRestClient;
    }

    @Scheduled(initialDelay = "3s")
    void test() {
        try {
            Instant when = Instant.now();
            String cpName = "cp name";
            int connectorId = 1;
            String idTag = "43242498";
            String messageId = cscRestClient.remoteStartTransaction(cpName, connectorId, idTag, Date.from(when));
            validate(messageId, cpName, connectorId, idTag, when);
            log.info("Congratulation, you are able to integrate systems without tests :D");
        } catch (Exception e) {
            log.error("You suck, please start to write CDC tests immediately!", e);
        }
        System.exit(0);
    }

    private void validate(String messageId, String cpName, int connectorId, String idTag, Instant when) {
        if (!messageId.equals(String.format("%s-%d-%s-%s", cpName, connectorId, idTag, when))) {
            throw new IllegalStateException();
        }
    }
}
