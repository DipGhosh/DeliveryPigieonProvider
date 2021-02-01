package com.dev.pigeonproviderapp.ActivityAll.OrderdetailsSection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dev.pigeonproviderapp.ActivityAll.Map.OrderRouteMap;
import com.dev.pigeonproviderapp.ActivityAll.ProviderRating.RatingActivity;
import com.dev.pigeonproviderapp.ActivityAll.ProviderRegistration.Registrationactivity;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.GPSTracker;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.datamodel.ListOrderResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.OrderDetailsResponseDatamodel;
import com.dev.pigeonproviderapp.httpRequest.AcceptPaymentAPIModel;
import com.dev.pigeonproviderapp.httpRequest.ProfileUpdateAPI;
import com.dev.pigeonproviderapp.storage.Singleton;
import com.dev.pigeonproviderapp.view.Adapter.ActiveOrder.ActiveOrderListAdapter;
import com.dev.pigeonproviderapp.view.Adapter.OrderDetails.OrderDetailsAdapter;
import com.dev.pigeonproviderapp.view.Dataprovider.DeliveryPointListingDatamodel;
import com.dev.pigeonproviderapp.viewmodel.OrderListViewModel;
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
import java.util.List;

public class OrderDetails extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    com.google.android.gms.maps.GoogleMap mMap;
    SupportMapFragment mapFragment;
    static LatLng co_ordinate;
    ArrayList<LatLng> coordList = new ArrayList<LatLng>();
    private double pickpoint_lat,pickpoint_long;

    OrderListViewModel orderListViewModel;

    private Activity activity = OrderDetails.this;
    private LinearLayout back, mainLayout, startOrder, acceptOrder, startedOrder, redirectRatingScreen, pickuppointViewLinear, orderCompleted, mapIconClick;
    private TextView pickupStatus, pickupAddress, orderWeight, paymentStatus,orderPaymentAccept;
    private int pickupPointID,orderItemStatus;
    private String pickupPointAddress, pickuPointPaymentStatus, pickupTime, pickupComment, orderStatus;
    private long pickupPhonenUmber;
    private Dialog dialog;
    int orderPaymentStatus;


    private RecyclerView orderDetailsListing_recyclerview;
    private ArrayList<DeliveryPointListingDatamodel> order_detailsList_arraylist = new ArrayList<>();
    private OrderDetailsAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order_details);

        back = findViewById(R.id.ll_back);
        pickupStatus = findViewById(R.id.tv_pickup_status);
        pickupAddress = findViewById(R.id.tv_pickup_address);
        orderWeight = findViewById(R.id.tv_weight);
        paymentStatus = findViewById(R.id.tv_payment_status);
        mainLayout = findViewById(R.id.ll_main);
        startOrder = findViewById(R.id.ll_start_order_orderdetails);
        acceptOrder = findViewById(R.id.ll_accept_order_orderdetails);
        redirectRatingScreen = findViewById(R.id.ll_moveRatingScreen);
        pickuppointViewLinear = findViewById(R.id.ll_pickup_point_view);
        startedOrder = findViewById(R.id.ll_started_order);
        orderCompleted = findViewById(R.id.ll_completed_order);
        mapIconClick = findViewById(R.id.ll_map_icon_click);
        orderPaymentAccept=findViewById(R.id.tv_order_payment_accept);



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
        redirectRatingScreen.setOnClickListener(this);
        pickuppointViewLinear.setOnClickListener(this);
        acceptOrder.setOnClickListener(this);
        startOrder.setOnClickListener(this);
        mapIconClick.setOnClickListener(this);
        orderPaymentAccept.setOnClickListener(this);



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
                Intent intent = new Intent(OrderDetails.this, RatingActivity.class);
                startActivity(intent);
                break;

            case R.id.ll_pickup_point_view:
                Singleton.getInstance().setORDERITEMID(pickupPointID);
                Singleton.getInstance().setORDERITEMSTATUS(orderItemStatus);
                Singleton.getInstance().setPHONENUMBER(pickupPhonenUmber);
                Singleton.getInstance().setItemcomplete(false);
                Intent itemdetails = new Intent(activity, ItemDetailsActivity.class);
                itemdetails.putExtra("ITEMID", pickupPointID);
                itemdetails.putExtra("TYPE", "Pickup Point");
                itemdetails.putExtra("ADDRESS", pickupPointAddress);
                itemdetails.putExtra("PAYMENTSTATUS", pickuPointPaymentStatus);
                itemdetails.putExtra("TIME", pickupTime);
                itemdetails.putExtra("COMMENT", pickupComment);
                itemdetails.putExtra("lat", pickpoint_lat);
                itemdetails.putExtra("long", pickpoint_long);
                activity.startActivity(itemdetails);
                break;

            case R.id.ll_accept_order_orderdetails:

                dialogue();
                break;

            case R.id.ll_start_order_orderdetails:
                callStartOrder();
                break;

            case R.id.ll_map_icon_click:

                Intent mapRoute = new Intent(OrderDetails.this, OrderRouteMap.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("coordinates",coordList);
                mapRoute.putExtras(bundle);
                startActivity(mapRoute);
                break;

            case R.id.tv_order_payment_accept:

                if (orderPaymentStatus==2)
                {
                    acceptOrderPaymentByProvider();
                }

                break;
            default:
                break;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        //OnResume Fragment

        if (Singleton.getInstance().isItemcomplete()==true)
        {
            getOrderDetails();
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

                if (orderDetailsResponseDatamodel.getStatus() == 200) {
                    mainLayout.setVisibility(View.VISIBLE);

                    orderStatus = orderDetailsResponseDatamodel.getData().getOrderStatus().getMessage();

                    orderPaymentStatus=orderDetailsResponseDatamodel.getData().getPayment().getStatus();
                    Singleton.getInstance().setPAYMENTSTATUS(orderDetailsResponseDatamodel.getData().getPayment().getStatus());

                    //Check Pickuppoint status
                    if(orderDetailsResponseDatamodel.getData().getPickupPoint().getOrderStatus().getStatus()==5)
                    {
                        pickupStatus.setText("Complete");
                    }else {
                        pickupStatus.setText("Pending");
                    }

                    pickupAddress.setText(orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupAddress().getAddress());
                    orderWeight.setText("Weight: Upto " + orderDetailsResponseDatamodel.getData().getWeight() + "KG");
                    paymentStatus.setText(orderDetailsResponseDatamodel.getData().getPayment().getMessage());

                    pickupPointID = orderDetailsResponseDatamodel.getData().getPickupPoint().getId();
                    pickupPointAddress = orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupAddress().getAddress();
                    pickuPointPaymentStatus = orderDetailsResponseDatamodel.getData().getPayment().getMessage();
                    orderItemStatus = orderDetailsResponseDatamodel.getData().getPickupPoint().getOrderStatus().getStatus();
                    pickupTime = orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupTime();
                    pickupComment = orderDetailsResponseDatamodel.getData().getPickupPoint().getComments();
                    pickupPhonenUmber = orderDetailsResponseDatamodel.getData().getPickupPoint().getPhone();

                    pickpoint_lat=orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupAddress().getLat();
                    pickpoint_long=orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupAddress().getLong();

                    Singleton.getInstance().setORDERAMOUNT(orderDetailsResponseDatamodel.getData().getPayment().getAmount());

                    // add  coordinates to polyline draw for pickup point
                    coordList.add(new LatLng(orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupAddress().getLat(),orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupAddress().getLong()));

                    order_detailsList_arraylist.clear();

                    DeliveryPointListingDatamodel deliveryPointListingDatamodel = new DeliveryPointListingDatamodel();

                    for (OrderDetailsResponseDatamodel.DropPoint dropPoint : orderDetailsResponseDatamodel.getData().getDropPoints()) {

                        deliveryPointListingDatamodel.orderpoint_name = "Drop Point";
                        deliveryPointListingDatamodel.order_droppoint_status = dropPoint.getOrderStatus().getStatus();
                        deliveryPointListingDatamodel.delivery_order_id = dropPoint.getId();
                        deliveryPointListingDatamodel.order_point_address = dropPoint.getDropAddress().getAddress();
                        deliveryPointListingDatamodel.payment_status = orderDetailsResponseDatamodel.getData().getPayment().getMessage();
                        deliveryPointListingDatamodel.delivery_time = dropPoint.getDropTime();
                        deliveryPointListingDatamodel.delivery_comments = dropPoint.getComments();
                        deliveryPointListingDatamodel.droppoint_status_message=dropPoint.getOrderStatus().getMessage();
                        deliveryPointListingDatamodel.item_phone_number = dropPoint.getPhone();
                        deliveryPointListingDatamodel.droppoint_lat=dropPoint.getDropAddress().getLat();
                        deliveryPointListingDatamodel.droppoint_long=dropPoint.getDropAddress().getLong();

                        order_detailsList_arraylist.add(deliveryPointListingDatamodel);
                        // add coordinates to polyline draw for drop point
                        coordList.add(new LatLng(dropPoint.getDropAddress().getLat(),dropPoint.getDropAddress().getLong()));


                    }

                    adapter = new OrderDetailsAdapter(activity, order_detailsList_arraylist);
                    orderDetailsListing_recyclerview.setAdapter(adapter);


                    calMapRouteDraw();

                    AllFieldVisibility();


                }

            }
        });
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

        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(co_ordinate, 10);
        mMap.animateCamera(yourLocation);
        mMap.moveCamera(yourLocation);


    }

    //Call ACcept Prder API
    public void callAcceptOrder() {

        dialog.show();
        orderListViewModel.acceptOrderData().observe(this, acceptOrderResponseDataModel -> {
            dialog.dismiss();

            if (acceptOrderResponseDataModel.getStatus() == 200) {
                Singleton.getInstance().setOrderaccept(true);
                finish();
            } else {

            }

        });


    }

    //Call Start Order API

    public void callStartOrder() {

        dialog.show();
        orderListViewModel.startOrderData().observe(this, startOrderResponseDataModel -> {
            dialog.dismiss();

            if (startOrderResponseDataModel.getStatus() == 200) {
                UiUtils.showAlert(activity, "Start Order", getString(R.string.aleart_order_start));
                getOrderDetails();
            }
        });

    }

    public void dialogue() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
        builder.setTitle(getResources().getString(R.string.app_name));
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage(R.string.aleart_accept_order);
        builder.setPositiveButton(R.string.label_ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callAcceptOrder();
                    }
                });
        builder.setNegativeButton(R.string.label_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    public void AllFieldVisibility() {
        if (orderStatus.equals("Is not assigned")) {
            acceptOrder.setVisibility(View.VISIBLE);
            startOrder.setVisibility(View.GONE);
            startedOrder.setVisibility(View.GONE);
            orderCompleted.setVisibility(View.GONE);
            redirectRatingScreen.setVisibility(View.INVISIBLE);

            orderPaymentAccept.setText(getString(R.string.accept_payment));
            paymentStatus.setText(getString(R.string.payment_msg_1)+" "+ Singleton.getInstance().getORDERAMOUNT()+" "+getString(R.string.payment_msg_2));

        } else if (orderStatus.equals("Started")) {
            acceptOrder.setVisibility(View.GONE);
            startOrder.setVisibility(View.GONE);
            startedOrder.setVisibility(View.VISIBLE);
            orderCompleted.setVisibility(View.GONE);
            redirectRatingScreen.setVisibility(View.VISIBLE);



        } else if (orderStatus.equals("Completed")) {
            acceptOrder.setVisibility(View.GONE);
            startOrder.setVisibility(View.GONE);
            startedOrder.setVisibility(View.GONE);
            orderCompleted.setVisibility(View.VISIBLE);
            redirectRatingScreen.setVisibility(View.INVISIBLE);


        } else {
            acceptOrder.setVisibility(View.GONE);
            startOrder.setVisibility(View.VISIBLE);
            startedOrder.setVisibility(View.GONE);
            orderCompleted.setVisibility(View.GONE);
            redirectRatingScreen.setVisibility(View.INVISIBLE);

            orderPaymentAccept.setText(getString(R.string.accept_payment));
            paymentStatus.setText(getString(R.string.payment_msg_1)+" "+ Singleton.getInstance().getORDERAMOUNT()+" "+getString(R.string.payment_msg_2));
        }

        if (orderPaymentStatus==1)
        {
            orderPaymentAccept.setText(getString(R.string.accept_payment));
            paymentStatus.setText(getString(R.string.payment_msg_1)+" "+ Singleton.getInstance().getORDERAMOUNT()+" "+getString(R.string.payment_msg_2));

        }else if (orderPaymentStatus==2)
        {
            orderPaymentAccept.setText(getString(R.string.accept_payment));
            paymentStatus.setText(getString(R.string.payment_msg_1)+" "+ Singleton.getInstance().getORDERAMOUNT()+" "+getString(R.string.payment_msg_2));

        }else if (orderPaymentStatus==3)
        {
            orderPaymentAccept.setText(getString(R.string.accepted_payment));
            paymentStatus.setText(getString(R.string.alert_complete_payment_msg)+" "+ Singleton.getInstance().getORDERAMOUNT());
        }
    }

    public void acceptOrderPaymentByProvider() {

        dialog.show();

        AcceptPaymentAPIModel acceptPaymentAPIModel = new AcceptPaymentAPIModel();
        acceptPaymentAPIModel.setOrderid(Singleton.getInstance().getORDERID());



        orderListViewModel.paymentAcceptData(acceptPaymentAPIModel).observe(this, acceptPaymentResponseModel -> {

            dialog.dismiss();

            if (acceptPaymentResponseModel.getStatus()==200)
            {
                orderPaymentStatus=3;

                orderPaymentAccept.setText(getString(R.string.accepted_payment));
                paymentStatus.setText(getString(R.string.alert_complete_payment_msg)+" "+ Singleton.getInstance().getORDERAMOUNT());

                UiUtils.showAlert(activity,"Payment",getString(R.string.aleart_accept_payment));
            }
        });
    }

}