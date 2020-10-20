package com.remisiewicz.csc.messages.outgoing;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static io.micronaut.http.HttpStatus.CREATED;

@Controller
class StationMessagesController {

    private static final Logger log = LoggerFactory.getLogger(StationMessagesController.class);

    private Map<String, String> repository = new ConcurrentHashMap<>();

    @Post("/chargingpoints/{chargingPointName}/actions/RemoteStartTransaction")
    @Status(CREATED)
    MessageId remoteStartTransaction(String chargingPointName, @Body RemoteStartTransactionRequest request) {
        log.info("remoteStartTransaction {}, {}", chargingPointName, request);
        String transaction = repository.get(chargingPointName);
        if (transaction != null) {
            throw new IllegalArgumentException("You can not start existing transaction again");
        }
        repository.put(chargingPointName, "transaction");
        return new MessageId(UUID.randomUUID().toString());
    }

    @Post("/chargingpoints/{chargingPointName}/actions/RemoteStopTransaction")
    @Status(CREATED)
    MessageId remoteStopTransaction(String chargingPointName, @Body RemoteStopTransactionRequest request) {
        log.info("remoteStopTransaction {}, {}", chargingPointName, request);
        String transaction = repository.get(chargingPointName);
        if (transaction == null) {
            throw new IllegalArgumentException("You can not stop non existing transaction");
        }
        return new MessageId(UUID.randomUUID().toString());
    }

    public void add(String chargingPointName) {
        repository.put(chargingPointName, "transaction");
    }
}
