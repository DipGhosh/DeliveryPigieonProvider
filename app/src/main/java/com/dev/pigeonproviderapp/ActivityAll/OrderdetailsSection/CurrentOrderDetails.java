package com.dev.pigeonproviderapp.ActivityAll.OrderdetailsSection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dev.pigeonproviderapp.ActivityAll.ProviderRating.RatingActivity;
import com.dev.pigeonproviderapp.ActivityAll.ProviderRegistration.Registrationactivity;
import com.dev.pigeonproviderapp.Fragment.OrdersFrag;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.datamodel.ListOrderResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.OrderDetailsResponseDatamodel;
import com.dev.pigeonproviderapp.datamodel.ProfileGetResponseDataModel;
import com.dev.pigeonproviderapp.view.Adapter.OrderDetails.OrderDetailsAdapter;
import com.dev.pigeonproviderapp.view.Dataprovider.OrderDetailsListingDatamodel;
import com.dev.pigeonproviderapp.viewmodel.OrderDetailsViewModel;
import com.dev.pigeonproviderapp.viewmodel.OrderListViewModel;
import com.dev.pigeonproviderapp.viewmodel.OtpSendViewModel;
import com.dev.pigeonproviderapp.viewmodel.ProfileViewModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.tabs.TabLayout;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CurrentOrderDetails extends AppCompatActivity implements OnMapReadyCallback,View.OnClickListener {

    private Activity activity = CurrentOrderDetails.this;

    com.google.android.gms.maps.GoogleMap mMap;
    SupportMapFragment mapFragment;
    private LinearLayout back,moveProviderRating;
    private TextView pickupStatus,pickupAddress;


    OrderListViewModel orderListViewModel;

    private Dialog dialog;
    private RecyclerView orderDetailsListing_recyclerview;
    private ArrayList<OrderDetailsListingDatamodel> order_detailsList_arraylist = new ArrayList<>();
    private OrderDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order_details);

        back=findViewById(R.id.ll_back);
        moveProviderRating=findViewById(R.id.ll_moveRatingScreen);
        pickupStatus=findViewById(R.id.tv_pickup_status);
        pickupAddress=findViewById(R.id.tv_pickup_address);


        dialog = UiUtils.showProgress(CurrentOrderDetails.this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().
                findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // ViewModel Object
        orderListViewModel = ViewModelProviders.of(this).get(OrderListViewModel.class);

        orderDetailsListing_recyclerview = findViewById(R.id.rl_order_details);
        orderDetailsListing_recyclerview.setLayoutManager(new LinearLayoutManager(activity));
        orderDetailsListing_recyclerview
                .addItemDecoration(new DividerItemDecoration(activity, LinearLayoutManager.VERTICAL));

        back.setOnClickListener(this);
        moveProviderRating.setOnClickListener(this);


        getOrderDetails();
        createList();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;

            case R.id.ll_moveRatingScreen:
                Intent intent=new Intent(CurrentOrderDetails.this, RatingActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    public void getOrderDetails() {
        dialog.show();
        orderListViewModel.getOrderDetailsData().observe(this, new Observer<OrderDetailsResponseDatamodel>() {
            @Override
            public void onChanged(OrderDetailsResponseDatamodel orderDetailsResponseDatamodel) {
                dialog.dismiss();

            }
        });
    }

    private void createList() {
        order_detailsList_arraylist = new ArrayList<>();
        for (int i = 0; i <= 1; i++) {
            OrderDetailsListingDatamodel orderDetailsListingDatamodel = new OrderDetailsListingDatamodel();
            if (i == 0) {
                orderDetailsListingDatamodel.orderpoint_name = "PickUp Point";
                orderDetailsListingDatamodel.order_status = "Not Delivered";
                orderDetailsListingDatamodel.order_point_address = "122 Regent Street, Kolkata 700014";

            }
            if (i == 1) {
                orderDetailsListingDatamodel.orderpoint_name = "Drop Point";
                orderDetailsListingDatamodel.order_status = "Not Delivered";
                orderDetailsListingDatamodel.order_point_address = "122 Regent Street, Kolkata 700014";
            }

            order_detailsList_arraylist.add(orderDetailsListingDatamodel);
            adapter = new OrderDetailsAdapter(activity, order_detailsList_arraylist);
            orderDetailsListing_recyclerview.setAdapter(adapter);

        }


    }





}