package com.dev.pigeonproviderapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.dev.pigeonproviderapp.activity.BaseActivity;

public class LoginActivity extends BaseActivity {

  Button providerLogin;
  EditText et_phoneNumber;
  EditText et_otp;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    et_phoneNumber = (EditText) findViewById(R.id.et_phoneNumber);
    et_otp = (EditText) findViewById(R.id.et_otp);
    providerLogin = (Button) findViewById(R.id.btn_providerlogin);


    providerLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(LoginActivity.this, Registrationactivity.class);
        startActivity(intent);
      }
    });

  }
}