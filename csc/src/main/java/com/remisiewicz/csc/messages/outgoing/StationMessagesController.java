package com.remisiewicz.csc.messages.outgoing;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static io.micronaut.http.HttpStatus.CREATED;

@Controller
class StationMessagesController {

    private static final Logger log = LoggerFactory.getLogger(StationMessagesController.class);

    @Post("/chargingpoints/{chargingPointName}/actions/RemoteStartTransaction")
    @Status(CREATED)
    MessageId remoteStartTransaction(String chargingPointName, @Body RemoteStartTransactionRequest request) {
        log.info("remoteStartTransaction {}, {}", chargingPointName, request);
        return new MessageId(UUID.randomUUID().toString());
    }

    @Post("/chargingpoints/{chargingPointName}/actions/RemoteStopTransaction")
    @Status(CREATED)
    MessageId remoteStopTransaction(String chargingPointName, @Body RemoteStopTransactionRequest request) {
        log.info("remoteStopTransaction {}, {}", chargingPointName, request);
        return new MessageId(UUID.randomUUID().toString());
    }
}
