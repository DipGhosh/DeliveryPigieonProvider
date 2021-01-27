package com.dev.pigeonproviderapp.ActivityAll.ProviderRegistration;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.dev.pigeonproviderapp.ActivityAll.ProviderDashboard;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.PermissionUtils;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.Utility.Utility;
import com.dev.pigeonproviderapp.Baseclass.BaseActivity;
import com.dev.pigeonproviderapp.datamodel.OTPSendResponseDataModel;
import com.dev.pigeonproviderapp.httpRequest.OTPSendAPIModel;
import com.dev.pigeonproviderapp.httpRequest.VerifyOtpAPIModel;
import com.dev.pigeonproviderapp.storage.SharePreference;
import com.dev.pigeonproviderapp.storage.Singleton;
import com.dev.pigeonproviderapp.viewmodel.OtpSendViewModel;
import com.dev.pigeonproviderapp.viewmodel.VerifyOtpViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;


public class Registrationactivity extends BaseActivity implements View.OnClickListener {

    String MobilePattern = "[0-9]{5}";
    OtpSendViewModel otpSendViewModel;
    VerifyOtpViewModel verifyOtpViewModel;
    private Button btnRegistration;
    private EditText providerPhoneNumber, otpField;
    private TextView getOtp, resendOtp;
    private CheckBox checkTerms;
    private Dialog dialog;
    private SharePreference sharePreference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrationactivity);

        //Check the location permission
        final PermissionUtils permissionUtils = new PermissionUtils(this);
        permissionUtils.checkPermissions();

        dialog = UiUtils.showProgress(Registrationactivity.this);
        sharePreference=new SharePreference(Registrationactivity.this);

        //Find all the views
        btnRegistration = findViewById(R.id.btn_registration);
        providerPhoneNumber = findViewById(R.id.et_phoneNumber);
        otpField = findViewById(R.id.et_otp);
        getOtp = findViewById(R.id.tv_getOtp);
        resendOtp = findViewById(R.id.tv_resendOtp);
        checkTerms = findViewById(R.id.checkTerms);


        otpSendViewModel = ViewModelProviders.of(this).get(OtpSendViewModel.class);
        verifyOtpViewModel = ViewModelProviders.of(this).get(VerifyOtpViewModel.class);




        //Registered click listener
        btnRegistration.setOnClickListener(this);
        getOtp.setOnClickListener(this);
        resendOtp.setOnClickListener(this);

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
                        String token = task.getResult();
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
            dialog.show();
            OTPSendAPIModel otpSendAPIModel = new OTPSendAPIModel();
            otpSendAPIModel.setPhone(providerPhoneNumber.getText().toString());
            otpSendAPIModel.setUserType(2);
            otpSendAPIModel.setDeviceName(Utility.DEVICE_NAME);

            otpSendViewModel.getRegisterData(otpSendAPIModel).observe(this, new Observer<OTPSendResponseDataModel>() {
                @Override
                public void onChanged(OTPSendResponseDataModel otpSendResponseDataModel) {
                    dialog.dismiss();
                    int data = otpSendResponseDataModel.getData();
                    if (data > 0) {
                        getOtp.setVisibility(View.GONE);
                        resendOtp.setVisibility(View.VISIBLE);
                        otpField.setText("" + data);
                    }

                }
            });
        }
    }


    public void CallVerifyOTP() {
        if (isotpVerifiedValidation()) {
            dialog.show();

            VerifyOtpAPIModel verifyOtpAPIModel = new VerifyOtpAPIModel();
            verifyOtpAPIModel.setPhone(providerPhoneNumber.getText().toString());
            verifyOtpAPIModel.setDeviceName(Utility.DEVICE_NAME);
            verifyOtpAPIModel.setOtp(otpField.getText().toString());


            verifyOtpViewModel.getVerifyOtpData(verifyOtpAPIModel).observe(this, verifyOtpResponseDataModel -> {
                if (verifyOtpResponseDataModel.getStatus() == 200) {
                    dialog.dismiss();
                    String token = verifyOtpResponseDataModel.getData().getToken();
                    Log.d("Aslam", token);

                    if (token != "") {

                        Singleton.getInstance().setTOKEN(token);
                        sharePreference.SetIsloogedIn(true);
                        sharePreference.setToken(token);

                        if (verifyOtpResponseDataModel.getData().getUserFirstLogin()==true)
                        {
                            Intent providerDetails = new Intent(Registrationactivity.this, ProviderDetails.class);
                            startActivity(providerDetails);
                        }else {
                            Intent providerDetails = new Intent(Registrationactivity.this, ProviderDashboard.class);
                            startActivity(providerDetails);
                        }


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
        } else if (TextUtils.isEmpty(otpField.getText().toString())) {
            UiUtils.showToast(this, getString(R.string.alert_create_otpvalidation));
            return false;
        }else if (checkTerms.isChecked()==false) {
            UiUtils.showToast(this, getString(R.string.alert_terms_condition));
            return false;
        } else {
            return true;
        }
    }


}