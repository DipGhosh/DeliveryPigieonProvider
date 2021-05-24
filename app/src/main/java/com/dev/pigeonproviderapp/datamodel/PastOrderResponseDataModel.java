package com.dev.pigeonproviderapp.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PastOrderResponseDataModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("errorMessage")
    @Expose
    private Object errorMessage;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("errors")
    @Expose
    private List<Object> errors = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public List<Object> getErrors() {
        return errors;
    }

    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }



    public class Data {


        @SerializedName("past_new")
        @Expose
        private List<PastNew> pastNew = null;

        public List<PastNew> getPastNew() {
            return pastNew;
        }

        public void setPastNew(List<PastNew> pastNew) {
            this.pastNew = pastNew;
        }

    }

    public class PastNew {

        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("orders")
        @Expose
        private List<Order> orders = null;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<Order> getOrders() {
            return orders;
        }

        public void setOrders(List<Order> orders) {
            this.orders = orders;
        }

    }



    public class Order {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("order_no")
        @Expose
        private String orderNo;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("orderType")
        @Expose
        private Integer orderType;
        @SerializedName("pickupPoint")
        @Expose
        private String pickupPoint;
        @SerializedName("pickupTime")
        @Expose
        private String pickupTime;
        @SerializedName("pickupDate")
        @Expose
        private String pickupDate;
        @SerializedName("pickupDate_new")
        @Expose
        private String pickupDateNew;
        @SerializedName("dropPoint")
        @Expose
        private String dropPoint;
        @SerializedName("dropTime")
        @Expose
        private Object dropTime;
        @SerializedName("dropDate")
        @Expose
        private Object dropDate;
        @SerializedName("amount")
        @Expose
        private String amount;

        @SerializedName("provider_bonus")
        @Expose
        private Integer providerBonus;
        @SerializedName("provider_name")
        @Expose
        private String providerName;
        @SerializedName("provider_phone")
        @Expose
        private Long providerPhone;
        @SerializedName("provider_profile_picture")
        @Expose
        private String providerProfilePicture;
        @SerializedName("earn")
        @Expose
        private double earn;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getOrderType() {
            return orderType;
        }

        public void setOrderType(Integer orderType) {
            this.orderType = orderType;
        }

        public String getPickupPoint() {
            return pickupPoint;
        }

        public void setPickupPoint(String pickupPoint) {
            this.pickupPoint = pickupPoint;
        }

        public String getPickupTime() {
            return pickupTime;
        }

        public void setPickupTime(String pickupTime) {
            this.pickupTime = pickupTime;
        }

        public String getPickupDate() {
            return pickupDate;
        }

        public void setPickupDate(String pickupDate) {
            this.pickupDate = pickupDate;
        }

        public String getPickupDateNew() {
            return pickupDateNew;
        }

        public void setPickupDateNew(String pickupDateNew) {
            this.pickupDateNew = pickupDateNew;
        }

        public String getDropPoint() {
            return dropPoint;
        }

        public void setDropPoint(String dropPoint) {
            this.dropPoint = dropPoint;
        }

        public Object getDropTime() {
            return dropTime;
        }

        public void setDropTime(Object dropTime) {
            this.dropTime = dropTime;
        }

        public Object getDropDate() {
            return dropDate;
        }

        public void setDropDate(Object dropDate) {
            this.dropDate = dropDate;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }


        public Integer getProviderBonus() {
            return providerBonus;
        }

        public void setProviderBonus(Integer providerBonus) {
            this.providerBonus = providerBonus;
        }

        public String getProviderName() {
            return providerName;
        }

        public void setProviderName(String providerName) {
            this.providerName = providerName;
        }

        public Long getProviderPhone() {
            return providerPhone;
        }

        public void setProviderPhone(Long providerPhone) {
            this.providerPhone = providerPhone;
        }

        public String getProviderProfilePicture() {
            return providerProfilePicture;
        }

        public void setProviderProfilePicture(String providerProfilePicture) {
            this.providerProfilePicture = providerProfilePicture;
        }

        public double getEarn() {
            return earn;
        }

        public void setEarn(double earn) {
            this.earn = earn;
        }

    }

}
