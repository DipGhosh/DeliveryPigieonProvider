package com.dev.pigeonproviderapp.ActivityAll.ProviderRegistration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.dev.pigeonproviderapp.ActivityAll.ProviderDashboard;
import com.dev.pigeonproviderapp.MainActivity;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.activity.BaseActivity;

public class ProviderDetails extends BaseActivity implements View.OnClickListener{
    private Button btnSubmit;
    private ImageView providerImageUpload,adharcardFontsideImageUpload,adharcardBacksideImageUpload,panCardImageupload,otherDetailsImageupload;
    private EditText providerName,providerEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_details);

        btnSubmit=findViewById(R.id.btn_providersubmit);
        providerImageUpload=findViewById(R.id.ic_providerImage_upload);
        adharcardFontsideImageUpload=findViewById(R.id.ic_adharcard_fontsideImageUpload);
        adharcardBacksideImageUpload=findViewById(R.id.ic_adharcard_backsideImageUpload);
        panCardImageupload=findViewById(R.id.ic_pandcardImageUpload);
        otherDetailsImageupload=findViewById(R.id.ic_others_documentImageUpload);
        providerName=findViewById(R.id.et_providerFullName);
        providerEmail=findViewById(R.id.et_providerEmail);
        //Registered click listener
        btnSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId() /*to get clicked view id**/) {
            case R.id.btn_providersubmit:
                Intent intent=new Intent(ProviderDetails.this, ProviderDashboard.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}