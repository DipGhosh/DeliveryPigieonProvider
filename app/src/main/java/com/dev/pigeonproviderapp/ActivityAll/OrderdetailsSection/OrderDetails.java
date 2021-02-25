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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dev.pigeonproviderapp.ActivityAll.Map.DataParser;
import com.dev.pigeonproviderapp.ActivityAll.Map.OrderRouteMap;
import com.dev.pigeonproviderapp.ActivityAll.ProviderRating.RatingActivity;
import com.dev.pigeonproviderapp.ActivityAll.ProviderRegistration.Registrationactivity;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.GPSTracker;
import com.dev.pigeonproviderapp.Utility.NetworkUtils;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.Utility.Utility;
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

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderDetails extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    com.google.android.gms.maps.GoogleMap mMap;
    SupportMapFragment mapFragment;
    static LatLng co_ordinate;
    ArrayList<LatLng> coordList = new ArrayList<LatLng>();
    private double pickpoint_lat,pickpoint_long;

    OrderListViewModel orderListViewModel;

    private Activity activity = OrderDetails.this;
    private LinearLayout back, mainLayout, startOrder, acceptOrder, startedOrder, redirectRatingScreen, pickuppointViewLinear, orderCompleted, mapIconClick,paymentInfoLayout;
    private TextView pickupStatus, pickupAddress, orderWeight, paymentStatus,orderPaymentAccept,packageType,totalDistanceNeedtocover,pickupFlatnumber,instructionMessage;
    private int pickupPointID,orderItemStatus;
    private String pickupPointAddress, pickuPointPaymentStatus, pickupTime, pickupComment,paymentstatusMessage,pickupflatName,pickupAddresstoreach,totalDistanceShowinMap,paymentcollectionpoint,paymentpointaddress;
    private long pickupPhonenUmber;
    private Dialog dialog;
    int orderPaymentStatus,orderStatus;
    private boolean pickupiscollected;


    private RecyclerView orderDetailsListing_recyclerview;
    private ArrayList<DeliveryPointListingDatamodel> order_detailsList_arraylist = new ArrayList<>();
    private OrderDetailsAdapter adapter;

    LatLng startLatLng = null;
    LatLng endLatLng = null;



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
        packageType=findViewById(R.id.tv_package_type);
        totalDistanceNeedtocover=findViewById(R.id.tv_total_distance);
        pickupFlatnumber=findViewById(R.id.order_flatNumber);
        instructionMessage = findViewById(R.id.tv_instruction);
        paymentInfoLayout=findViewById(R.id.ll_payment_info);



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
        if (NetworkUtils.isNetworkAvailable(activity)) {
            getOrderDetails();
        }else {
            UiUtils.showToast(this, getString(R.string.network_error));
        }


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;

            case R.id.ll_moveRatingScreen:

                if (Singleton.getInstance().getORDERSTATUSCODE() == 5)
                {
                    Intent intent = new Intent(OrderDetails.this, RatingActivity.class);
                    startActivity(intent);
                }else {
                    UiUtils.showAlert(activity, getString(R.string.app_name), getString(R.string.rate_app_validation));
                }

                break;

            case R.id.ll_pickup_point_view:
                Singleton.getInstance().setORDERITEMID(pickupPointID);
                Singleton.getInstance().setORDERITEMSTATUS(orderItemStatus);
                Singleton.getInstance().setPHONENUMBER(pickupPhonenUmber);
                Singleton.getInstance().setItemcomplete(false);
                Singleton.getInstance().setCollectPayment(pickupiscollected);

                Intent itemdetails = new Intent(activity, ItemDetailsActivity.class);
                itemdetails.putExtra(Utility.DROPPOINT_TYPE, Utility.PICK_POINT_KEY);
                itemdetails.putExtra(Utility.ADDRESS_KEY, pickupPointAddress);
                itemdetails.putExtra(Utility.TIME_KEY, pickupTime);
                itemdetails.putExtra(Utility.COMMENT_KEY, pickupComment);
                itemdetails.putExtra(Utility.LAT_KEY, pickpoint_lat);
                itemdetails.putExtra(Utility.LONG_KEY, pickpoint_long);
                itemdetails.putExtra(Utility.FLATNAME_KEY, pickupflatName);
                itemdetails.putExtra(Utility.REACHADDRESS_KEY,pickupAddresstoreach);
                activity.startActivity(itemdetails);
                break;

            case R.id.ll_accept_order_orderdetails:

                if (orderStatus==1)
                {
                    dialogue();
                }

                break;

            case R.id.ll_start_order_orderdetails:


                if (NetworkUtils.isNetworkAvailable(activity)) {
                    setStartOrderDialogueShow();
                }else {
                    UiUtils.showToast(this, getString(R.string.network_error));
                }
                break;

            case R.id.ll_map_icon_click:

                Intent mapRoute = new Intent(OrderDetails.this, OrderRouteMap.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("coordinates",coordList);
                bundle.putString("distance",totalDistanceShowinMap);
                mapRoute.putExtras(bundle);
                startActivity(mapRoute);

                break;

            case R.id.tv_order_payment_accept:

                if (orderStatus==1&&orderPaymentStatus==0)
                {
                    UiUtils.showAlert(activity,getString(R.string.payment_header),getString(R.string.accept_order_before_payment));
                }else if (orderStatus==2&&orderPaymentStatus==0)
                {
                    UiUtils.showAlert(activity,getString(R.string.payment_header),getString(R.string.order_start_before_payment));
                }else if (orderStatus==2&&orderPaymentStatus==1)
                {
                    UiUtils.showAlert(activity,getString(R.string.payment_header),getString(R.string.order_start_before_payment));
                }else if (orderStatus==4&&orderPaymentStatus==1)
                {
                    UiUtils.showAlert(activity,getString(R.string.payment_header),getString(R.string.order_start_before_payment));
                }else if (orderPaymentStatus==1)
                {
                    if (orderStatus==3||orderStatus==5)
                    {
                        if (NetworkUtils.isNetworkAvailable(activity)) {
                            acceptOrderPaymentByProvider();
                        }else {
                            UiUtils.showToast(this, getString(R.string.network_error));
                        }
                    }

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
            if (NetworkUtils.isNetworkAvailable(activity)) {
                getOrderDetails();
                Singleton.getInstance().setItemcomplete(false);
            }else {
                UiUtils.showToast(this, getString(R.string.network_error));
            }

        }
        else if (Singleton.getInstance().isALLDROPPOINTCOMPLETE()==true)
        {
            finish();
        }

        if (Singleton.getInstance().getPAYMENTSTATUS()==3)
        {
            orderPaymentStatus=3;
            //AllFieldVisibility();
            paymentStatus.setText(getString(R.string.accepted_payment));
            orderPaymentAccept.setText(getString(R.string.payment_complete));
            Singleton.getInstance().setPAYMENTSTATUSMESSAGE(getString(R.string.accepted_payment));
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

                if (orderDetailsResponseDatamodel != null) {

                    if (orderDetailsResponseDatamodel.getStatus() == 200) {
                        mainLayout.setVisibility(View.VISIBLE);

                        //Store the order status
                        orderStatus = orderDetailsResponseDatamodel.getData().getOrderStatus().getStatus();
                        Singleton.getInstance().setORDERSTATUSCODE(orderDetailsResponseDatamodel.getData().getOrderStatus().getStatus());

                        //store order payment status in singletin class
                        Singleton.getInstance().setPAYMENTSTATUS(orderDetailsResponseDatamodel.getData().getPayment().getStatus());

                        //Store user info for rating
                        Singleton.getInstance().setUSERNAME(orderDetailsResponseDatamodel.getData().getUser().getName());
                        Singleton.getInstance().setUSERIMAGE(orderDetailsResponseDatamodel.getData().getUser().getProfileImageUrl());
                        Singleton.getInstance().setORDERRATING(orderDetailsResponseDatamodel.getData().getRating().getRate());
                        Singleton.getInstance().setRATECOMMENT(orderDetailsResponseDatamodel.getData().getRating().getComment());


                        pickupStatus.setText(orderDetailsResponseDatamodel.getData().getPickupPoint().getOrderStatus().getMessage());

                        pickupAddress.setText(orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupAddress().getAddress());

                        //Show order weight & package type
                        if (orderDetailsResponseDatamodel.getData().getWeight()==2.0)
                        {
                            orderWeight.setText("Weight: Upto " + "0-2" + " KG");
                        }else if (orderDetailsResponseDatamodel.getData().getWeight()==4.0)
                        {
                            orderWeight.setText("Weight: Upto " + "2-4" + " KG");
                        }
                        else if (orderDetailsResponseDatamodel.getData().getWeight()==6.0)
                        {
                            orderWeight.setText("Weight: Upto " + "4-6" + " KG");
                        } else if (orderDetailsResponseDatamodel.getData().getWeight()==8.0)
                        {
                            orderWeight.setText("Weight: Upto " + "6-8" + " KG");
                        } else if (orderDetailsResponseDatamodel.getData().getWeight()==10.0)
                        {
                            orderWeight.setText("Weight: Upto " + "8-10" + " KG");
                        } else if (orderDetailsResponseDatamodel.getData().getWeight()==12.0)
                        {
                            orderWeight.setText("Weight: Upto " + "10-12" + " KG");
                        } else if (orderDetailsResponseDatamodel.getData().getWeight()==14.0)
                        {
                            orderWeight.setText("Weight: Upto " + "12-14" + " KG");
                        } else if (orderDetailsResponseDatamodel.getData().getWeight()==16.0)
                        {
                            orderWeight.setText("Weight: Upto " + "14-16" + " KG");
                        } else if (orderDetailsResponseDatamodel.getData().getWeight()==18.0)
                        {
                            orderWeight.setText("Weight: Upto " + "16-18" + " KG");
                        }else if (orderDetailsResponseDatamodel.getData().getWeight()==20.0)
                        {
                            orderWeight.setText("Weight: Upto " + "18-20" + " KG");
                        }

                        packageType.setText(orderDetailsResponseDatamodel.getData().getPackageTypes());
                        totalDistanceNeedtocover.setText("Total Distance: "+orderDetailsResponseDatamodel.getData().getDistance()+" KM");
                        totalDistanceShowinMap= String.valueOf(orderDetailsResponseDatamodel.getData().getDistance());

                        //show payment status message in order details screen
                        paymentstatusMessage=orderDetailsResponseDatamodel.getData().getPayment().getMessage();
                        paymentpointaddress=orderDetailsResponseDatamodel.getData().getPayment().getAddress();


                        //store payment status in local variable
                        orderPaymentStatus=orderDetailsResponseDatamodel.getData().getPayment().getStatus();

                        //Payment collection point
                        paymentcollectionpoint = orderDetailsResponseDatamodel.getData().getPaymentPoint();
                        Singleton.getInstance().setPAYMENTPOINT(paymentcollectionpoint);
                        Singleton.getInstance().setPAYMENTSTATUSMESSAGE(paymentstatusMessage);


                        if (orderDetailsResponseDatamodel.getData().getInstruction() != null) {
                            instructionMessage.setText(getString(R.string.special_instruction_heading)+" "+orderDetailsResponseDatamodel.getData().getInstruction());
                        } else {
                            instructionMessage.setVisibility(View.GONE);
                        }
                        //Set data in pickup points view
                        pickupPointID = orderDetailsResponseDatamodel.getData().getPickupPoint().getId();
                        pickupPointAddress = orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupAddress().getAddress();
                        pickuPointPaymentStatus = orderDetailsResponseDatamodel.getData().getPayment().getMessage();
                        orderItemStatus = orderDetailsResponseDatamodel.getData().getPickupPoint().getOrderStatus().getStatus();
                        pickupTime = orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupTime();
                        pickupComment = orderDetailsResponseDatamodel.getData().getPickupPoint().getComments();
                        pickupPhonenUmber = orderDetailsResponseDatamodel.getData().getPickupPoint().getPhone();
                        pickupflatName=orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupAddress().getPickupFlatname();
                        pickupAddresstoreach=orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupAddress().getPickupReachaddressNote();
                        pickupiscollected=orderDetailsResponseDatamodel.getData().getPickupPoint().getIsCollectPayment();


                        if (orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupAddress().getPickupFlatname()!=null)
                        {
                            pickupFlatnumber.setText(orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupAddress().getPickupFlatname());
                        }else {
                            pickupFlatnumber.setVisibility(View.GONE);
                        }

                        pickpoint_lat=orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupAddress().getLat();
                        pickpoint_long=orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupAddress().getLong();

                        Singleton.getInstance().setORDERAMOUNT(orderDetailsResponseDatamodel.getData().getPayment().getAmount());

                        // add  coordinates to polyline draw for pickup point
                        coordList.add(new LatLng(orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupAddress().getLat(),orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupAddress().getLong()));




                        order_detailsList_arraylist.clear();

                        for (OrderDetailsResponseDatamodel.DropPoint dropPoint : orderDetailsResponseDatamodel.getData().getDropPoints()) {

                            DeliveryPointListingDatamodel deliveryPointListingDatamodel = new DeliveryPointListingDatamodel();

                            deliveryPointListingDatamodel.orderpoint_name = getString(R.string.drop_points);
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
                            deliveryPointListingDatamodel.flatName=dropPoint.getDropAddress().getDropFlatname();
                            deliveryPointListingDatamodel.addressToReach=dropPoint.getDropAddress().getDropReachaddressNote();
                            deliveryPointListingDatamodel.drop_collect_apyment=dropPoint.getIsCollectPayment();

                            order_detailsList_arraylist.add(deliveryPointListingDatamodel);
                            // add coordinates to polyline draw for drop point
                            coordList.add(new LatLng(dropPoint.getDropAddress().getLat(),dropPoint.getDropAddress().getLong()));


                        }

                        adapter = new OrderDetailsAdapter(activity, order_detailsList_arraylist);
                        orderDetailsListing_recyclerview.setAdapter(adapter);
                        adapter.notifyDataSetChanged();


                        calMapRouteDraw();

                        AllFieldVisibility();


                    }else {
                        mainLayout.setVisibility(View.GONE);
                    }
                }else {
                    mainLayout.setVisibility(View.GONE);
                }



            }
        });
    }



    //Call ACcept Prder API
    public void callAcceptOrder() {

        dialog.show();
        orderListViewModel.acceptOrderData().observe(this, acceptOrderResponseDataModel -> {
            dialog.dismiss();
            if (acceptOrderResponseDataModel != null) {
                if (acceptOrderResponseDataModel.getStatus() == 200) {
                    Singleton.getInstance().setOrderaccept(true);
                    getOrderDetails();
                } else {

                }
            }else {
                UiUtils.showAlert(activity, getString(R.string.app_name), getString(R.string.job_already_accepted));

            }

        });


    }

    //Call Start Order API

    public void callStartOrder() {

        dialog.show();
        orderListViewModel.startOrderData().observe(this, startOrderResponseDataModel -> {
            dialog.dismiss();

            if (startOrderResponseDataModel != null) {
                if (startOrderResponseDataModel.getStatus() == 200) {
                    UiUtils.showAlert(activity, "Start Order", getString(R.string.aleart_order_start));
                    getOrderDetails();
                }
            }else {
                UiUtils.showAlert(activity, getString(R.string.app_name), getString(R.string.wrong_data_aleart));

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

    public void setStartOrderDialogueShow()
    {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
        builder.setTitle(getResources().getString(R.string.app_name));
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage(R.string.start_order_aleart);
        builder.setPositiveButton(R.string.label_ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callStartOrder();
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
        if (orderStatus==1) {
            acceptOrder.setVisibility(View.VISIBLE);
            startOrder.setVisibility(View.GONE);
            startedOrder.setVisibility(View.GONE);
            orderCompleted.setVisibility(View.GONE);
            redirectRatingScreen.setVisibility(View.INVISIBLE);
            paymentInfoLayout.setVisibility(View.INVISIBLE);



        } else if (orderStatus==3) {
            acceptOrder.setVisibility(View.GONE);
            startOrder.setVisibility(View.GONE);
            startedOrder.setVisibility(View.VISIBLE);
            orderCompleted.setVisibility(View.GONE);
            redirectRatingScreen.setVisibility(View.VISIBLE);
            paymentInfoLayout.setVisibility(View.VISIBLE);



        } else if (orderStatus==5) {
            acceptOrder.setVisibility(View.GONE);
            startOrder.setVisibility(View.GONE);
            startedOrder.setVisibility(View.GONE);
            orderCompleted.setVisibility(View.VISIBLE);
            redirectRatingScreen.setVisibility(View.VISIBLE);
            paymentInfoLayout.setVisibility(View.VISIBLE);


        } else if (orderStatus==2) {
            acceptOrder.setVisibility(View.GONE);
            startOrder.setVisibility(View.VISIBLE);
            startedOrder.setVisibility(View.GONE);
            orderCompleted.setVisibility(View.GONE);
            redirectRatingScreen.setVisibility(View.INVISIBLE);
            paymentInfoLayout.setVisibility(View.VISIBLE);

        }

        if (orderPaymentStatus==1)
        {
            paymentStatus.setText(paymentstatusMessage+" : "+"₹"+ Singleton.getInstance().getORDERAMOUNT()+ " " + "at "+paymentpointaddress);
            orderPaymentAccept.setText(getString(R.string.accept_payment));

        } else if (orderPaymentStatus==2)
        {
            //paymentStatus.setText(getString(R.string.alert_complete_payment_msg)+" "+ Singleton.getInstance().getORDERAMOUNT());
            paymentStatus.setText(paymentstatusMessage);
            orderPaymentAccept.setText(getString(R.string.payment_complete));

        }else if (orderPaymentStatus==3)
        {
            paymentStatus.setText(paymentstatusMessage);
            orderPaymentAccept.setText(getString(R.string.payment_complete));

        }else if (orderPaymentStatus==0)
        {
            paymentStatus.setText(paymentstatusMessage+" : "+"₹"+ Singleton.getInstance().getORDERAMOUNT()+ " " + "at "+paymentpointaddress);
            orderPaymentAccept.setText(getString(R.string.accept_payment));

        }
    }

    public void acceptOrderPaymentByProvider() {

        dialog.show();

        AcceptPaymentAPIModel acceptPaymentAPIModel = new AcceptPaymentAPIModel();
        acceptPaymentAPIModel.setOrderid(Singleton.getInstance().getORDERID());



        orderListViewModel.paymentAcceptData(acceptPaymentAPIModel).observe(this, acceptPaymentResponseModel -> {

            dialog.dismiss();

            if (acceptPaymentResponseModel != null) {
                if (acceptPaymentResponseModel.getStatus()==200)
                {
                    orderPaymentStatus=3;
                    orderPaymentAccept.setText(getString(R.string.accepted_payment));
                    paymentStatus.setText(getString(R.string.alert_complete_payment_msg)+" "+ Singleton.getInstance().getORDERAMOUNT());

                    UiUtils.showAlert(activity,"Payment",getString(R.string.aleart_accept_payment));
                }
            }else {
                UiUtils.showAlert(activity, getString(R.string.app_name), getString(R.string.wrong_data_aleart));

            }


        });
    }

    public void calMapRouteDraw()
    {

        for (int i = 0; i < coordList.size(); i++)
        {
            // add coordinates to point marker for drop point
            co_ordinate=new LatLng(coordList.get(i).latitude,coordList.get(i).longitude);

            Double lati = coordList.get(i).latitude;
            Double longi = coordList.get(i).longitude;

            mMap.addMarker(new MarkerOptions().position(co_ordinate)
                    .title("Delivery Pigieon")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

            LatLng latlng = new LatLng(lati,
                    longi);
            if (i == 0) {
                startLatLng = latlng;
            }
            if (i == coordList.size() - 1) {
                endLatLng = latlng;
            }

        }



        // Getting URL to the Google Directions API
        String url = getDirectionsUrl(startLatLng, endLatLng);

        DownloadTask downloadTask = new DownloadTask();

        // Start downloading json data from Google Directions API
        downloadTask.execute(url);


        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(startLatLng, 10));


    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }
    }

    /**
     * A class to parse the JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DataParser parser = new DataParser();

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = new ArrayList();
            PolylineOptions lineOptions = new PolylineOptions();

            for (int i = 0; i < result.size(); i++) {

                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(Color.RED);
                lineOptions.geodesic(true);

            }

            // Drawing polyline in the Google Map
            if (points.size() != 0)
                mMap.addPolyline(lineOptions);
        }
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        //setting transportation mode
        String mode = "mode=driving";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest +  "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + "AIzaSyBPosa7I3iL3LGInh5dfNadtCAaPi_41jo";

        return url;
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

}