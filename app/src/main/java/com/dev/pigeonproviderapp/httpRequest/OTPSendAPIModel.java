package com.dev.pigeonproviderapp.httpRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OTPSendAPIModel {

  @SerializedName("device_name")
  @Expose
  private String deviceName;
  @SerializedName("phone")
  @Expose
  private String phone;
  @SerializedName("user_type")
  @Expose
  private Integer userType;

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Integer getUserType() {
    return userType;
  }

  public void setUserType(Integer userType) {
    this.userType = userType;
  }
}
