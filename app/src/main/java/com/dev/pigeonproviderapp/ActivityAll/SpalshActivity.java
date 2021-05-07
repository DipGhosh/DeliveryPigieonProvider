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
    String value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);

        sharePreference=new SharePreference(SpalshActivity.this);

        new Handler().postDelayed(() -> {
            if (NetworkUtils.isNetworkAvailable(SpalshActivity.this)) {

                if (sharePreference.getLoggdInstaus())
                {
                    if (sharePreference.GetVerified())
                    {
                        Singleton.getInstance().setProfileUpdated(false);

                    }else {

                        Singleton.getInstance().setProfileUpdated(true);
                    }

                    Singleton.getInstance().setTOKEN(sharePreference.getToken());
                    Bundle extras = getIntent().getExtras();
                    if (extras != null)
                    {
                        value = extras.getString("pushnotification");

                    }

                    if (value==null)
                    {
                        Intent intent = new Intent(SpalshActivity.this, ProviderDashboard.class);
                        startActivity(intent);
                        Singleton.getInstance().setMessageType("");
                    }else if (value.equals("support"))
                    {
                        Intent intent = new Intent(SpalshActivity.this, ProviderDashboard.class);
                        startActivity(intent);
                        Singleton.getInstance().setMessageType("support");
                    }else {
                        Intent intent = new Intent(SpalshActivity.this, ProviderDashboard.class);
                        startActivity(intent);
                        Singleton.getInstance().setMessageType("");
                    }


                }else {
                    if (sharePreference.GetVerified())
                    {
                        Singleton.getInstance().setProfileUpdated(false);

                    }else {
                        Singleton.getInstance().setProfileUpdated(true);
                    }
                    Intent intent = new Intent(SpalshActivity.this, Registrationactivity.class);
                    startActivity(intent);
                    Singleton.getInstance().setMessageType("");
                }

            }
        }, 2000);
    }
}