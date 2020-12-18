package com.dev.pigeonproviderapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.dev.pigeonproviderapp.activity.BaseActivity;

public class Registrationactivity extends BaseActivity {
    Button btnRegistration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrationactivity);
        btnRegistration=(Button)findViewById(R.id.btn_registration);
        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Registrationactivity.this,ProviderDetails.class);
                startActivity(intent);
            }
        });
    }
}