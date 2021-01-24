package com.dev.pigeonproviderapp.storage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Singleton {
    private static Singleton singleton = new Singleton();
    private String TOKEN;


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


}
