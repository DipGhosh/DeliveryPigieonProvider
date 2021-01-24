package com.dev.pigeonproviderapp.ActivityAll.OrderdetailsSection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.dev.pigeonproviderapp.ActivityAll.ProviderRating.RatingActivity;
import com.dev.pigeonproviderapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class CurrentOrderDetails extends AppCompatActivity implements OnMapReadyCallback {
    com.google.android.gms.maps.GoogleMap mMap;
    SupportMapFragment mapFragment;
    private LinearLayout back;
    private LinearLayout itemClick;
    private LinearLayout moveProviderRating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order_details);
        back=findViewById(R.id.ll_back);
        itemClick=findViewById(R.id.ll_item_click);
        moveProviderRating=findViewById(R.id.ll_moveRatingScreen);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().
                findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        itemClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CurrentOrderDetails.this,ItemDetailsActivity.class);
                startActivity(intent);
            }
        });
        moveProviderRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CurrentOrderDetails.this, RatingActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}