package com.dev.pigeonproviderapp.ActivityAll.OrderdetailsSection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dev.pigeonproviderapp.ActivityAll.Map.OrderRouteMap;
import com.dev.pigeonproviderapp.ActivityAll.OTPSection.ItemDigitalSignature;
import com.dev.pigeonproviderapp.ActivityAll.OTPSection.OtpVerificationActivity;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.GPSTracker;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.httpRequest.AcceptPaymentAPIModel;
import com.dev.pigeonproviderapp.httpRequest.CompleteOrderAPIModel;
import com.dev.pigeonproviderapp.storage.Singleton;
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

public class ItemDetailsActivity extends AppCompatActivity implements OnMapReadyCallback,View.OnClickListener {

    private Activity activity = ItemDetailsActivity.this;

    com.google.android.gms.maps.GoogleMap mMap;
    SupportMapFragment mapFragment;
    ArrayList<LatLng> coordList = new ArrayList<LatLng>();
    static LatLng co_ordinate;
    GPSTracker gpsTracker;

    private LinearLayout back,verifyOTP,addSignature,completeorderSubmit,orderCompleted;
    private TextView pointName,pointDeliveryTime,pointAddress,paymentStatus,pointDeliveryComment,acceptPaymentByProvider;
    private EditText providerComment;
    private ImageView itemPhoneNumber,mapIconClick;

    OrderListViewModel orderListViewModel;

    private Dialog dialog;
    private String orderType;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        back=findViewById(R.id.ll_back);
        verifyOTP=findViewById(R.id.ll_verify_otp);
        pointName=findViewById(R.id.tv_item_point_name);
        pointDeliveryTime=findViewById(R.id.tv_item_deliverytime);
        pointDeliveryComment=findViewById(R.id.tv_item_comment);
        pointAddress=findViewById(R.id.tv_item_address);
        paymentStatus=findViewById(R.id.tv_item_paymentstatus);
        addSignature=findViewById(R.id.ll_add_signature);
        providerComment=findViewById(R.id.et_provider_comment);
        completeorderSubmit=findViewById(R.id.ll_order_item_complete);
        orderCompleted=findViewById(R.id.ll_order_item_completed);
        acceptPaymentByProvider=findViewById(R.id.tv_accept_payment_item);
        itemPhoneNumber=findViewById(R.id.order_item_phoneNumber);
        mapIconClick=findViewById(R.id.ic_map_icon);

        gpsTracker=new GPSTracker(activity);

        if (gpsTracker.canGetLocation())
        {
            coordList.add(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()));
        }


        dialog = UiUtils.showProgress(ItemDetailsActivity.this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().
                findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // ViewModel Object
        orderListViewModel = ViewModelProviders.of(this).get(OrderListViewModel.class);

        bundle = getIntent().getExtras();

        AllFieldVisibility();


        back.setOnClickListener(this);
        verifyOTP.setOnClickListener(this);
        addSignature.setOnClickListener(this);
        completeorderSubmit.setOnClickListener(this);
        acceptPaymentByProvider.setOnClickListener(this);
        itemPhoneNumber.setOnClickListener(this);
        mapIconClick.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_verify_otp:
                if (Singleton.getInstance().getORDERITEMSTATUS()==2)
                {
                    Intent intent=new Intent(ItemDetailsActivity.this, OtpVerificationActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.ll_add_signature:
                if (Singleton.getInstance().getORDERITEMSTATUS()==2)
                {
                    Intent ordersignature=new Intent(ItemDetailsActivity.this, ItemDigitalSignature.class);
                    startActivity(ordersignature);
                }

                break;

            case R.id.ll_order_item_complete:
                completeOrderItem();
                break;

            case R.id.tv_accept_payment_item:

                if (Singleton.getInstance().getORDERITEMSTATUS()==2)
                {
                    acceptOrderPaymentByProvider();
                }
                break;
            case R.id.order_item_phoneNumber:

                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+Singleton.getInstance().getPHONENUMBER()));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(callIntent);

                break;
            case R.id.ic_map_icon:
                Intent mapRoute = new Intent(ItemDetailsActivity.this, OrderRouteMap.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("coordinates", coordList);
                mapRoute.putExtras(bundle);
                startActivity(mapRoute);
                break;

            default:
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        calMapRouteDraw();




    }

    public void completeOrderItem() {

        dialog.show();

        CompleteOrderAPIModel completeOrderAPIModel = new CompleteOrderAPIModel();
        completeOrderAPIModel.setComment(providerComment.getText().toString());
        completeOrderAPIModel.setType(orderType);


        orderListViewModel.completeOrderData(completeOrderAPIModel).observe(this, completeOrderPointResponseDataModel -> {

            dialog.dismiss();

            if (completeOrderPointResponseDataModel.getStatus()==200)
            {
                completeorderSubmit.setVisibility(View.GONE);
                orderCompleted.setVisibility(View.VISIBLE);

                UiUtils.showAlert(activity,pointName.getText().toString(),getString(R.string.aleart_orderitem_complete) );

                Singleton.getInstance().setItemcomplete(true);

            }
        });
    }

    public void acceptOrderPaymentByProvider() {

        dialog.show();

        AcceptPaymentAPIModel acceptPaymentAPIModel = new AcceptPaymentAPIModel();
        acceptPaymentAPIModel.setOrderid(Singleton.getInstance().getORDERID());



        orderListViewModel.paymentAcceptData(acceptPaymentAPIModel).observe(this, acceptPaymentResponseModel -> {

            dialog.dismiss();

            if (acceptPaymentResponseModel.getStatus()==200)
            {
                acceptPaymentByProvider.setText(getString(R.string.accepted_payment));
                paymentStatus.setText(getString(R.string.alert_complete_payment_msg)+" "+ Singleton.getInstance().getORDERAMOUNT());

                Singleton.getInstance().setPAYMENTSTATUS(3);

                UiUtils.showAlert(activity,"Payment",getString(R.string.aleart_accept_payment));
            }
        });
    }

    public void AllFieldVisibility()
    {


        if (Singleton.getInstance().getORDERITEMSTATUS()==1) {

            completeorderSubmit.setVisibility(View.GONE);
            orderCompleted.setVisibility(View.GONE);


        } else if (Singleton.getInstance().getORDERITEMSTATUS()==2) {
            completeorderSubmit.setVisibility(View.VISIBLE);
            orderCompleted.setVisibility(View.GONE);

        }else if (Singleton.getInstance().getORDERITEMSTATUS()==3) {

            completeorderSubmit.setVisibility(View.GONE);
            orderCompleted.setVisibility(View.VISIBLE);


        }


        if (bundle != null) {
            pointName.setText(bundle.getString("TYPE"));
            pointAddress.setText(bundle.getString("ADDRESS"));
            pointDeliveryTime.setText(bundle.getString("TIME"));
            pointDeliveryComment.setText(bundle.getString("COMMENT"));

            if (bundle.getString("TYPE").equals("Pickup Point"))
            {
                orderType="pickup";
            }else {
                orderType="drop";
            }

            // add  coordinates to polyline draw for pickup point
            coordList.add(new LatLng(bundle.getDouble("lat"), bundle.getDouble("long")));

        }

        if (Singleton.getInstance().getPAYMENTSTATUS()==1)
        {
            acceptPaymentByProvider.setText(getString(R.string.accept_payment));
            paymentStatus.setText(getString(R.string.payment_msg_1)+" "+ Singleton.getInstance().getORDERAMOUNT()+" "+getString(R.string.payment_msg_2));

        }else if (Singleton.getInstance().getPAYMENTSTATUS()==2)
        {
            acceptPaymentByProvider.setText(getString(R.string.accept_payment));
            paymentStatus.setText(getString(R.string.payment_msg_1)+" "+ Singleton.getInstance().getORDERAMOUNT()+" "+getString(R.string.payment_msg_2));


        }else if (Singleton.getInstance().getPAYMENTSTATUS()==3)
        {
            acceptPaymentByProvider.setText(getString(R.string.accepted_payment));
            paymentStatus.setText(getString(R.string.alert_complete_payment_msg)+" "+ Singleton.getInstance().getORDERAMOUNT());
        }



    }

    public void calMapRouteDraw() {

        for (int i = 0; i < coordList.size(); i++) {
            // add coordinates to point marker for drop point
            co_ordinate = new LatLng(coordList.get(i).latitude, coordList.get(i).longitude);
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
}