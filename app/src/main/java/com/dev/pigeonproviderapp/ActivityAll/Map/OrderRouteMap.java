package com.dev.pigeonproviderapp.ActivityAll.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dev.pigeonproviderapp.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class OrderRouteMap extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    com.google.android.gms.maps.GoogleMap mMap;
    SupportMapFragment mapFragment;
    ArrayList<LatLng> coordList;
    static LatLng co_ordinate;

    private TextView cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_route_map);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            coordList = getIntent().getParcelableArrayListExtra("coordinates");

        }


        cancel = findViewById(R.id.tv_cancel);


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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);

        calMapRouteDraw();

    }

    public void calMapRouteDraw()
    {

        for (int i = 0; i < coordList.size(); i++)
        {
            // add coordinates to point marker for drop point
            co_ordinate=new LatLng(coordList.get(i).latitude,coordList.get(i).longitude);
            mMap.addMarker(new MarkerOptions().position(co_ordinate)
                    .title("Delivery Pigieon")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }

        //Polyline draw
        PolylineOptions polylineOptions1 = new PolylineOptions();
        polylineOptions1.addAll(coordList);
        polylineOptions1
                .width(10)
                .color(Color.RED).zIndex(90);

        mMap.addPolyline(polylineOptions1);

        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(co_ordinate, 11);
        mMap.animateCamera(yourLocation);
        mMap.moveCamera(yourLocation);

         /*LatLng userLocation = new LatLng(orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupAddress().getLat(),orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupAddress().getLong());
                    //mMap.addMarker(new MarkerOptions().position(new LatLng(dou_lat,dou_long)));
                    mMap.addMarker(new MarkerOptions().position(userLocation).title("Pickup Point"));*/

    }
}