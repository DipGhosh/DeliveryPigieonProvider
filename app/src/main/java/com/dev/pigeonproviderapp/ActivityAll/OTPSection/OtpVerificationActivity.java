package com.dev.pigeonproviderapp.ActivityAll.OTPSection;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.dev.pigeonproviderapp.ActivityAll.ProviderRegistration.Registrationactivity;
import com.dev.pigeonproviderapp.Baseclass.BaseActivity;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.datamodel.OtpVerifyResponseDataModel;
import com.dev.pigeonproviderapp.httpRequest.OrderItemOTPVerifyModel;

import com.dev.pigeonproviderapp.httpRequest.ProfileUpdateAPI;
import com.dev.pigeonproviderapp.storage.Singleton;
import com.dev.pigeonproviderapp.viewmodel.OrderListViewModel;

public class OtpVerificationActivity extends BaseActivity implements View.OnClickListener {

    OrderListViewModel orderListViewModel;
    private Activity activity = OtpVerificationActivity.this;
    private ImageView back;
    private TextView OTPverify;
    private EditText getOTP;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        back = findViewById(R.id.img_back);
        OTPverify = findViewById(R.id.tv_verify);
        getOTP = findViewById(R.id.et_OTP);

        dialog = UiUtils.showProgress(OtpVerificationActivity.this);

        // ViewModel Object
        orderListViewModel = ViewModelProviders.of(this).get(OrderListViewModel.class);

        back.setOnClickListener(this);
        OTPverify.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_verify:
                if (isValid()) {
                    verifyOrderOtp();
                }
                break;


            default:
                break;

        }
    }



    public void verifyOrderOtp() {

        dialog.show();

        OrderItemOTPVerifyModel orderItemOTPVerifyModel = new OrderItemOTPVerifyModel();
        orderItemOTPVerifyModel.setOtp(getOTP.getText().toString());
        orderItemOTPVerifyModel.setType(Singleton.getInstance().getDROPPOINTTYPE());


        orderListViewModel.verifyOTPData(orderItemOTPVerifyModel).observe(this, new Observer<OtpVerifyResponseDataModel>() {
            @Override
            public void onChanged(OtpVerifyResponseDataModel otpVerifyResponseDataModel) {

                dialog.dismiss();

           /* if (otpVerifyResponseDataModel != null) {
              if (otpVerifyResponseDataModel.getStatus() == 200) {
                UiUtils.showAlert(activity, "Start Order", getString(R.string.alert_otp));
              } else if (otpVerifyResponseDataModel.getStatus() == 400) {
                UiUtils.showAlert(activity, "Start Order", getString(R.string.alert_otp));
              }
            } else {
              UiUtils.showAlert(activity, "Start Order", "Something went wrong");
            }*/
                if (Singleton.getInstance().getERRORSTATUS() == 400) {
                    UiUtils.showAlert(activity, "OTP Verification", "OTP verification failed");
                } else if (Singleton.getInstance().getERRORSTATUS() == 200) {
                    final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
                    builder.setTitle(getResources().getString(R.string.app_name));
                    builder.setIcon(R.mipmap.ic_launcher);
                    builder.setMessage(R.string.alert_otp);
                    builder.setPositiveButton(R.string.aleart_ok,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                   finish();
                                }
                            });
                    final android.app.AlertDialog alert = builder.create();
                    alert.show();

                }
            }
        });
    }

    // method will validate the fields
    private boolean isValid() {
        if (TextUtils.isEmpty(getOTP.getText().toString())) {
            UiUtils.showToast(this, getString(R.string.aleart_otp));
            return false;
        } else {
            return true;
        }
    }


}