package com.remisiewicz.csc.messages.outgoing;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.StringJoiner;

public class RemoteStartTransactionRequest {

    private final Integer connectorId;
    private final String idTag;
    private final Instant when;

    @JsonCreator
    public RemoteStartTransactionRequest(
            @JsonProperty("connectorId") Integer connectorId,
            @JsonProperty("idTag") String idTag,
            @JsonProperty("when") Instant when) {
        this.connectorId = connectorId;
        this.idTag = idTag;
        this.when = when;
    }

    public Integer getConnectorId() {
        return connectorId;
    }

    public String getIdTag() {
        return idTag;
    }

    public Instant getWhen() {
        return when;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RemoteStartTransactionRequest.class.getSimpleName() + "[", "]")
                .add("connectorId=" + connectorId)
                .add("idTag='" + idTag + "'")
                .add("when=" + when)
                .toString();
    }
}