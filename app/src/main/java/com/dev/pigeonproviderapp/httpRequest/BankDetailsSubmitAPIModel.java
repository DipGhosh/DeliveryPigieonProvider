package com.dev.pigeonproviderapp.httpRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankDetailsSubmitAPIModel {

    @SerializedName("name_on_account")
    @Expose
    private String name;
    @SerializedName("account_no")
    @Expose
    private String account_no;
    @SerializedName("account_type")
    @Expose
    private String account_type;
    @SerializedName("bank_name")
    @Expose
    private String bank_name;
    @SerializedName("ifsc")
    @Expose
    private String ifsc;
    @SerializedName("bank_address")
    @Expose
    private String bank_address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getBank_address() {
        return bank_address;
    }

    public void setBank_address(String bank_address) {
        this.bank_address = bank_address;
    }






}
