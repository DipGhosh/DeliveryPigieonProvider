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

  private String mobileNumber;

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
    mobileNumber = sharedPreferences.getString("mobile", "");
    return mobileNumber;
  }

  public void setMobileNumber(String mobileNumber) {
    editor.putString("mobile", mobileNumber);
    editor.commit();
  }
}
