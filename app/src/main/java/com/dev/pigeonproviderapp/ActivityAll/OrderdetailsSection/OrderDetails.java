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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

public class OrderDetails extends AppCompatActivity implements View.OnClickListener {


    ArrayList<LatLng> coordList = new ArrayList<LatLng>();
    ArrayList<String> addressStoreList = new ArrayList<String>();

    OrderListViewModel orderListViewModel;
    int orderPaymentStatus, orderStatus;

    private double pickpoint_lat, pickpoint_long;
    private Activity activity = OrderDetails.this;
    private LinearLayout back, mainLayout, startOrder, acceptOrder, startedOrder, redirectRatingScreen, pickuppointViewLinear, orderCompleted, mapIconClick, paymentInfoLayout;
    private TextView pickupStatus, pickupAddress, orderWeight, paymentStatus, orderPaymentAccept, packageType, totalDistanceNeedtocover, pickupFlatnumber, instructionMessage, earnPrice, bonusPrice,paymentMode,pickupTimeShow;
    private int pickupPointID, orderItemStatus;
    private String pickupPointAddress, pickuPointPaymentStatus, pickupTime, pickupComment, paymentstatusMessage, pickupflatName, pickupAddresstoreach, totalDistanceShowinMap, paymentcollectionpoint, paymentpointaddress,paymentCollectiontime="";
    private long pickupPhonenUmber;
    private Dialog dialog;
    private boolean pickupiscollected;
    private ImageView completeSignImage, paymentCOmpleteSign,cashPayIcon,onlinePayIcon;
    private boolean droppointVerified, imageVerified;
    private RecyclerView orderDetailsListing_recyclerview;
    private ArrayList<DeliveryPointListingDatamodel> order_detailsList_arraylist = new ArrayList<>();
    private OrderDetailsAdapter adapter;
    private Intent getordertypeIntent;
    private Uri uri_redirect_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order_details);

        getordertypeIntent=getIntent();

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
        orderPaymentAccept = findViewById(R.id.tv_order_payment_accept);
        packageType = findViewById(R.id.tv_package_type);
        totalDistanceNeedtocover = findViewById(R.id.tv_total_distance);
        pickupFlatnumber = findViewById(R.id.order_flatNumber);
        instructionMessage = findViewById(R.id.tv_instruction);
        paymentInfoLayout = findViewById(R.id.ll_payment_info);
        earnPrice = findViewById(R.id.tv_earnprice);
        bonusPrice = findViewById(R.id.tvbonus_bounus);
        completeSignImage = findViewById(R.id.et_complete_sign);
        paymentCOmpleteSign = findViewById(R.id.et_payment_complete_sign);
        paymentMode=findViewById(R.id.tv_paymentMode);
        cashPayIcon=findViewById(R.id.ic_cash_payment);
        onlinePayIcon=findViewById(R.id.ic_online_payment);
        pickupTimeShow=findViewById(R.id.tv_pickup_time);


        dialog = UiUtils.showProgress(OrderDetails.this);



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
        } else {
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

                if (Singleton.getInstance().getORDERSTATUSCODE() == 5) {
                    Intent intent = new Intent(OrderDetails.this, RatingActivity.class);
                    startActivity(intent);
                } else {
                    UiUtils.showAlert(activity, getString(R.string.app_name), getString(R.string.rate_app_validation));
                }

                break;

            case R.id.ll_pickup_point_view:
                Singleton.getInstance().setORDERITEMID(pickupPointID);
                Singleton.getInstance().setORDERITEMSTATUS(orderItemStatus);
                Singleton.getInstance().setPHONENUMBER(pickupPhonenUmber);
                Singleton.getInstance().setItemcomplete(false);
                Singleton.getInstance().setCollectPayment(pickupiscollected);
                Singleton.getInstance().setDropotpVerified(droppointVerified);
                Singleton.getInstance().setDropImageVerified(imageVerified);

                Intent itemdetails = new Intent(activity, ItemDetailsActivity.class);
                itemdetails.putExtra(Utility.DROPPOINT_TYPE, Utility.PICK_POINT_KEY);
                itemdetails.putExtra(Utility.ADDRESS_KEY, pickupPointAddress);
                itemdetails.putExtra(Utility.TIME_KEY, pickupTime);
                itemdetails.putExtra(Utility.COMMENT_KEY, pickupComment);
                itemdetails.putExtra(Utility.LAT_KEY, pickpoint_lat);
                itemdetails.putExtra(Utility.LONG_KEY, pickpoint_long);
                itemdetails.putExtra(Utility.FLATNAME_KEY, pickupflatName);
                itemdetails.putExtra(Utility.REACHADDRESS_KEY, pickupAddresstoreach);
                activity.startActivity(itemdetails);
                break;

            case R.id.ll_accept_order_orderdetails:

                if (orderStatus == 1) {
                    dialogue();
                }

                break;

            case R.id.ll_start_order_orderdetails:


                if (NetworkUtils.isNetworkAvailable(activity)) {
                    setStartOrderDialogueShow();
                } else {
                    UiUtils.showToast(this, getString(R.string.network_error));
                }
                break;

            case R.id.ll_map_icon_click:

                Intent intent = new Intent(Intent.ACTION_VIEW, uri_redirect_map);
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);

                break;

            case R.id.tv_order_payment_accept:

                if (orderStatus == 1 && orderPaymentStatus == 0) {
                    UiUtils.showAlert(activity, getString(R.string.payment_header), getString(R.string.accept_order_before_payment));
                } else if (orderStatus == 2 && orderPaymentStatus == 0) {
                    UiUtils.showAlert(activity, getString(R.string.payment_header), getString(R.string.order_start_before_payment));
                } else if (orderStatus == 2 && orderPaymentStatus == 1) {
                    UiUtils.showAlert(activity, getString(R.string.payment_header), getString(R.string.order_start_before_payment));
                } else if (orderStatus == 4 && orderPaymentStatus == 1) {
                    UiUtils.showAlert(activity, getString(R.string.payment_header), getString(R.string.order_start_before_payment));
                } else if (orderPaymentStatus == 1) {
                    if (orderStatus == 3 || orderStatus == 5) {
                        if (NetworkUtils.isNetworkAvailable(activity)) {
                            acceptOrderPaymentByProvider();
                        } else {
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
    public void onResume() {
        super.onResume();
        //OnResume Fragment

        if (Singleton.getInstance().isItemcomplete() == true) {
            if (NetworkUtils.isNetworkAvailable(activity)) {
                getOrderDetails();
                Singleton.getInstance().setItemcomplete(false);
            } else {
                UiUtils.showToast(this, getString(R.string.network_error));
            }

        } else if (Singleton.getInstance().isALLDROPPOINTCOMPLETE() == true) {
            finish();
        }

        if (Singleton.getInstance().getPAYMENTSTATUS() == 3) {
            orderPaymentStatus = 3;
            //AllFieldVisibility();
            paymentStatus.setText(getString(R.string.accepted_payment));
            orderPaymentAccept.setText(getString(R.string.payment_complete));
            paymentCOmpleteSign.setVisibility(View.VISIBLE);
            Singleton.getInstance().setPAYMENTSTATUSMESSAGE(getString(R.string.accepted_payment));
        }

        if (Singleton.getInstance().isOtpverified() || Singleton.getInstance().isIdSignatureVerified()) {
            if (NetworkUtils.isNetworkAvailable(activity)) {

                getOrderDetails();

                Singleton.getInstance().setOtpverified(false);
                Singleton.getInstance().setIdSignatureVerified(false);

            } else {
                UiUtils.showToast(this, getString(R.string.network_error));
            }
        }
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

                        if (orderDetailsResponseDatamodel.getData().getPickupPoint().getOrderStatus().getMessage().equals("Completed")) {
                            completeSignImage.setVisibility(View.VISIBLE);
                        } else {
                            completeSignImage.setVisibility(View.GONE);
                        }

                        pickupAddress.setText(orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupAddress().getAddress());
                        addressStoreList.add(orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupAddress().getAddress());

                        if (getordertypeIntent.getStringExtra("TypeOfOrder").equals("pastorder"))
                        {
                            pickupTimeShow.setVisibility(View.GONE);
                        }else {
                            pickupTimeShow.setText(orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupDateNew()+" "+orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupTime());

                        }

                        int originalWeight=orderDetailsResponseDatamodel.getData().getWeight()-2;
                        orderWeight.setText("Weight: Upto " + originalWeight+"-" +orderDetailsResponseDatamodel.getData().getWeight()+ " KG");

                        packageType.setText(orderDetailsResponseDatamodel.getData().getPackageTypes());
                        totalDistanceNeedtocover.setText("Total Distance: " + orderDetailsResponseDatamodel.getData().getDistance() + " KM");
                        totalDistanceShowinMap = String.valueOf(orderDetailsResponseDatamodel.getData().getDistance());
                        earnPrice.setText("Earn: " + "₹ " + orderDetailsResponseDatamodel.getData().getAmountEarn());

                        if (orderDetailsResponseDatamodel.getData().getProviderBonus() <= 0) {
                            bonusPrice.setVisibility(View.GONE);
                        } else {
                            bonusPrice.setText("Bonus: " + "₹ " + orderDetailsResponseDatamodel.getData().getProviderBonus());
                        }

                        if (orderDetailsResponseDatamodel.getData().getPayment().getMode().equals("Cash"))
                        {
                            paymentMode.setText("Cash Payment");
                            cashPayIcon.setVisibility(View.VISIBLE);
                        }else {
                            paymentMode.setText("Online Payment");
                            onlinePayIcon.setVisibility(View.VISIBLE);
                        }



                        //show payment status message in order details screen
                        paymentstatusMessage = orderDetailsResponseDatamodel.getData().getPayment().getMessage();
                        paymentpointaddress = orderDetailsResponseDatamodel.getData().getPayment().getAddress();


                        //store payment status in local variable
                        orderPaymentStatus = orderDetailsResponseDatamodel.getData().getPayment().getStatus();

                        //Payment collection point
                        paymentcollectionpoint = orderDetailsResponseDatamodel.getData().getPaymentPoint();

                        if (orderDetailsResponseDatamodel.getData().getPayment().getPaymentTime()!=null)
                        {
                            paymentCollectiontime=" at "+orderDetailsResponseDatamodel.getData().getPayment().getPaymentTime();

                            Singleton.getInstance().setCashcollectiontime(paymentCollectiontime);
                        }
                        Singleton.getInstance().setPAYMENTPOINT(paymentcollectionpoint);
                        Singleton.getInstance().setPAYMENTSTATUSMESSAGE(paymentstatusMessage);
                        Singleton.getInstance().setPAYMENTSTATUS(orderPaymentStatus);


                        if (orderDetailsResponseDatamodel.getData().getInstruction() != null) {
                            instructionMessage.setText(getString(R.string.special_instruction_heading) + " " + orderDetailsResponseDatamodel.getData().getInstruction());
                        } else {
                            instructionMessage.setVisibility(View.GONE);
                        }
                        //Set data in pickup points view
                        pickupPointID = orderDetailsResponseDatamodel.getData().getPickupPoint().getId();
                        pickupPointAddress = orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupAddress().getAddress();
                        pickuPointPaymentStatus = orderDetailsResponseDatamodel.getData().getPayment().getMessage();
                        orderItemStatus = orderDetailsResponseDatamodel.getData().getPickupPoint().getOrderStatus().getStatus();
                        pickupTime = orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupTime();
                        pickupComment = orderDetailsResponseDatamodel.getData().getInstruction();
                        pickupPhonenUmber = orderDetailsResponseDatamodel.getData().getPickupPoint().getPhone();
                        pickupflatName = orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupAddress().getPickupFlatname();
                        pickupAddresstoreach = orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupAddress().getPickupReachaddressNote();
                        pickupiscollected = orderDetailsResponseDatamodel.getData().getPickupPoint().getIsCollectPayment();
                        droppointVerified = orderDetailsResponseDatamodel.getData().getPickupPoint().getIsOtpVerified();
                        imageVerified = orderDetailsResponseDatamodel.getData().getPickupPoint().getIsSignatureVerified();



                        //Client requirement was untill order will be not accepted by the provider flat number will be not showing
                        if (orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupAddress().getPickupFlatname() != null && orderDetailsResponseDatamodel.getData().getOrderStatus().getStatus() >1)
                        {
                            pickupFlatnumber.setVisibility(View.VISIBLE);
                            pickupFlatnumber.setText(orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupAddress().getPickupFlatname());
                        } else {
                            pickupFlatnumber.setVisibility(View.GONE);
                        }

                        pickpoint_lat = orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupAddress().getLat();
                        pickpoint_long = orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupAddress().getLong();

                        Singleton.getInstance().setORDERAMOUNT(orderDetailsResponseDatamodel.getData().getPayment().getAmount());

                        // add  coordinates to polyline draw for pickup point
                        coordList.add(new LatLng(orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupAddress().getLat(), orderDetailsResponseDatamodel.getData().getPickupPoint().getPickupAddress().getLong()));


                        order_detailsList_arraylist.clear();

                        for (OrderDetailsResponseDatamodel.DropPoint dropPoint : orderDetailsResponseDatamodel.getData().getDropPoints()) {

                            DeliveryPointListingDatamodel deliveryPointListingDatamodel = new DeliveryPointListingDatamodel();

                            deliveryPointListingDatamodel.orderpoint_name = getString(R.string.drop_points);
                            deliveryPointListingDatamodel.order_droppoint_status = dropPoint.getOrderStatus().getStatus();
                            deliveryPointListingDatamodel.delivery_order_id = dropPoint.getId();
                            deliveryPointListingDatamodel.order_point_address = dropPoint.getDropAddress().getAddress();
                            deliveryPointListingDatamodel.payment_status = orderDetailsResponseDatamodel.getData().getPayment().getMessage();
                            deliveryPointListingDatamodel.delivery_time = dropPoint.getDropTime();
                            deliveryPointListingDatamodel.delivery_comments = orderDetailsResponseDatamodel.getData().getInstruction();
                            deliveryPointListingDatamodel.droppoint_status_message = dropPoint.getOrderStatus().getMessage();
                            deliveryPointListingDatamodel.item_phone_number = dropPoint.getPhone();
                            deliveryPointListingDatamodel.droppoint_lat = dropPoint.getDropAddress().getLat();
                            deliveryPointListingDatamodel.droppoint_long = dropPoint.getDropAddress().getLong();
                            deliveryPointListingDatamodel.flatName = dropPoint.getDropAddress().getDropFlatname();
                            deliveryPointListingDatamodel.addressToReach = dropPoint.getDropAddress().getDropReachaddressNote();
                            deliveryPointListingDatamodel.drop_collect_apyment = dropPoint.getIsCollectPayment();
                            deliveryPointListingDatamodel.is_otp_verified = dropPoint.getIsOtpVerified();
                            deliveryPointListingDatamodel.order_status=orderStatus;
                            deliveryPointListingDatamodel.is_signature_verified = dropPoint.getIsSignatureVerified();

                            order_detailsList_arraylist.add(deliveryPointListingDatamodel);
                            // add coordinates to polyline draw for drop point
                            coordList.add(new LatLng(dropPoint.getDropAddress().getLat(), dropPoint.getDropAddress().getLong()));
                            addressStoreList.add(dropPoint.getDropAddress().getAddress());


                        }

                        adapter = new OrderDetailsAdapter(activity, order_detailsList_arraylist);
                        orderDetailsListing_recyclerview.setAdapter(adapter);
                        adapter.notifyDataSetChanged();


                        calMapRouteDraw();

                        AllFieldVisibility();


                    } else {
                        mainLayout.setVisibility(View.GONE);
                    }
                } else {
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
            } else {
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
            } else {
                UiUtils.showAlert(activity, getString(R.string.app_name), Singleton.getInstance().getOTPVERIFYMESSAGE());

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

    public void setStartOrderDialogueShow() {
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
        if (orderStatus == 1) {
            acceptOrder.setVisibility(View.VISIBLE);
            startOrder.setVisibility(View.GONE);
            startedOrder.setVisibility(View.GONE);
            orderCompleted.setVisibility(View.GONE);
            redirectRatingScreen.setVisibility(View.INVISIBLE);
            paymentInfoLayout.setVisibility(View.INVISIBLE);


        } else if (orderStatus == 3) {
            acceptOrder.setVisibility(View.GONE);
            startOrder.setVisibility(View.GONE);
            startedOrder.setVisibility(View.VISIBLE);
            orderCompleted.setVisibility(View.GONE);
            redirectRatingScreen.setVisibility(View.VISIBLE);
            paymentInfoLayout.setVisibility(View.VISIBLE);


        } else if (orderStatus == 5) {
            acceptOrder.setVisibility(View.GONE);
            startOrder.setVisibility(View.GONE);
            startedOrder.setVisibility(View.GONE);
            orderCompleted.setVisibility(View.VISIBLE);
            redirectRatingScreen.setVisibility(View.VISIBLE);
            paymentInfoLayout.setVisibility(View.VISIBLE);


        } else if (orderStatus == 2) {
            acceptOrder.setVisibility(View.GONE);
            startOrder.setVisibility(View.VISIBLE);
            startedOrder.setVisibility(View.GONE);
            orderCompleted.setVisibility(View.GONE);
            redirectRatingScreen.setVisibility(View.INVISIBLE);
            paymentInfoLayout.setVisibility(View.VISIBLE);

        }

        if (orderPaymentStatus == 1) {
            paymentStatus.setText(paymentstatusMessage + " : " + "₹" + Singleton.getInstance().getORDERAMOUNT() + " " + "at " + paymentpointaddress);
            orderPaymentAccept.setText(getString(R.string.accept_payment));
            paymentCOmpleteSign.setVisibility(View.GONE);

        } else if (orderPaymentStatus == 2) {
            //paymentStatus.setText(getString(R.string.alert_complete_payment_msg)+" "+ Singleton.getInstance().getORDERAMOUNT());
            paymentStatus.setText(paymentstatusMessage+paymentCollectiontime);
            orderPaymentAccept.setText(getString(R.string.payment_complete));
            paymentCOmpleteSign.setVisibility(View.VISIBLE);

        } else if (orderPaymentStatus == 3) {
            paymentStatus.setText(paymentstatusMessage+ paymentCollectiontime);
            orderPaymentAccept.setText(getString(R.string.payment_complete));
            paymentCOmpleteSign.setVisibility(View.VISIBLE);

        } else if (orderPaymentStatus == 0) {
            paymentStatus.setText(paymentstatusMessage + " : " + "₹" + Singleton.getInstance().getORDERAMOUNT() + " " + "at " + paymentpointaddress);
            orderPaymentAccept.setText(getString(R.string.accept_payment));
            paymentCOmpleteSign.setVisibility(View.GONE);

        }
    }

    public void acceptOrderPaymentByProvider() {

        dialog.show();

        AcceptPaymentAPIModel acceptPaymentAPIModel = new AcceptPaymentAPIModel();
        acceptPaymentAPIModel.setOrderid(Singleton.getInstance().getORDERID());


        orderListViewModel.paymentAcceptData(acceptPaymentAPIModel).observe(this, acceptPaymentResponseModel -> {

            dialog.dismiss();



            if(Singleton.getInstance().getERRORSTATUS()==200)
            {
                orderPaymentStatus = 3;
                Singleton.getInstance().setPAYMENTSTATUS(orderPaymentStatus);
                orderPaymentAccept.setText(getString(R.string.accepted_payment));
                paymentStatus.setText(getString(R.string.alert_complete_payment_msg) + " " + Singleton.getInstance().getORDERAMOUNT());
                Singleton.getInstance().setCashcollectiontime("");
                Singleton.getInstance().setPAYMENTSTATUSMESSAGE(getString(R.string.alert_complete_payment_msg) + " " + Singleton.getInstance().getORDERAMOUNT());
                paymentCOmpleteSign.setVisibility(View.VISIBLE);

                UiUtils.showAlert(activity, "Payment", getString(R.string.aleart_accept_payment));

            }else if(Singleton.getInstance().getERRORSTATUS()==400)
            {
                UiUtils.showAlert(activity, getString(R.string.app_name),Singleton.getInstance().getOTPVERIFYMESSAGE());
            }


        });
    }

    public void calMapRouteDraw()
    {

        Log.d("TAG", String.valueOf(coordList.size()));

        if (coordList.size()==2)
        {
            uri_redirect_map =Uri.parse("http://maps.google.com/maps?saddr=" + coordList.get(0).latitude + "," + coordList.get(0).longitude+"&daddr=" +  coordList.get(1).latitude + "," + coordList.get(1).longitude + "");
        }
        else if (coordList.size()==3)
        {
            uri_redirect_map =Uri.parse("http://maps.google.com/maps?saddr=" + coordList.get(0).latitude + "," + coordList.get(0).longitude+"&daddr=" +  coordList.get(1).latitude + "," + coordList.get(1).longitude + "+to:"+  coordList.get(2).latitude + "," + coordList.get(2).longitude+"");

        }else if (coordList.size()==4)
        {
            uri_redirect_map =Uri.parse("http://maps.google.com/maps?saddr=" + coordList.get(0).latitude + "," + coordList.get(0).longitude+"&daddr=" +  coordList.get(1).latitude + "," + coordList.get(1).longitude + "+to:"+  coordList.get(2).latitude + "," + coordList.get(2).longitude+ "+to:"+  coordList.get(3).latitude + "," + coordList.get(3).longitude+"");

        }




    }


}