package com.dev.pigeonproviderapp.httpRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AcceptPaymentAPIModel {

    @SerializedName("order_id")
    @Expose
    private int orderid;

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }


}
