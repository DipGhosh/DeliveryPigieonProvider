package com.dev.pigeonproviderapp.datamodel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class OrderDetailsResponseDatamodel {

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

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("orderType")
    @Expose
    private Integer orderType;
    @SerializedName("packageTypes")
    @Expose
    private Object packageTypes;
    @SerializedName("instruction")
    @Expose
    private Object instruction;
    @SerializedName("rating")
    @Expose
    private Rating rating;
    @SerializedName("orderStatus")
    @Expose
    private OrderStatus orderStatus;
    @SerializedName("weight")
    @Expose
    private Float weight;
    @SerializedName("provider")
    @Expose
    private Provider provider;
    @SerializedName("payment")
    @Expose
    private Payment payment;
    @SerializedName("pickupPoint")
    @Expose
    private PickupPoint pickupPoint;
    @SerializedName("dropPoints")
    @Expose
    private List<DropPoint> dropPoints = null;
    @SerializedName("amountEarn")
    @Expose
    private Integer amountEarn;

    public Integer getId() {
      return id;
    }

    public void setId(Integer id) {
      this.id = id;
    }

    public Integer getOrderType() {
      return orderType;
    }

    public void setOrderType(Integer orderType) {
      this.orderType = orderType;
    }

    public Object getPackageTypes() {
      return packageTypes;
    }

    public void setPackageTypes(Object packageTypes) {
      this.packageTypes = packageTypes;
    }

    public Object getInstruction() {
      return instruction;
    }

    public void setInstruction(Object instruction) {
      this.instruction = instruction;
    }

    public Rating getRating() {
      return rating;
    }

    public void setRating(Rating rating) {
      this.rating = rating;
    }

    public OrderStatus getOrderStatus() {
      return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
      this.orderStatus = orderStatus;
    }

    public Float getWeight() {
      return weight;
    }

    public void setWeight(Float weight) {
      this.weight = weight;
    }

    public Provider getProvider() {
      return provider;
    }

    public void setProvider(Provider provider) {
      this.provider = provider;
    }

    public Payment getPayment() {
      return payment;
    }

    public void setPayment(Payment payment) {
      this.payment = payment;
    }

    public PickupPoint getPickupPoint() {
      return pickupPoint;
    }

    public void setPickupPoint(PickupPoint pickupPoint) {
      this.pickupPoint = pickupPoint;
    }

    public List<DropPoint> getDropPoints() {
      return dropPoints;
    }

    public void setDropPoints(List<DropPoint> dropPoints) {
      this.dropPoints = dropPoints;
    }

    public Integer getAmountEarn() {
      return amountEarn;
    }

    public void setAmountEarn(Integer amountEarn) {
      this.amountEarn = amountEarn;
    }

  }


  public class DropAddress {

    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("long")
    @Expose
    private Double _long;
    @SerializedName("lat")
    @Expose
    private Double lat;

    public String getAddress() {
      return address;
    }

    public void setAddress(String address) {
      this.address = address;
    }

    public Double getLong() {
      return _long;
    }

    public void setLong(Double _long) {
      this._long = _long;
    }

    public Double getLat() {
      return lat;
    }

    public void setLat(Double lat) {
      this.lat = lat;
    }

  }


  public class DropPoint {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("dropAddress")
    @Expose
    private DropAddress dropAddress;
    @SerializedName("dropTime")
    @Expose
    private String dropTime;
    @SerializedName("dropDate")
    @Expose
    private String dropDate;
    @SerializedName("comments")
    @Expose
    private String comments;
    @SerializedName("providerComments")
    @Expose
    private Object providerComments;
    @SerializedName("signatureImageUrl")
    @Expose
    private Object signatureImageUrl;
    @SerializedName("orderStatus")
    @Expose
    private OrderStatus__ orderStatus;
    @SerializedName("phone")
    @Expose
    private long phone;

    public Integer getId() {
      return id;
    }

    public void setId(Integer id) {
      this.id = id;
    }

    public DropAddress getDropAddress() {
      return dropAddress;
    }

    public void setDropAddress(DropAddress dropAddress) {
      this.dropAddress = dropAddress;
    }

    public String getDropTime() {
      return dropTime;
    }

    public void setDropTime(String dropTime) {
      this.dropTime = dropTime;
    }

    public String getDropDate() {
      return dropDate;
    }

    public void setDropDate(String dropDate) {
      this.dropDate = dropDate;
    }

    public String getComments() {
      return comments;
    }

    public void setComments(String comments) {
      this.comments = comments;
    }

    public Object getProviderComments() {
      return providerComments;
    }

    public void setProviderComments(Object providerComments) {
      this.providerComments = providerComments;
    }

    public Object getSignatureImageUrl() {
      return signatureImageUrl;
    }

    public void setSignatureImageUrl(Object signatureImageUrl) {
      this.signatureImageUrl = signatureImageUrl;
    }

    public OrderStatus__ getOrderStatus() {
      return orderStatus;
    }

    public void setOrderStatus(OrderStatus__ orderStatus) {
      this.orderStatus = orderStatus;
    }

    public long getPhone() {
      return phone;
    }

    public void setPhone(long phone) {
      this.phone = phone;
    }

  }


  public class OrderStatus {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
      return status;
    }

    public void setStatus(Integer status) {
      this.status = status;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

  }


  public class OrderStatus_ {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
      return status;
    }

    public void setStatus(Integer status) {
      this.status = status;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

  }


  public class OrderStatus__ {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
      return status;
    }

    public void setStatus(Integer status) {
      this.status = status;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

  }

  public class Payment {

    @SerializedName("amount")
    @Expose
    private Float amount;
    @SerializedName("mode")
    @Expose
    private Object mode;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("message")
    @Expose
    private String message;

    public Float getAmount() {
      return amount;
    }

    public void setAmount(Float amount) {
      this.amount = amount;
    }

    public Object getMode() {
      return mode;
    }

    public void setMode(Object mode) {
      this.mode = mode;
    }

    public int getStatus() {
      return status;
    }

    public void setStatus(int status) {
      this.status = status;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

  }


  public class PickupAddress {

    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("long")
    @Expose
    private Float _long;
    @SerializedName("lat")
    @Expose
    private Float lat;

    public String getAddress() {
      return address;
    }

    public void setAddress(String address) {
      this.address = address;
    }

    public Float getLong() {
      return _long;
    }

    public void setLong(Float _long) {
      this._long = _long;
    }

    public Float getLat() {
      return lat;
    }

    public void setLat(Float lat) {
      this.lat = lat;
    }

  }

  public class PickupPoint {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("pickupAddress")
    @Expose
    private PickupAddress pickupAddress;
    @SerializedName("pickupTime")
    @Expose
    private String pickupTime;
    @SerializedName("pickupDate")
    @Expose
    private String pickupDate;
    @SerializedName("comments")
    @Expose
    private String comments;
    @SerializedName("providerComments")
    @Expose
    private String providerComments;
    @SerializedName("signatureImageUrl")
    @Expose
    private String signatureImageUrl;
    @SerializedName("orderStatus")
    @Expose
    private OrderStatus_ orderStatus;
    @SerializedName("phone")
    @Expose
    private long phone;

    public Integer getId() {
      return id;
    }

    public void setId(Integer id) {
      this.id = id;
    }

    public PickupAddress getPickupAddress() {
      return pickupAddress;
    }

    public void setPickupAddress(PickupAddress pickupAddress) {
      this.pickupAddress = pickupAddress;
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

    public String getComments() {
      return comments;
    }

    public void setComments(String comments) {
      this.comments = comments;
    }

    public String getProviderComments() {
      return providerComments;
    }

    public void setProviderComments(String providerComments) {
      this.providerComments = providerComments;
    }

    public String getSignatureImageUrl() {
      return signatureImageUrl;
    }

    public void setSignatureImageUrl(String signatureImageUrl) {
      this.signatureImageUrl = signatureImageUrl;
    }

    public OrderStatus_ getOrderStatus() {
      return orderStatus;
    }

    public void setOrderStatus(OrderStatus_ orderStatus) {
      this.orderStatus = orderStatus;
    }

    public long getPhone() {
      return phone;
    }

    public void setPhone(long phone) {
      this.phone = phone;
    }

  }


  public class Provider {

    @SerializedName("id")
    @Expose
    private Object id;
    @SerializedName("name")
    @Expose
    private Object name;
    @SerializedName("phone")
    @Expose
    private Object phone;
    @SerializedName("profileImageUrl")
    @Expose
    private Object profileImageUrl;

    public Object getId() {
      return id;
    }

    public void setId(Object id) {
      this.id = id;
    }

    public Object getName() {
      return name;
    }

    public void setName(Object name) {
      this.name = name;
    }

    public Object getPhone() {
      return phone;
    }

    public void setPhone(Object phone) {
      this.phone = phone;
    }

    public Object getProfileImageUrl() {
      return profileImageUrl;
    }

    public void setProfileImageUrl(Object profileImageUrl) {
      this.profileImageUrl = profileImageUrl;
    }

  }


  public class Rating {

    @SerializedName("rate")
    @Expose
    private Object rate;
    @SerializedName("comment")
    @Expose
    private Object comment;

    public Object getRate() {
      return rate;
    }

    public void setRate(Object rate) {
      this.rate = rate;
    }

    public Object getComment() {
      return comment;
    }

    public void setComment(Object comment) {
      this.comment = comment;
    }

  }

}


