package com.dev.pigeonproviderapp.chat;

public class ChatModel {

    private String senderUid;
    private String message;
    private String dateTime;
    private String read;

    public ChatModel() {
    }

    public ChatModel(String senderUid, String message, String dateTime, String read) {
        this.senderUid = senderUid;
        this.message = message;
        this.dateTime=dateTime;
        this.read=read;

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
    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

}
