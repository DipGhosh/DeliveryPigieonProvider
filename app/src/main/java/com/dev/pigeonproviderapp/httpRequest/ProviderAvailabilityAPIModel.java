package com.dev.pigeonproviderapp.httpRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProviderAvailabilityAPIModel {

    @SerializedName("is_available")
    @Expose
    private int is_available;

    public int getIs_available() {
        return is_available;
    }

    public void setIs_available(int is_available) {
        this.is_available = is_available;
    }
}
