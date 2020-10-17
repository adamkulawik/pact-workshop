package com.remisiewicz.csc.messages.outgoing;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.StringJoiner;

public class MessageId {

    private final String id;

    @JsonCreator
    public MessageId(@JsonProperty("id") String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MessageId.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .toString();
    }
}