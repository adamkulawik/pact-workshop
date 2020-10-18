package com.remisiewicz.csc.messages.outgoing;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Controller
class StationMessagesController {

    private static final Logger log = LoggerFactory.getLogger(StationMessagesController.class);

    @Post("/chargingpoints/{chargingPointName}/actions/{action}")
    MessageId remoteStartTransaction(String chargingPointName, String action, @Body RemoteStartTransactionRequest request) {
        log.info("remoteStartTransaction {}, {}, {}", chargingPointName, action, request);
        return new MessageId(UUID.randomUUID().toString());
    }
}
