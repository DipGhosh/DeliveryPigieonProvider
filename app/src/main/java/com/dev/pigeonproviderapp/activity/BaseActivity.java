package com.dev.pigeonproviderapp.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.dev.pigeonproviderapp.network.APIInterface;

public abstract class BaseActivity extends AppCompatActivity {

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState,
      @Nullable PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);

  }
}
