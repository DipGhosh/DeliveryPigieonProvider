package com.dev.pigeonproviderapp.ActivityAll.ProviderRegistration;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.PermissionUtils;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.Utility.Utility;
import com.dev.pigeonproviderapp.activity.BaseActivity;
import com.dev.pigeonproviderapp.datamodel.OTPSendDataModel;
import com.dev.pigeonproviderapp.httpRequest.OTPSendAPI;
import com.dev.pigeonproviderapp.viewmodel.RegisterationActivityViewModel;

public class Registrationactivity extends BaseActivity implements View.OnClickListener {

  String MobilePattern = "[0-9]{5}";
  RegisterationActivityViewModel registerViewModel;
  private Button btnRegistration;
  private EditText providerPhoneNumber, otpField;
  private TextView getOtp, setOtp;
  private CheckBox checkTerms;

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
    getOtp = findViewById(R.id.tv_getOtp);
    setOtp = findViewById(R.id.tv_resendOtp);
    checkTerms = findViewById(R.id.checkTerms);

    registerViewModel = ViewModelProviders.of(this).get(RegisterationActivityViewModel.class);

    //Registered click listener
    btnRegistration.setOnClickListener(this);
    getOtp.setOnClickListener(this);
    setOtp.setOnClickListener(this);
  }


  @Override
  public void onClick(View v) {

    switch (v.getId()) {
      case R.id.tv_getOtp:
        CallGetOTP();
        break;
      case R.id.tv_resendOtp:
        CallGetOTP();
        break;
      case R.id.btn_registration:
        // TODO
        break;
      default:
        break;
    }
  }


  public void CallGetOTP() {

    if (isValid()) {

      OTPSendAPI otpSendAPI = new OTPSendAPI();
      otpSendAPI.setPhone(providerPhoneNumber.getText().toString());
      otpSendAPI.setUserType(1);
      otpSendAPI.setDeviceName(Utility.DEVICE_NAME);

      registerViewModel.getRegisterData(otpSendAPI).observe(this, new Observer<OTPSendDataModel>() {
        @Override
        public void onChanged(OTPSendDataModel otpSendDataModel) {

          int data = otpSendDataModel.getData();
          if (data > 0) {
            otpField.setText(""+data);
          }

        }
      });
    }
  }


  // method will validate the fields
  private boolean isValid() {
    if (TextUtils.isEmpty(providerPhoneNumber.getText().toString())) {
      UiUtils.showToast(this, getString(R.string.alert_create_phone));
      return false;
    } /*else if (!providerPhoneNumber.getText().toString().matches(MobilePattern)) {
      UiUtils.showToast(this, getString(R.string.alert_create_phone));
      return false;
    }*/ else {
      return true;
    }
  }


}