package com.dev.pigeonproviderapp.storage;

import android.app.Activity;
import android.content.SharedPreferences;

public class SharePreference {

  SharedPreferences.Editor editor;
  private Activity activity;
  private String packeageName = "com.dev.pigeonproviderapp";
  private SharedPreferences sharedPreferences;
  private int MOD_PRIVATE = 0;
  private boolean isLoggedin;
  private String token;
  private boolean isProfileverified;
  private boolean isProviderAvailable;


  public SharePreference(Activity activity) {
    this.activity = activity;
    sharedPreferences = this.activity.getSharedPreferences(packeageName, MOD_PRIVATE);
    editor = sharedPreferences.edit();

  }

  public void SetIsloogedIn(boolean logged) {
    editor.putBoolean("isloggedin", true);
    editor.commit();
  }

  public boolean getLoggdInstaus() {
    isLoggedin = sharedPreferences.getBoolean("isloggedin", false);
    return isLoggedin;
  }
  public void setProfileverified(boolean profileverified) {
    editor.putBoolean("isProfileverified", profileverified);
    editor.commit();
  }

  public boolean GetVerified() {
    isProfileverified = sharedPreferences.getBoolean("isProfileverified", false);
    return isProfileverified;
  }



  public void LogOut() {
    editor.clear();
    editor.commit();

  }

  public String getToken() {
    token = sharedPreferences.getString("token", "");
    return token;
  }

  public void setToken(String token) {
    editor.putString("token", token);
    editor.commit();
  }


  public String getMobileNumber() {
   String  mobileNumber = sharedPreferences.getString("mobile", "");
    return mobileNumber;
  }

  public void setMobileNumber(String mobileNumber) {
    editor.putString("mobile", mobileNumber);
    editor.commit();
  }

  public void setProviderAvailable(boolean providerAvailable) {
    editor.putBoolean("isProviderAvailable", providerAvailable);
    editor.commit();
  }

  public boolean GetProviderAvailable() {
    isProviderAvailable = sharedPreferences.getBoolean("isProviderAvailable", false);
    return isProviderAvailable;
  }



}
