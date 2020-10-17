package com.remisiewicz.csc.messages.outgoing;

import java.time.Instant;

public class RemoteStartTransactionRequest {

    private final int connectorId;
    private final String rfid;
    private final Instant when;

    public RemoteStartTransactionRequest(int connectorId, String rfid, Instant when) {
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
}