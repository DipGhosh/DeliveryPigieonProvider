package com.dev.pigeonproviderapp.ActivityAll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.dev.pigeonproviderapp.ActivityAll.ProviderRegistration.Registrationactivity;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.NetworkUtils;

public class SpalshActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);
        new Handler().postDelayed(() -> {
            if (NetworkUtils.isNetworkAvailable(SpalshActivity.this)) {
                Intent intent = new Intent(SpalshActivity.this, Registrationactivity.class);
                startActivity(intent);
            }
        }, 2000);
    }
}