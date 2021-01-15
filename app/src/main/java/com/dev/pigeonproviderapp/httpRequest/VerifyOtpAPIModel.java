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


}
