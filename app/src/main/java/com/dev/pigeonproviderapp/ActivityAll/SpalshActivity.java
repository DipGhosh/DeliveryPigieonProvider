package com.dev.pigeonproviderapp.ActivityAll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.dev.pigeonproviderapp.ActivityAll.ProviderRegistration.Registrationactivity;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.NetworkUtils;
import com.dev.pigeonproviderapp.storage.SharePreference;
import com.dev.pigeonproviderapp.storage.Singleton;

public class SpalshActivity extends AppCompatActivity {

    private SharePreference sharePreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);

        sharePreference=new SharePreference(SpalshActivity.this);

        new Handler().postDelayed(() -> {
            if (NetworkUtils.isNetworkAvailable(SpalshActivity.this)) {

                if (sharePreference.getLoggdInstaus())
                {
                    Singleton.getInstance().setTOKEN(sharePreference.getToken());

                    Intent intent = new Intent(SpalshActivity.this, ProviderDashboard.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(SpalshActivity.this, Registrationactivity.class);
                    startActivity(intent);
                }

            }
        }, 2000);
    }
}