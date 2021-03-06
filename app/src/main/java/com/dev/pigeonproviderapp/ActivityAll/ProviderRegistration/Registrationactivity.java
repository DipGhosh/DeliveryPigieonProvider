package com.dev.pigeonproviderapp.ActivityAll.ProviderRegistration;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.dev.pigeonproviderapp.ActivityAll.ProviderDashboard;
import com.dev.pigeonproviderapp.Baseclass.BaseActivity;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.NetworkUtils;
import com.dev.pigeonproviderapp.Utility.PermissionUtils;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.Utility.Utility;
import com.dev.pigeonproviderapp.datamodel.OTPSendResponseDataModel;
import com.dev.pigeonproviderapp.httpRequest.OTPSendAPIModel;
import com.dev.pigeonproviderapp.httpRequest.VerifyOtpAPIModel;
import com.dev.pigeonproviderapp.storage.SharePreference;
import com.dev.pigeonproviderapp.storage.Singleton;
import com.dev.pigeonproviderapp.view.WebViewLinkShow.WebserviceActivity;
import com.dev.pigeonproviderapp.viewmodel.OtpSendViewModel;
import com.dev.pigeonproviderapp.viewmodel.VerifyOtpViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;


public class Registrationactivity extends BaseActivity implements View.OnClickListener {


  private Activity activity = Registrationactivity.this;

  String MobilePattern = "[0-9]{10}";

  OtpSendViewModel otpSendViewModel;
  VerifyOtpViewModel verifyOtpViewModel;

  private Button btnRegistration;
  private EditText providerPhoneNumber, otpField;
  private TextView getOtp, resendOtp, termsandcondition;
  private CheckBox checkTerms;
  private Dialog dialog;
  private SharePreference sharePreference;
  private String token;
  private ConstraintLayout mainLayout;




  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_registrationactivity);



    //Check the location permission
    final PermissionUtils permissionUtils = new PermissionUtils(this);
    permissionUtils.checkPermissions();



    dialog = UiUtils.showProgress(Registrationactivity.this);
    sharePreference = new SharePreference(Registrationactivity.this);

    //Find all the views
    btnRegistration = findViewById(R.id.btn_registration);
    providerPhoneNumber = findViewById(R.id.et_phoneNumber);
    otpField = findViewById(R.id.et_otp);
    getOtp = findViewById(R.id.tv_getOtp);
    resendOtp = findViewById(R.id.tv_resendOtp);
    checkTerms = findViewById(R.id.checkTerms);
    termsandcondition = findViewById(R.id.tv_terms_condition);
    mainLayout=findViewById(R.id.ConstraintLayoutRoot);

    otpSendViewModel = ViewModelProviders.of(this).get(OtpSendViewModel.class);
    verifyOtpViewModel = ViewModelProviders.of(this).get(VerifyOtpViewModel.class);

    //Registered click listener
    btnRegistration.setOnClickListener(this);
    getOtp.setOnClickListener(this);
    resendOtp.setOnClickListener(this);
    checkTerms.setOnClickListener(this);
    termsandcondition.setOnClickListener(this);
    mainLayout.setOnClickListener(this);

    //Firebase Module
    FirebaseMessaging.getInstance().getToken()
        .addOnCompleteListener(new OnCompleteListener<String>() {
          @Override
          public void onComplete(@NonNull Task<String> task) {
            if (!task.isSuccessful()) {
              Log.w("Token", "Fetching FCM registration token failed", task.getException());
              return;
            }

            // Get new FCM registration token
            token = task.getResult();
            Log.d("Token", token);

          }
        });

  }

  @Override
  public void onBackPressed() {

    Intent a = new Intent(Intent.ACTION_MAIN);
    a.addCategory(Intent.CATEGORY_HOME);
    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(a);

  }


  @Override
  public void onClick(View v) {

    switch (v.getId()) {
      case R.id.tv_getOtp:

        if (NetworkUtils.isNetworkAvailable(activity)) {
          CallGetOTP();
        }else {
          UiUtils.showToast(this, getString(R.string.network_error));
        }

        break;
      case R.id.tv_resendOtp:
        if (NetworkUtils.isNetworkAvailable(activity)) {
          CallGetOTP();
        }else {
          UiUtils.showToast(this, getString(R.string.network_error));
        }

        break;
      case R.id.btn_registration:

        if (NetworkUtils.isNetworkAvailable(activity)) {
          CallVerifyOTP();
        }else {
          UiUtils.showToast(this, getString(R.string.network_error));
        }

        break;
      case R.id.checkTerms:

        break;
      case R.id.tv_terms_condition:

        Intent intent = new Intent(Registrationactivity.this, WebserviceActivity.class);
        intent.putExtra(Utility.HEADER_KEY, Utility.TERMRS_OF_SERVICE_HEADER);
        intent.putExtra(Utility.LINK_KEY, Utility.TERMSSERVICES_LINK);
        startActivity(intent);
        break;
      case R.id.ConstraintLayoutRoot:
       UiUtils.hideSoftKeyBoard(activity,mainLayout);

        break;
      default:
        break;
    }
  }


  public void CallGetOTP() {

    if (isValid()) {
      dialog.show();
      OTPSendAPIModel otpSendAPIModel = new OTPSendAPIModel();
      otpSendAPIModel.setPhone(providerPhoneNumber.getText().toString());
      otpSendAPIModel.setUserType(2);
      otpSendAPIModel.setDeviceName(Utility.DEVICE_NAME);

      otpSendViewModel.getRegisterData(otpSendAPIModel)
          .observe(this, new Observer<OTPSendResponseDataModel>() {
            @Override
            public void onChanged(OTPSendResponseDataModel otpSendResponseDataModel) {
              dialog.dismiss();

              if (otpSendResponseDataModel != null) {

                if (otpSendResponseDataModel.getStatus() == 200) {

                  getOtp.setVisibility(View.GONE);
                  resendOtp.setVisibility(View.VISIBLE);

                }else if (otpSendResponseDataModel.getStatus() == 400) {
                  UiUtils.showAlert(activity, getString(R.string.app_name), getString(R.string.phone_number_restriction_validation));
                }

              }else {
                UiUtils.showAlert(activity, getString(R.string.app_name), getString(R.string.phone_number_restriction_validation));
              }


            }
          });
    }
  }


  public void CallVerifyOTP() {
    if (isotpVerifiedValidation()) {
      dialog.show();

      String mobileNumber = providerPhoneNumber.getText().toString();
      VerifyOtpAPIModel verifyOtpAPIModel = new VerifyOtpAPIModel();
      verifyOtpAPIModel.setPhone(mobileNumber);
      verifyOtpAPIModel.setDeviceName(Utility.DEVICE_NAME);
      verifyOtpAPIModel.setOtp(otpField.getText().toString());
      verifyOtpAPIModel.setDevicetoken(token);
      verifyOtpAPIModel.setDevicetype(Utility.DEVICE_TYPE);
      verifyOtpAPIModel.setUsertype(Utility.USERTYPE);

      verifyOtpViewModel.getVerifyOtpData(verifyOtpAPIModel)
          .observe(this, verifyOtpResponseDataModel -> {

            dialog.dismiss();



            if (verifyOtpResponseDataModel != null) {
              if (verifyOtpResponseDataModel.getStatus() == 200) {

                String token = verifyOtpResponseDataModel.getData().getToken();
                Log.d("Mangal", token);
                Singleton.getInstance().setTOKEN(token);

                if (token != "") {

                  Singleton.getInstance().setTOKEN(token);

                  if (verifyOtpResponseDataModel.getData().getUserFirstLogin() == true) {
                    sharePreference.setToken(token);

                    //User Verification check
                    if (verifyOtpResponseDataModel.getData().getUserVerified()==true)
                    {
                      Singleton.getInstance().setProfileUpdated(false);
                      sharePreference.setProfileverified(true);
                    }else {
                      Singleton.getInstance().setProfileUpdated(true);
                      sharePreference.setProfileverified(false);
                    }

                    Intent providerDetails = new Intent(Registrationactivity.this,
                            ProviderDetails.class);
                    startActivity(providerDetails);
                  } else {
                    sharePreference.SetIsloogedIn(true);
                    sharePreference.setToken(token);

                    // used for chat registeration and login
                    sharePreference.setMobileNumber(mobileNumber);

                    //User Verification check
                    if (verifyOtpResponseDataModel.getData().getUserVerified()==true)
                    {
                      Singleton.getInstance().setProfileUpdated(false);
                      sharePreference.setProfileverified(true);
                    }else {
                      Singleton.getInstance().setProfileUpdated(true);
                      sharePreference.setProfileverified(false);
                    }


                    Intent providerDetails = new Intent(Registrationactivity.this,
                            ProviderDashboard.class);
                    startActivity(providerDetails);
                  }


                }
              } else if (verifyOtpResponseDataModel.getStatus() == 400) {
                UiUtils.showAlert(activity, getString(R.string.app_name), getString(R.string.otp_failed));
              }
            } else {
              UiUtils.showAlert(activity, getString(R.string.app_name), getString(R.string.otp_failed));
            }

          });
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

  // method will validate the fields
  private boolean isotpVerifiedValidation() {
    if (TextUtils.isEmpty(providerPhoneNumber.getText().toString())) {
      UiUtils.showToast(this, getString(R.string.alert_create_phone));
      return false;
    } else if (TextUtils.isEmpty(otpField.getText().toString())) {
      UiUtils.showToast(this, getString(R.string.alert_create_otpvalidation));
      return false;
    } else if (checkTerms.isChecked() == false) {
      UiUtils.showToast(this, getString(R.string.alert_terms_condition));
      return false;
    } else {
      return true;
    }
  }





}