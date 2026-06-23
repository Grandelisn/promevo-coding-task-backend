package com.example.promevocodingtaskbackend.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.promevocodingtaskbackend.models.enums.LabelListVisibility;
import com.example.promevocodingtaskbackend.models.enums.MessageListVisibility;
import com.example.promevocodingtaskbackend.models.enums.Type;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LabelDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("messageListVisibility")
    private MessageListVisibility messageListVisibility;

    @JsonProperty("labelListVisibility")
    private LabelListVisibility labelListVisibility;

    @JsonProperty("type")
    private Type type;

    @JsonProperty("messagesTotal")
    private Integer messagesTotal;

    @JsonProperty("messagesUnread")
    private Integer messagesUnread;

    @JsonProperty("threadsTotal")
    private Integer threadsTotal;

    @JsonProperty("threadsUnread")
    private Integer threadsUnread;

    @JsonProperty("color")
    private ColorDTO color;

    public LabelDTO() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MessageListVisibility getMessageListVisibility() {
        return messageListVisibility;
    }

    public void setMessageListVisibility(MessageListVisibility messageListVisibility) {
        this.messageListVisibility = messageListVisibility;
    }

    public LabelListVisibility getLabelListVisibility() {
        return labelListVisibility;
    }

    public void setLabelListVisibility(LabelListVisibility labelListVisibility) {
        this.labelListVisibility = labelListVisibility;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Integer getMessagesTotal() {
        return messagesTotal;
    }

    public void setMessagesTotal(Integer messagesTotal) {
        this.messagesTotal = messagesTotal;
    }

    public Integer getMessagesUnread() {
        return messagesUnread;
    }

    public void setMessagesUnread(Integer messagesUnread) {
        this.messagesUnread = messagesUnread;
    }

    public Integer getThreadsTotal() {
        return threadsTotal;
    }

    public void setThreadsTotal(Integer threadsTotal) {
        this.threadsTotal = threadsTotal;
    }

    public Integer getThreadsUnread() {
        return threadsUnread;
    }

    public void setThreadsUnread(Integer threadsUnread) {
        this.threadsUnread = threadsUnread;
    }

    public ColorDTO getColor() {
        return color;
    }

    public void setColor(ColorDTO color) {
        this.color = color;
    }
}
