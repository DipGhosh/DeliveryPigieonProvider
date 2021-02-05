package com.dev.pigeonproviderapp.chat;

public class UserChatModel {

  private String senderUid;
  private String message;

  // user =1 admin =2
  private int Userchat;


  public UserChatModel(String senderUid, String message, int userchat) {
    this.senderUid = senderUid;
    this.message = message;
    Userchat = userchat;
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
}
