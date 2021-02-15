package com.dev.pigeonproviderapp.httpRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationRequestSendModel {

    @SerializedName("lat")
    @Expose
    private double latitude;

    @SerializedName("long")
    @Expose
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
