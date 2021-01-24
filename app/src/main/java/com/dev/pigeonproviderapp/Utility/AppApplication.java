package com.dev.pigeonproviderapp.Utility;

import android.app.Application;

import com.dev.pigeonproviderapp.storage.Singleton;

public class AppApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Singleton.getInstance();
  }

}
