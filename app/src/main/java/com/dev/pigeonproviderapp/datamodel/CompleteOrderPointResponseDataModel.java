package com.dev.pigeonproviderapp.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CompleteOrderPointResponseDataModel {

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

        @SerializedName("isAllDropPointsCompleted")
        @Expose
        private Boolean isAllDropPointsCompleted;

        public Boolean getIsAllDropPointsCompleted() {
            return isAllDropPointsCompleted;
        }

        public void setIsAllDropPointsCompleted(Boolean isAllDropPointsCompleted) {
            this.isAllDropPointsCompleted = isAllDropPointsCompleted;
        }

    }
}
