package com.dev.pigeonproviderapp.view.Dataprovider;

public class ChildItem {


    // Declaration of the variable
    private String placedorder_type;
    private String orderplace_pickup_address;
    private String orderplace_delivery_address;
    private String placeorder_total_ammount;
    private String order_status_message;
    private String providername;
    private String providerImgLink;
    private String orderId;
    public long providerPhonenumber;
    public int currentorder_id;
    public double earnAmount;
    public int provider_bonus;


    public String getPlacedorder_type() {
        return placedorder_type;
    }

    public void setPlacedorder_type(String placedorder_type) {
        this.placedorder_type = placedorder_type;
    }

    public String getOrderplace_pickup_address() {
        return orderplace_pickup_address;
    }

    public void setOrderplace_pickup_address(String orderplace_pickup_address) {
        this.orderplace_pickup_address = orderplace_pickup_address;
    }

    public String getOrderplace_delivery_address() {
        return orderplace_delivery_address;
    }

    public void setOrderplace_delivery_address(String orderplace_delivery_address) {
        this.orderplace_delivery_address = orderplace_delivery_address;
    }

    public String getPlaceorder_total_ammount() {
        return placeorder_total_ammount;
    }

    public void setPlaceorder_total_ammount(String placeorder_total_ammount) {
        this.placeorder_total_ammount = placeorder_total_ammount;
    }

    public String getOrder_status_message() {
        return order_status_message;
    }

    public void setOrder_status_message(String order_status_message) {
        this.order_status_message = order_status_message;
    }

    public String getProvidername() {
        return providername;
    }

    public void setProvidername(String providername) {
        this.providername = providername;
    }

    public String getProviderImgLink() {
        return providerImgLink;
    }

    public void setProviderImgLink(String providerImgLink) {
        this.providerImgLink = providerImgLink;
    }
    public long getProviderPhonenumber() {
        return providerPhonenumber;
    }

    public void setProviderPhonenumber(long providerPhonenumber) {
        this.providerPhonenumber = providerPhonenumber;
    }
    public int getCurrentorder_id() {
        return currentorder_id;
    }

    public void setCurrentorder_id(int currentorder_id) {
        this.currentorder_id = currentorder_id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getEarnAmount() {
        return earnAmount;
    }

    public void setEarnAmount(double earnAmount) {
        this.earnAmount = earnAmount;
    }

    public int getProvider_bonus() {
        return provider_bonus;
    }

    public void setProvider_bonus(int provider_bonus) {
        this.provider_bonus = provider_bonus;
    }
}
