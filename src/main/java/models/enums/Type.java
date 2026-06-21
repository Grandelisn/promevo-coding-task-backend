package models.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Type {
    SYSTEM("system"),
    USER("user");

    private final String value;

    Type(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
