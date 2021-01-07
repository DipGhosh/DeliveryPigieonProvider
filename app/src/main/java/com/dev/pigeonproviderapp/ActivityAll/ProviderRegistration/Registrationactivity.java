package com.dev.pigeonproviderapp.ActivityAll.ProviderRegistration;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import com.dev.pigeonproviderapp.MainActivity;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.PermissionUtils;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.activity.BaseActivity;
import com.dev.pigeonproviderapp.network.APIClient;
import com.dev.pigeonproviderapp.network.APIInterface;
import com.dev.pigeonproviderapp.viewmodel.RegisterationActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class Registrationactivity extends BaseActivity implements View.OnClickListener{

  private Button btnRegistration;
  private EditText providerPhoneNumber,otpField;
  private TextView getOtp,setOtp;
  private CheckBox checkTerms;
  String MobilePattern = "[0-9]{10}";
  RegisterationActivityViewModel registerViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_registrationactivity);
    //Check the location permission
    final PermissionUtils permissionUtils = new PermissionUtils(this);
    permissionUtils.checkPermissions();
    //Find all the views
    btnRegistration = findViewById(R.id.btn_registration);
    providerPhoneNumber = findViewById(R.id.et_phoneNumber);
    otpField = findViewById(R.id.et_otp);
    getOtp=findViewById(R.id.tv_getOtp);
    setOtp=findViewById(R.id.tv_resendOtp);
    checkTerms=findViewById(R.id.checkTerms);

    registerViewModel = ViewModelProviders.of(this).get(RegisterationActivityViewModel.class);
    //Registered click listener
    btnRegistration.setOnClickListener(this);

  }
  @Override
  public void onClick(View v) {

    switch (v.getId() /*to get clicked view id**/) {
      case R.id.btn_registration:
        if (isValid()) {
           /*apiInterface = APIClient.getClient().create(APIInterface.class);
        registerViewModel.getRegisterData(apiInterface);*/
          Intent intent=new Intent(Registrationactivity.this, ProviderDetails.class);
          startActivity(intent);
        }
        break;
      default:
        break;
    }
  }
  // method will validate the fields
  private boolean isValid() {
    if (TextUtils.isEmpty(providerPhoneNumber.getText().toString())) {
      UiUtils.showToast(this, getString(R.string.alert_create_phone));
      return false;
    } else if (!providerPhoneNumber.getText().toString().matches(MobilePattern)) {
      UiUtils.showToast(this, getString(R.string.alert_create_phone));
      return false;
    } else {
      return true;
    }
  }


}