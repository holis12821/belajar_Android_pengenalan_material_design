package com.example.belajar_android_pengenalan_material_design.model;

public class Chats {
    private String sender;
    private String message;
    private String timestamp;
    private String receiver;
    private boolean isseen;
    private String url;
    private String messageId;

    public Chats() {
    }

    public Chats(String sender, String message, String timestamp, String receiver, boolean isseen, String url, String messageId) {
        this.sender = sender;
        this.message = message;
        this.timestamp = timestamp;
        this.receiver = receiver;
        this.isseen = isseen;
        this.url = url;
        this.messageId = messageId;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getReceiver() {
        return receiver;
    }

    public boolean isIsseen() {
        return isseen;
    }

    public String getUrl() {
        return url;
    }

    public String getMessageId() {
        return messageId;
    }
}
