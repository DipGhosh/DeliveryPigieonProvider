package com.dev.pigeonproviderapp.ActivityAll.ProviderRegistration;

import android.content.Intent;
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
import com.dev.pigeonproviderapp.Baseclass.BaseActivity;
import com.dev.pigeonproviderapp.datamodel.OTPSendResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.VerifyOtpResponseDataModel;
import com.dev.pigeonproviderapp.httpRequest.OTPSendAPIModel;
import com.dev.pigeonproviderapp.httpRequest.VerifyOtpAPIModel;
import com.dev.pigeonproviderapp.storage.ShareP;
import com.dev.pigeonproviderapp.viewmodel.OtpSendViewModel;
import com.dev.pigeonproviderapp.viewmodel.VerifyOtpViewModel;

public class Registrationactivity extends BaseActivity implements View.OnClickListener {

  String MobilePattern = "[0-9]{5}";
  OtpSendViewModel otpSendViewModel;
  VerifyOtpViewModel verifyOtpViewModel;
  private Button btnRegistration;
  private EditText providerPhoneNumber, otpField;
  private TextView getOtp, setOtp;
  private CheckBox checkTerms;
  private ShareP shareP;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_registrationactivity);
    shareP=new ShareP(Registrationactivity.this);
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

    otpSendViewModel = ViewModelProviders.of(this).get(OtpSendViewModel.class);
    verifyOtpViewModel =ViewModelProviders.of(this).get(VerifyOtpViewModel.class);

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
        CallVerifyOTP();
        break;
      default:
        break;
    }
  }



  public void CallGetOTP() {

    if (isValid()) {

      OTPSendAPIModel otpSendAPIModel = new OTPSendAPIModel();
      otpSendAPIModel.setPhone(providerPhoneNumber.getText().toString());
      otpSendAPIModel.setUserType(2);
      otpSendAPIModel.setDeviceName(Utility.DEVICE_NAME);

      otpSendViewModel.getRegisterData(otpSendAPIModel).observe(this, new Observer<OTPSendResponseDataModel>() {
        @Override
        public void onChanged(OTPSendResponseDataModel otpSendResponseDataModel) {

          int data = otpSendResponseDataModel.getData();
          if (data > 0) {
            otpField.setText(""+data);
          }

        }
      });
    }
  }



  public void CallVerifyOTP(){
     if (isotpVerifiedValidation())
     {
       VerifyOtpAPIModel verifyOtpAPIModel=new VerifyOtpAPIModel();
       verifyOtpAPIModel.setPhone(providerPhoneNumber.getText().toString());
       verifyOtpAPIModel.setDeviceName(Utility.DEVICE_NAME);
       verifyOtpAPIModel.setOtp(otpField.getText().toString());
       verifyOtpViewModel.getVerifyOtpData(verifyOtpAPIModel).observe(this, new Observer<VerifyOtpResponseDataModel>() {
         @Override
         public void onChanged(VerifyOtpResponseDataModel verifyOtpResponseDataModel) {
             if (verifyOtpResponseDataModel.getStatus()==200)
             {
               shareP.SetIsloogedIn(true);
               shareP.SetUsertoken(verifyOtpResponseDataModel.getData().getToken());
               Intent providerDetails=new Intent(Registrationactivity.this,ProviderDetails.class);
               startActivity(providerDetails);
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

  // method will validate the fields
  private boolean isotpVerifiedValidation() {
    if (TextUtils.isEmpty(providerPhoneNumber.getText().toString())) {
      UiUtils.showToast(this, getString(R.string.alert_create_phone));
      return false;
    }else if(TextUtils.isEmpty(otpField.getText().toString())) {
      UiUtils.showToast(this, getString(R.string.alert_create_otpvalidation));
      return false;
    } else {
      return true;
    }
  }


}