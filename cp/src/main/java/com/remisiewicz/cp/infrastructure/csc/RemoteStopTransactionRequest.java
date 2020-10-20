package com.remisiewicz.cp.infrastructure.csc;

import java.util.StringJoiner;

public class RemoteStopTransactionRequest {

    private final int transactionId;

    public RemoteStopTransactionRequest(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RemoteStopTransactionRequest.class.getSimpleName() + "[", "]")
                .add("transactionId=" + transactionId)
                .toString();
    }
}