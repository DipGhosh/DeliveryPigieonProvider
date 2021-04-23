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
    private String packageTypes;
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
    private int weight;
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
    private float amountEarn;
    @SerializedName("provider_bonus")
    @Expose
    private Integer providerBonus;
    @SerializedName("distance")
    @Expose
    private double distance;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("payment_point")
    @Expose
    private String paymentPoint;

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

    public String getPackageTypes() {
      return packageTypes;
    }

    public void setPackageTypes(String packageTypes) {
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

    public int getWeight() {
      return weight;
    }

    public void setWeight(int weight) {
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

    public float getAmountEarn() {
      return amountEarn;
    }

    public void setAmountEarn(float amountEarn) {
      this.amountEarn = amountEarn;
    }
    public double getDistance() {
      return distance;
    }

    public void setDistance(double distance) {
      this.distance = distance;
    }
    public User getUser() {
      return user;
    }

    public void setUser(User user) {
      this.user = user;
    }

    public String getPaymentPoint() {
      return paymentPoint;
    }

    public void setPaymentPoint(String paymentPoint) {
      this.paymentPoint = paymentPoint;
    }
    public Integer getProviderBonus() {
      return providerBonus;
    }

    public void setProviderBonus(Integer providerBonus) {
      this.providerBonus = providerBonus;
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
    @SerializedName("flatname")
    @Expose
    private String dropFlatname;
    @SerializedName("reachaddress_note")
    @Expose
    private String dropReachaddressNote;

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

    public String getDropFlatname() {
      return dropFlatname;
    }

    public void setDropFlatname(String dropFlatname) {
      this.dropFlatname = dropFlatname;
    }

    public String getDropReachaddressNote() {
      return dropReachaddressNote;
    }

    public void setDropReachaddressNote(String dropReachaddressNote) {
      this.dropReachaddressNote = dropReachaddressNote;
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
    @SerializedName("is_collect_payment")
    @Expose
    private Boolean isCollectPayment;
    @SerializedName("is_signature_verified")
    @Expose
    private Boolean isSignatureVerified;
    @SerializedName("is_otp_verified")
    @Expose
    private Boolean isOtpVerified;

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
    public Boolean getIsCollectPayment() {
      return isCollectPayment;
    }

    public void setIsCollectPayment(Boolean isCollectPayment) {
      this.isCollectPayment = isCollectPayment;
    }

    public Boolean getIsSignatureVerified() {
      return isSignatureVerified;
    }

    public void setIsSignatureVerified(Boolean isSignatureVerified) {
      this.isSignatureVerified = isSignatureVerified;
    }

    public Boolean getIsOtpVerified() {
      return isOtpVerified;
    }

    public void setIsOtpVerified(Boolean isOtpVerified) {
      this.isOtpVerified = isOtpVerified;
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
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("payment_time")
    @Expose
    private String paymentTime;


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
    public String getAddress() {
      return address;
    }

    public void setAddress(String address) {
      this.address = address;
    }

    public String getPaymentTime() {
      return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
      this.paymentTime = paymentTime;
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
    @SerializedName("flatname")
    @Expose
    private String pickupFlatname;
    @SerializedName("reachaddress_note")
    @Expose
    private String pickupReachaddressNote;

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

    public String getPickupFlatname() {
      return pickupFlatname;
    }

    public void setPickupFlatname(String pickupFlatname) {
      this.pickupFlatname = pickupFlatname;
    }

    public String getPickupReachaddressNote() {
      return pickupReachaddressNote;
    }

    public void setPickupReachaddressNote(String pickupReachaddressNote) {
      this.pickupReachaddressNote = pickupReachaddressNote;
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
    @SerializedName("is_collect_payment")
    @Expose
    private Boolean isCollectPayment;
    @SerializedName("is_signature_verified")
    @Expose
    private Boolean isSignatureVerified;
    @SerializedName("is_otp_verified")
    @Expose
    private Boolean isOtpVerified;

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

    public Boolean getIsCollectPayment() {
      return isCollectPayment;
    }

    public void setIsCollectPayment(Boolean isCollectPayment) {
      this.isCollectPayment = isCollectPayment;
    }
    public Boolean getIsSignatureVerified() {
      return isSignatureVerified;
    }

    public void setIsSignatureVerified(Boolean isSignatureVerified) {
      this.isSignatureVerified = isSignatureVerified;
    }

    public Boolean getIsOtpVerified() {
      return isOtpVerified;
    }

    public void setIsOtpVerified(Boolean isOtpVerified) {
      this.isOtpVerified = isOtpVerified;
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
    private float rate;
    @SerializedName("comment")
    @Expose
    private String comment;

    public float getRate() {
      return rate;
    }

    public void setRate(float rate) {
      this.rate = rate;
    }

    public String getComment() {
      return comment;
    }

    public void setComment(String comment) {
      this.comment = comment;
    }

  }

  public class User {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private long phone;
    @SerializedName("profileImageUrl")
    @Expose
    private String profileImageUrl;

    public Integer getId() {
      return id;
    }

    public void setId(Integer id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public long getPhone() {
      return phone;
    }

    public void setPhone(long phone) {
      this.phone = phone;
    }

    public String getProfileImageUrl() {
      return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
      this.profileImageUrl = profileImageUrl;
    }

  }

}


