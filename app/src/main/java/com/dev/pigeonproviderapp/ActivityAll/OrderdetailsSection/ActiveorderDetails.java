package com.dev.pigeonproviderapp.ActivityAll.OrderdetailsSection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.dev.pigeonproviderapp.ActivityAll.ProviderRating.RatingActivity;
import com.dev.pigeonproviderapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class ActiveorderDetails extends AppCompatActivity implements OnMapReadyCallback {
    com.google.android.gms.maps.GoogleMap mMap;
    SupportMapFragment mapFragment;
    private LinearLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activeorder_details);
        back=findViewById(R.id.ll_back);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().
                findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}