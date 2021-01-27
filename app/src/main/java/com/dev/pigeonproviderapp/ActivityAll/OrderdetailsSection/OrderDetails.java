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
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dev.pigeonproviderapp.ActivityAll.ProviderRating.RatingActivity;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.datamodel.ListOrderResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.OrderDetailsResponseDatamodel;
import com.dev.pigeonproviderapp.httpRequest.ProfileUpdateAPI;
import com.dev.pigeonproviderapp.storage.Singleton;
import com.dev.pigeonproviderapp.view.Adapter.OrderDetails.OrderDetailsAdapter;
import com.dev.pigeonproviderapp.view.Dataprovider.DeliveryPointListingDatamodel;
import com.dev.pigeonproviderapp.viewmodel.OrderListViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class OrderDetails extends AppCompatActivity implements OnMapReadyCallback,View.OnClickListener {

    private Activity activity = OrderDetails.this;

    com.google.android.gms.maps.GoogleMap mMap;
    SupportMapFragment mapFragment;
    private LinearLayout back,moveProviderRating,mainLayout,startOrder,acceptOrder,redirectRatingScreen,pickuppointViewLinear;
    private TextView pickupStatus,pickupAddress,orderWeight,paymentStatus;
    private int pickupPointID;
    private String pickupPointAddress,pickuPointPaymentStatus,orderStatus,pickupTime,pickupComment;


    OrderListViewModel orderListViewModel;

    private Dialog dialog;
    private RecyclerView orderDetailsListing_recyclerview;
    private ArrayList<DeliveryPointListingDatamodel> order_detailsList_arraylist = new ArrayList<>();
    private OrderDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order_details);

        back=findViewById(R.id.ll_back);
        moveProviderRating=findViewById(R.id.ll_moveRatingScreen);
        pickupStatus=findViewById(R.id.tv_pickup_status);
        pickupAddress=findViewById(R.id.tv_pickup_address);
        orderWeight=findViewById(R.id.tv_weight);
        paymentStatus=findViewById(R.id.tv_payment_status);
        mainLayout=findViewById(R.id.ll_main);
        startOrder=findViewById(R.id.ll_start_order_orderdetails);
        acceptOrder=findViewById(R.id.ll_accept_order_orderdetails);
        redirectRatingScreen=findViewById(R.id.ll_moveRatingScreen);
        pickuppointViewLinear=findViewById(R.id.ll_pickup_point_view);


        dialog = UiUtils.showProgress(OrderDetails.this);

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
        pickuppointViewLinear.setOnClickListener(this);
        acceptOrder.setOnClickListener(this);
        startOrder.setOnClickListener(this);

        //Order Details API Call
        getOrderDetails();


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;

            case R.id.ll_moveRatingScreen:
                Intent intent=new Intent(OrderDetails.this, RatingActivity.class);
                startActivity(intent);
                break;

            case R.id.ll_pickup_point_view:
                Singleton.getInstance().setORDERITEMID(pickupPointID);
                Intent itemdetails=new Intent(activity, ItemDetailsActivity.class);
                itemdetails.putExtra("ITEMID",pickupPointID);
                itemdetails.putExtra("TYPE","Pickup Point");
                itemdetails.putExtra("ADDRESS",pickupPointAddress);
                itemdetails.putExtra("PAYMENTSTATUS",pickuPointPaymentStatus);
                itemdetails.putExtra("ORDERSTATUS",orderStatus);
                itemdetails.putExtra("TIME",pickupTime);
                itemdetails.putExtra("COMMENT",pickupComment);
                activity.startActivity(itemdetails);
                break;

            case R.id.ll_accept_order_orderdetails:
                callAcceptOrder();
                break;

            case R.id.ll_start_order_orderdetails:
                callStartOrder();

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

    public void getOrderDetails() {
        dialog.show();
        orderListViewModel.getOrderDetailsData().observe(this, new Observer<OrderDetailsResponseDatamodel>() {
            @Override
            public void onChanged(OrderDetailsResponseDatamodel orderDetailsResponseDatamodel) {
                dialog.dismiss();

                if (orderDetailsResponseDatamodel.getStatus()==200)
                {
                    mainLayout.setVisibility(View.VISIBLE);

                    if (orderDetailsResponseDatamodel.getData().getOrderStatus().getMessage().equals("Is not assigned"))
                    {
                        acceptOrder.setVisibility(View.VISIBLE);
                        startOrder.setVisibility(View.GONE);
                        redirectRatingScreen.setVisibility(View.INVISIBLE);
                    }else {
                        acceptOrder.setVisibility(View.GONE);
                        startOrder.setVisibility(View.VISIBLE);
                        redirectRatingScreen.setVisibility(View.VISIBLE);
                    }

                    pickupStatus.setText(orderDetailsResponseDatamodel.getData().getPickupPoint().getOrderStatus().getMessage());
                    pickupAddress.setText(orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupAddress().getAddress());
                    orderWeight.setText("Weight: Upto "+orderDetailsResponseDatamodel.getData().getWeight()+"KG");
                    paymentStatus.setText(orderDetailsResponseDatamodel.getData().getPayment().getMessage());

                    pickupPointID=orderDetailsResponseDatamodel.getData().getPickupPoint().getId();
                    pickupPointAddress=orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupAddress().getAddress();
                    pickuPointPaymentStatus=orderDetailsResponseDatamodel.getData().getPayment().getMessage();
                    orderStatus=orderDetailsResponseDatamodel.getData().getOrderStatus().getMessage();
                    pickupTime=orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupTime();
                    pickupComment=orderDetailsResponseDatamodel.getData().getPickupPoint().getComments();

                    Singleton.getInstance().setORDERAMOUNT(orderDetailsResponseDatamodel.getData().getPayment().getAmount());


                    DeliveryPointListingDatamodel deliveryPointListingDatamodel = new DeliveryPointListingDatamodel();

                    for (OrderDetailsResponseDatamodel.DropPoint dropPoint : orderDetailsResponseDatamodel.getData().getDropPoints()) {

                        deliveryPointListingDatamodel.orderpoint_name = "Drop Point";
                        deliveryPointListingDatamodel.order_status = orderDetailsResponseDatamodel.getData().getOrderStatus().getMessage();
                        deliveryPointListingDatamodel.delivery_order_id=dropPoint.getId();
                        deliveryPointListingDatamodel.order_point_address =dropPoint.getDropAddress().getAddress();
                        deliveryPointListingDatamodel.payment_status=orderDetailsResponseDatamodel.getData().getPayment().getMessage();
                        deliveryPointListingDatamodel.delivery_time=dropPoint.getDropTime();
                        deliveryPointListingDatamodel.delivery_comments=dropPoint.getComments();
                        order_detailsList_arraylist.add(deliveryPointListingDatamodel);

                        adapter = new OrderDetailsAdapter(activity, order_detailsList_arraylist);
                        orderDetailsListing_recyclerview.setAdapter(adapter);

                    }



                }

            }
        });
    }

    //Call ACcept Prder API
    public void callAcceptOrder() {

        dialog.show();
        orderListViewModel.acceptOrderData().observe(this, orderListViewModel -> {

            dialog.dismiss();
            });

    }

    //Call Start Order API

    public void callStartOrder() {

        dialog.show();
        orderListViewModel.startOrderData().observe(this, orderListViewModel -> {

            dialog.dismiss();
        });

    }






}