package com.dev.pigeonproviderapp.ActivityAll.ProviderRegistration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dev.pigeonproviderapp.ActivityAll.ProviderDashboard;
import com.dev.pigeonproviderapp.MainActivity;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.activity.BaseActivity;

public class ProviderDetails extends BaseActivity {
    Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_details);

        btnSubmit=findViewById(R.id.btn_providersubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProviderDetails.this, ProviderDashboard.class);
                startActivity(intent);
            }
        });
    }
}