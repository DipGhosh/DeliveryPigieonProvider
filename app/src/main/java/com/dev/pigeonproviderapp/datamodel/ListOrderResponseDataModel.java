package com.dev.pigeonproviderapp.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListOrderResponseDataModel {

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

    public class Available {

        @SerializedName("id")
        @Expose
        private Integer id;
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
        @SerializedName("dropPoint")
        @Expose
        private String dropPoint;
        @SerializedName("dropTime")
        @Expose
        private String dropTime;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("earn")
        @Expose
        private Integer earn;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
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

        public String getDropPoint() {
            return dropPoint;
        }

        public void setDropPoint(String dropPoint) {
            this.dropPoint = dropPoint;
        }

        public String getDropTime() {
            return dropTime;
        }

        public void setDropTime(String dropTime) {
            this.dropTime = dropTime;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public Integer getEarn() {
            return earn;
        }

        public void setEarn(Integer earn) {
            this.earn = earn;
        }

    }

    public class Current {

        @SerializedName("id")
        @Expose
        private Integer id;
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
        @SerializedName("dropPoint")
        @Expose
        private String dropPoint;
        @SerializedName("dropTime")
        @Expose
        private String dropTime;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("earn")
        @Expose
        private Integer earn;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
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

        public String getDropPoint() {
            return dropPoint;
        }

        public void setDropPoint(String dropPoint) {
            this.dropPoint = dropPoint;
        }

        public String getDropTime() {
            return dropTime;
        }

        public void setDropTime(String dropTime) {
            this.dropTime = dropTime;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public Integer getEarn() {
            return earn;
        }

        public void setEarn(Integer earn) {
            this.earn = earn;
        }

    }

    public class Data {

        @SerializedName("available")
        @Expose
        private List<Available> available = null;
        @SerializedName("current")
        @Expose
        private List<Current> current = null;
        @SerializedName("past")
        @Expose
        private List<Object> past = null;

        public List<Available> getAvailable() {
            return available;
        }

        public void setAvailable(List<Available> available) {
            this.available = available;
        }

        public List<Current> getCurrent() {
            return current;
        }

        public void setCurrent(List<Current> current) {
            this.current = current;
        }

        public List<Object> getPast() {
            return past;
        }

        public void setPast(List<Object> past) {
            this.past = past;
        }

    }

}
