package com.dev.pigeonproviderapp.ActivityAll.OTPSection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dev.pigeonproviderapp.ActivityAll.OrderdetailsSection.OrderDetails;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.httpRequest.OrderItemOTPVerifyModel;
import com.dev.pigeonproviderapp.httpRequest.ProfileUpdateAPI;
import com.dev.pigeonproviderapp.viewmodel.OrderListViewModel;
import com.google.android.gms.maps.OnMapReadyCallback;

public class OtpVerificationActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView back;
    private TextView OTPverify;
    private EditText getOTP;

    OrderListViewModel orderListViewModel;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        back=findViewById(R.id.img_back);
        OTPverify=findViewById(R.id.tv_verify);
        getOTP=findViewById(R.id.et_OTP);

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
                if (isValid())
                {
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
            orderItemOTPVerifyModel.setSignature("");


        orderListViewModel.verifyOTPData(orderItemOTPVerifyModel).observe(this, orderListViewModel -> {

             dialog.dismiss();
            });
        }

    // method will validate the fields
    private boolean isValid() {
        if (TextUtils.isEmpty(getOTP.getText().toString())) {
            UiUtils.showToast(this, getString(R.string.aleart_otp));
            return false;
        }  else {
            return true;
        }
    }




}