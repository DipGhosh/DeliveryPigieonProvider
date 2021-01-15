package com.dev.pigeonproviderapp.storage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by android on 9/6/16.
 */
public class ShareP {
    private Activity activity;
    private Context context;
    private String packeageName="com.dev.pigeonproviderapp";
    private SharedPreferences sharedPreferences;
    private int MOD_PRIVATE=0;
    SharedPreferences.Editor editor;
    private  boolean isLoggedin;
    private String usertoken;





    public ShareP(Activity activity)
    {
        this.activity=activity;
        sharedPreferences=this.activity.getSharedPreferences(packeageName,MOD_PRIVATE);
        editor=sharedPreferences.edit();

    }

    public ShareP(Context context)
    {
        this.context=context;
        sharedPreferences=this.activity.getSharedPreferences(packeageName,MOD_PRIVATE);
        editor=sharedPreferences.edit();
    }


    public void SetUsertoken(String usertoken)
    {
        editor.putString("USERTOKEN",usertoken);
        editor.commit();
    }

    public String GetUserToken()
    {
        usertoken=sharedPreferences.getString("usertoken", "");
        return usertoken;
    }

    public void SetIsloogedIn(boolean logged)
    {
        editor.putBoolean("isloggedin",true);
        editor.commit();
    }

    public boolean getLoggdInstaus()
    {
        isLoggedin=sharedPreferences.getBoolean("isloggedin",false);
        return isLoggedin;
    }



    public void LogOut()
    {
        editor.clear();
        editor.commit();

    }














}