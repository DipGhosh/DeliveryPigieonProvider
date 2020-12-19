package com.dev.pigeonproviderapp.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class RegisterDataModel {

  @SerializedName("status")
  @Expose
  private Integer status;
  @SerializedName("errorMessage")
  @Expose
  private String errorMessage;
  @SerializedName("data")
  @Expose
  private Object data;
  @SerializedName("message")
  @Expose
  private String message;
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

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Errors getErrors() {
    return errors;
  }

  public void setErrors(Errors errors) {
    this.errors = errors;
  }

  public class Errors {

    @SerializedName("name")
    @Expose
    private List<String> name = null;
    @SerializedName("email")
    @Expose
    private List<String> email = null;
    @SerializedName("phone")
    @Expose
    private List<String> phone = null;
    @SerializedName("user_type")
    @Expose
    private List<String> userType = null;

    public List<String> getName() {
      return name;
    }

    public void setName(List<String> name) {
      this.name = name;
    }

    public List<String> getEmail() {
      return email;
    }

    public void setEmail(List<String> email) {
      this.email = email;
    }

    public List<String> getPhone() {
      return phone;
    }

    public void setPhone(List<String> phone) {
      this.phone = phone;
    }

    public List<String> getUserType() {
      return userType;
    }

    public void setUserType(List<String> userType) {
      this.userType = userType;
    }

  }


 /* @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("email")
  @Expose
  private String email;
  @SerializedName("phone")
  @Expose
  private String phone;
  @SerializedName("user_type")
  @Expose
  private Integer userType;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
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
  }*/

}
