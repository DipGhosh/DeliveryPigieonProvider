package com.dev.pigeonproviderapp.ActivityAll.ProviderRegistration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.lifecycle.ViewModelProviders;

import com.dev.pigeonproviderapp.MainActivity;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.activity.BaseActivity;
import com.dev.pigeonproviderapp.network.APIClient;
import com.dev.pigeonproviderapp.network.APIInterface;
import com.dev.pigeonproviderapp.viewmodel.RegisterationActivityViewModel;

public class Registrationactivity extends BaseActivity {

  Button btnRegistration;
  EditText et_phoneNumber;
  EditText et_otp;

  RegisterationActivityViewModel registerViewModel;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_registrationactivity);

    btnRegistration = (Button) findViewById(R.id.btn_registration);
    et_phoneNumber = findViewById(R.id.et_phoneNumber);
    et_otp = findViewById(R.id.et_otp);

    registerViewModel = ViewModelProviders.of(this).get(RegisterationActivityViewModel.class);

    btnRegistration.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        /*apiInterface = APIClient.getClient().create(APIInterface.class);
        registerViewModel.getRegisterData(apiInterface);*/
        Intent intent=new Intent(Registrationactivity.this, ProviderDetails.class);
        startActivity(intent);
      }
    });

  }
}