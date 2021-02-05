package com.dev.pigeonproviderapp.chat;

public class ChatModel {

    private String senderUid;
    private String message;

    public ChatModel() {
    }

    public ChatModel(String senderUid, String message) {
        this.senderUid = senderUid;
        this.message = message;

    }

    public String getSenderUid() {
        return senderUid;
    }

    public void setSenderUid(String senderUid) {
        this.senderUid = senderUid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
