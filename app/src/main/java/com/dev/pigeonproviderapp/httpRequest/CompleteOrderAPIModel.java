package com.dev.pigeonproviderapp.httpRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompleteOrderAPIModel {

    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("type")
    @Expose
    private String type;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
