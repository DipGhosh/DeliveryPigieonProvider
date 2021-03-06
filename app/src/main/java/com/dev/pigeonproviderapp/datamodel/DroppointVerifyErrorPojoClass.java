package com.dev.pigeonproviderapp.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DroppointVerifyErrorPojoClass {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("errorMessage")
    @Expose
    private String errorMessage;
    @SerializedName("data")
    @Expose
    private Object data;
    @SerializedName("errors")
    @Expose
    private Errors errors;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    public class Errors {

        @SerializedName("order")
        @Expose
        private List<String> order = null;

        public List<String> getOrder() {
            return order;
        }

        public void setOrder(List<String> order) {
            this.order = order;
        }
    }
}
