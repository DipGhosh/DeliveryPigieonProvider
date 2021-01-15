package com.dev.pigeonproviderapp.ActivityAll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.dev.pigeonproviderapp.ActivityAll.ProviderRegistration.ProviderDetails;
import com.dev.pigeonproviderapp.ActivityAll.ProviderRegistration.Registrationactivity;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.NetworkUtils;
import com.dev.pigeonproviderapp.storage.K;
import com.dev.pigeonproviderapp.storage.ShareP;

public class SpalshActivity extends AppCompatActivity {
    ShareP shareP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);
        shareP=new ShareP(SpalshActivity.this);
        new Handler().postDelayed(() -> {
            if(NetworkUtils.isNetworkAvailable(SpalshActivity.this)) {
                if (shareP.getLoggdInstaus()) {
                    Intent intent=new Intent(SpalshActivity.this, ProviderDetails.class);
                    startActivity(intent);

                } else {
                    Intent intent=new Intent(SpalshActivity.this, Registrationactivity.class);
                    startActivity(intent);
                }
            }
        }, 2000);
    }
}