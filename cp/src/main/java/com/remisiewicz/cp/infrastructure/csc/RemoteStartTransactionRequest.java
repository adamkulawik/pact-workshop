package com.remisiewicz.cp.infrastructure.csc;

import java.time.Instant;

public class RemoteStartTransactionRequest {

    private final int connectorId;
    private final String idTag;
    private final Instant when;

    public RemoteStartTransactionRequest(int connectorId, String idTag, Instant when) {
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