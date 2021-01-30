package com.dev.pigeonproviderapp.ActivityAll.Map;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dev.pigeonproviderapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class OrderRouteMap extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener{

    com.google.android.gms.maps.GoogleMap mMap;
    SupportMapFragment mapFragment;

    private TextView cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_route_map);

        cancel=findViewById(R.id.tv_cancel);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().
                findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_cancel:
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        Polyline path = googleMap.addPolyline(new PolylineOptions()
                .add(
                        new LatLng(38.893596444352134, -77.0381498336792),
                        new LatLng(38.89337933372204, -77.03792452812195),
                        new LatLng(38.893596444352134, -77.0349633693695)
                )
        );

        // Style the polyline
        path.setWidth(10);
        path.setColor(Color.parseColor("#FF0000"));


        // Position the map's camera
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(38.89399, -77.03659), 16));
    }
}