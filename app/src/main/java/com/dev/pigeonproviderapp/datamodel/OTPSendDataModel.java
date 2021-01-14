package com.dev.pigeonproviderapp.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class OTPSendDataModel {

  @SerializedName("status")
  @Expose
  private Integer status;
  @SerializedName("errorMessage")
  @Expose
  private Object errorMessage;
  @SerializedName("data")
  @Expose
  private Integer data;
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

  public Integer getData() {
    return data;
  }

  public void setData(Integer data) {
    this.data = data;
  }

  public List<Object> getErrors() {
    return errors;
  }

  public void setErrors(List<Object> errors) {
    this.errors = errors;
  }

}
