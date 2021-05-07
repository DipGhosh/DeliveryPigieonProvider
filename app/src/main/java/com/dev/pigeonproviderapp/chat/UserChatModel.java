package com.dev.pigeonproviderapp.chat;

public class UserChatModel {

  private String senderUid;
  private String message;
  private String dateTime;
  private String read;

  // user =1 admin =2
  private int Userchat;


  public UserChatModel(String senderUid, String message, int userchat, String dateTime, String read) {
    this.senderUid = senderUid;
    this.message = message;
    Userchat = userchat;
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

  public int getUserchat() {
    return Userchat;
  }

  public void setUserchat(int userchat) {
    Userchat = userchat;
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
