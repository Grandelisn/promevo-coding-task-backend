package com.example.promevocodingtaskbackend.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MessageListVisibility {
    SHOW("show"),
    HIDE("hide");

    private final String value;

    MessageListVisibility(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}