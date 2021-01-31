package com.dev.pigeonproviderapp.httpRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyOtpAPIModel {
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("device_name")
    @Expose
    private String deviceName;
    @SerializedName("otp")
    @Expose
    private String otp;

    @SerializedName("device_token")
    @Expose
    private String devicetoken;
    @SerializedName("device_type")
    @Expose
    private String devicetype;
    @SerializedName("user_type")
    @Expose
    private int usertype;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getDevicetoken() {
        return devicetoken;
    }

    public void setDevicetoken(String devicetoken) {
        this.devicetoken = devicetoken;
    }

    public String getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }

    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }


}
