package models.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LabelListVisibility {
    LABEL_SHOW("labelShow"),
    LABEL_SHOW_IF_UNREAD("labelShowIfUnread"),
    LABEL_HIDE("labelHide");

    private final String value;

    LabelListVisibility(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}