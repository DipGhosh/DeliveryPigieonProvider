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
    private String ITEMSTATUSMESSAGE;
    private long PHONENUMBER;


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
    public String getITEMSTATUSMESSAGE() {
        return ITEMSTATUSMESSAGE;
    }

    public void setITEMSTATUSMESSAGE(String ITEMSTATUSMESSAGE) {
        this.ITEMSTATUSMESSAGE = ITEMSTATUSMESSAGE;
    }
    public long getPHONENUMBER() {
        return PHONENUMBER;
    }

    public void setPHONENUMBER(long PHONENUMBER) {
        this.PHONENUMBER = PHONENUMBER;
    }



}
