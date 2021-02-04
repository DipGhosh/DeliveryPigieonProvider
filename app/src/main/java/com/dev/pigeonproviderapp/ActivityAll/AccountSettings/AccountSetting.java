package com.dev.pigeonproviderapp.ActivityAll.AccountSettings;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dev.pigeonproviderapp.ActivityAll.ProfileEdit;
import com.dev.pigeonproviderapp.Baseclass.BaseActivity;
import com.dev.pigeonproviderapp.R;

public class AccountSetting extends BaseActivity implements View.OnClickListener{

    private Activity activity = AccountSetting.this;

    private ImageView back;
    private LinearLayout bankDetailsSection,documentSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

        back = findViewById(R.id.img_back);
        bankDetailsSection=findViewById(R.id.ll_bankdetails_section);
        documentSection=findViewById(R.id.ll_document_section);

        bankDetailsSection.setOnClickListener(this);
        documentSection.setOnClickListener(this);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId() /*to get clicked view id**/) {
            case R.id.img_back:
                finish();
                break;
            case R.id.ll_bankdetails_section:
                Intent intent=new Intent(AccountSetting.this,AddBankDetails.class);
                startActivity(intent);
                break;
            case R.id.ll_document_section:
                Intent document=new Intent(AccountSetting.this,DocumentListing.class);
                startActivity(document);
                break;
            default:
                break;
        }
    }
}