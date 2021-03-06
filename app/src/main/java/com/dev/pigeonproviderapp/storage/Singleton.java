package com.dev.pigeonproviderapp.storage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Singleton {
    private static Singleton singleton = new Singleton();
    private String TOKEN;
    private int ORDERID;
    private int ORDERITEMID;
    private boolean orderaccept;
    private float ORDERAMOUNT;
    private long PHONENUMBER;
    private int ORDERITEMSTATUS;
    private boolean itemcomplete;
    private int PAYMENTSTATUS;
    private int ORDERSTATUSCODE;
    private int ERRORSTATUS;
    private String DROPPOINTTYPE;
    private boolean ALLDROPPOINTCOMPLETE;
    private String USERNAME;
    private String USERIMAGE;
    private float ORDERRATING;
    private String RATECOMMENT;
    private String PAYMENTPOINT;
    private String PAYMENTSTATUSMESSAGE;
    private boolean isCollectPayment;
    private boolean alreadyExecuted;
    private boolean isOtpverified;
    private boolean idSignatureVerified;
    private boolean dropotpVerified;
    private boolean dropImageVerified;
    private String OTPVERIFYMESSAGE;
    private String profileImageUrl;

    private boolean isProfileUpdated;

    /* A private Constructor prevents any other
     * class from instantiating.
     */
    private Singleton() {


    }

    /* Static 'instance' method */
    public static Singleton getInstance() {

        return singleton;
    }

    public String getTOKEN() {
        return TOKEN;
    }

    public void setTOKEN(String TOKEN) {
        this.TOKEN = "Bearer " + TOKEN;
    }

    public int getORDERID() {
        return ORDERID;
    }

    public void setORDERID(int ORDERID) {
        this.ORDERID = ORDERID;
    }

    public int getORDERITEMID() {
        return ORDERITEMID;
    }

    public void setORDERITEMID(int ORDERITEMID) {
        this.ORDERITEMID = ORDERITEMID;
    }

    public boolean isOrderaccept() {
        return orderaccept;
    }

    public void setOrderaccept(boolean orderaccept) {
        this.orderaccept = orderaccept;
    }

    public float getORDERAMOUNT() {
        return ORDERAMOUNT;
    }

    public void setORDERAMOUNT(float ORDERAMOUNT) {
        this.ORDERAMOUNT = ORDERAMOUNT;
    }

    public long getPHONENUMBER() {
        return PHONENUMBER;
    }

    public void setPHONENUMBER(long PHONENUMBER) {
        this.PHONENUMBER = PHONENUMBER;
    }

    public int getORDERITEMSTATUS() {
        return ORDERITEMSTATUS;
    }

    public void setORDERITEMSTATUS(int ORDERITEMSTATUS) {
        this.ORDERITEMSTATUS = ORDERITEMSTATUS;
    }

    public boolean isItemcomplete() {
        return itemcomplete;
    }

    public void setItemcomplete(boolean itemcomplete) {
        this.itemcomplete = itemcomplete;
    }

    public int getPAYMENTSTATUS() {
        return PAYMENTSTATUS;
    }

    public void setPAYMENTSTATUS(int PAYMENTSTATUS) {
        this.PAYMENTSTATUS = PAYMENTSTATUS;
    }

    public int getORDERSTATUSCODE() {
        return ORDERSTATUSCODE;
    }

    public void setORDERSTATUSCODE(int ORDERSTATUSCODE) {
        this.ORDERSTATUSCODE = ORDERSTATUSCODE;
    }

    public int getERRORSTATUS() {
        return ERRORSTATUS;
    }

    public void setERRORSTATUS(int ERRORSTATUS) {
        this.ERRORSTATUS = ERRORSTATUS;
    }

    public String getDROPPOINTTYPE() {
        return DROPPOINTTYPE;
    }

    public void setDROPPOINTTYPE(String DROPPOINTTYPE) {
        this.DROPPOINTTYPE = DROPPOINTTYPE;
    }

    public boolean isALLDROPPOINTCOMPLETE() {
        return ALLDROPPOINTCOMPLETE;
    }

    public void setALLDROPPOINTCOMPLETE(boolean ALLDROPPOINTCOMPLETE) {
        this.ALLDROPPOINTCOMPLETE = ALLDROPPOINTCOMPLETE;
    }

    public boolean isProfileUpdated() {
        return isProfileUpdated;
    }

    public void setProfileUpdated(boolean profileUpdated) {
        isProfileUpdated = profileUpdated;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getUSERIMAGE() {
        return USERIMAGE;
    }

    public void setUSERIMAGE(String USERIMAGE) {
        this.USERIMAGE = USERIMAGE;
    }

    public String getOTPVERIFYMESSAGE() {
        return OTPVERIFYMESSAGE;
    }

    public void setOTPVERIFYMESSAGE(String OTPVERIFYMESSAGE) {
        this.OTPVERIFYMESSAGE = OTPVERIFYMESSAGE;
    }

    public float getORDERRATING() {
        return ORDERRATING;
    }

    public void setORDERRATING(float ORDERRATING) {
        this.ORDERRATING = ORDERRATING;
    }

    public String getRATECOMMENT() {
        return RATECOMMENT;
    }

    public void setRATECOMMENT(String RATECOMMENT) {
        this.RATECOMMENT = RATECOMMENT;
    }

    public String getPAYMENTPOINT() {
        return PAYMENTPOINT;
    }

    public void setPAYMENTPOINT(String PAYMENTPOINT) {
        this.PAYMENTPOINT = PAYMENTPOINT;
    }

    public String getPAYMENTSTATUSMESSAGE() {
        return PAYMENTSTATUSMESSAGE;
    }

    public void setPAYMENTSTATUSMESSAGE(String PAYMENTSTATUSMESSAGE) {
        this.PAYMENTSTATUSMESSAGE = PAYMENTSTATUSMESSAGE;
    }

    public boolean isCollectPayment() {
        return isCollectPayment;
    }

    public void setCollectPayment(boolean collectPayment) {
        isCollectPayment = collectPayment;
    }

    public boolean isAlreadyExecuted() {
        return alreadyExecuted;
    }

    public void setAlreadyExecuted(boolean alreadyExecuted) {
        this.alreadyExecuted = alreadyExecuted;
    }
    public boolean isOtpverified() {
        return isOtpverified;
    }

    public void setOtpverified(boolean otpverified) {
        isOtpverified = otpverified;
    }

    public boolean isIdSignatureVerified() {
        return idSignatureVerified;
    }

    public void setIdSignatureVerified(boolean idSignatureVerified) {
        this.idSignatureVerified = idSignatureVerified;
    }

    public boolean isDropotpVerified() {
        return dropotpVerified;
    }

    public void setDropotpVerified(boolean dropotpVerified) {
        this.dropotpVerified = dropotpVerified;
    }
    public boolean isDropImageVerified() {
        return dropImageVerified;
    }

    public void setDropImageVerified(boolean dropImageVerified) {
        this.dropImageVerified = dropImageVerified;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
