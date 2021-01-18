package com.dev.pigeonproviderapp.ActivityAll.ProviderRegistration;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.lifecycle.ViewModelProviders;

import com.dev.pigeonproviderapp.ActivityAll.ProviderDashboard;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Baseclass.BaseActivity;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.Utility.Utility;
import com.dev.pigeonproviderapp.httpRequest.ProfileUpdateAPI;
import com.dev.pigeonproviderapp.httpRequest.VerifyOtpAPIModel;
import com.dev.pigeonproviderapp.viewmodel.OtpSendViewModel;
import com.dev.pigeonproviderapp.viewmodel.ProfileViewModel;

public class ProviderDetails extends BaseActivity implements View.OnClickListener {
    ProfileViewModel profileViewModel;
    private Button btnSubmit;
    private ImageView providerImageUpload, adharcardFontsideImageUpload, adharcardBacksideImageUpload, panCardImageupload, otherDetailsImageupload;
    private EditText providerName, providerEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_details);

        btnSubmit = findViewById(R.id.btn_providersubmit);
        providerImageUpload = findViewById(R.id.ic_providerImage_upload);
        adharcardFontsideImageUpload = findViewById(R.id.ic_adharcard_fontsideImageUpload);
        adharcardBacksideImageUpload = findViewById(R.id.ic_adharcard_backsideImageUpload);
        panCardImageupload = findViewById(R.id.ic_pandcardImageUpload);
        otherDetailsImageupload = findViewById(R.id.ic_others_documentImageUpload);
        providerName = findViewById(R.id.et_providerFullName);
        providerEmail = findViewById(R.id.et_providerEmail);

        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        //Registered click listener
        btnSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId() /*to get clicked view id**/) {
            case R.id.btn_providersubmit:
               /* Intent intent=new Intent(ProviderDetails.this, ProviderDashboard.class);
                startActivity(intent);*/
                callProfileInfoUpdate();
                break;
            default:
                break;
        }
    }

    public void callProfileInfoUpdate() {
        if (isValid()) {
            ProfileUpdateAPI profileUpdateAPI = new ProfileUpdateAPI();
            profileUpdateAPI.setName(providerName.getText().toString());
            profileUpdateAPI.setEmail(providerEmail.getText().toString());


            profileViewModel.getProfileUploaddata(profileUpdateAPI).observe(this, profileViewModel -> {
                Intent intent = new Intent(ProviderDetails.this, ProviderDashboard.class);
                startActivity(intent);

            });
        }

    }

    private boolean isValid() {
        if (TextUtils.isEmpty(providerName.getText().toString())) {
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