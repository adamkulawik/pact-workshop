package com.remisiewicz.csc.messages.outgoing;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.StringJoiner;

public class RemoteStopTransactionRequest {

    private final int transactionId;

    @JsonCreator
    public RemoteStopTransactionRequest(@JsonProperty("transactionId") int transactionId) {
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