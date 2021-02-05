package com.dev.pigeonproviderapp.httpRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderItemOTPVerifyModel {

    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("file_name")
    @Expose
    private String signature;
    @SerializedName("type")
    @Expose
    private String type;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
