package com.remisiewicz.csc.messages.outgoing;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class RemoteStartTransactionRequest {

    private final int connectorId;
    private final String idTag;
    private final Instant when;

    @JsonCreator
    public RemoteStartTransactionRequest(@JsonProperty("connectorId") int connectorId, @JsonProperty("idTag") String idTag, @JsonProperty("when") Instant when) {
        this.connectorId = connectorId;
        this.idTag = idTag;
        this.when = when;
    }

    public int getConnectorId() {
        return connectorId;
    }

    public String getIdTag() {
        return idTag;
    }

    public Instant getWhen() {
        return when;
    }
}