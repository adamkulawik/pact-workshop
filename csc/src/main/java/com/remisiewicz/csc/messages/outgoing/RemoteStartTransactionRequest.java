package com.remisiewicz.csc.messages.outgoing;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.StringJoiner;

public class RemoteStartTransactionRequest {

    private final int connectorId;
    private final String rfid;
    private final Instant when;

    @JsonCreator
    public RemoteStartTransactionRequest(
            @JsonProperty("connectorId") int connectorId,
            @JsonProperty("rfid") String rfid,
            @JsonProperty("when") Instant when) {
        this.connectorId = connectorId;
        this.rfid = rfid;
        this.when = when;
    }

    public int getConnectorId() {
        return connectorId;
    }

    public String getRfid() {
        return rfid;
    }

    public Instant getWhen() {
        return when;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RemoteStartTransactionRequest.class.getSimpleName() + "[", "]")
                .add("connectorId=" + connectorId)
                .add("rfid='" + rfid + "'")
                .add("when=" + when)
                .toString();
    }
}