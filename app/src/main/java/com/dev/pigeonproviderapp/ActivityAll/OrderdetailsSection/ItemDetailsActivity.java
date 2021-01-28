package com.dev.pigeonproviderapp.ActivityAll.OrderdetailsSection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dev.pigeonproviderapp.ActivityAll.OTPSection.ItemDigitalSignature;
import com.dev.pigeonproviderapp.ActivityAll.OTPSection.OtpVerificationActivity;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.httpRequest.AcceptPaymentAPIModel;
import com.dev.pigeonproviderapp.httpRequest.CompleteOrderAPIModel;
import com.dev.pigeonproviderapp.storage.Singleton;
import com.dev.pigeonproviderapp.viewmodel.OrderListViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
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
    private LinearLayout back,verifyOTP,addSignature,completeorderSubmit,orderCompleted;
    private TextView pointName,pointDeliveryTime,pointAddress,paymentStatus,pointDeliveryComment,acceptPaymentByProvider;
    private EditText providerComment;

    OrderListViewModel orderListViewModel;

    private Dialog dialog;
    private String orderType;

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

        dialog = UiUtils.showProgress(ItemDetailsActivity.this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().
                findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // ViewModel Object
        orderListViewModel = ViewModelProviders.of(this).get(OrderListViewModel.class);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            pointName.setText(bundle.getString("TYPE"));
            pointAddress.setText(bundle.getString("ADDRESS"));
            //paymentStatus.setText(bundle.getString("PAYMENTSTATUS"));
            pointDeliveryTime.setText(bundle.getString("TIME"));
            pointDeliveryComment.setText(bundle.getString("COMMENT"));

            if (bundle.getString("TYPE").equals("Pickup Point"))
            {
                orderType="pickup";
            }else {
                orderType="drop";
            }
            paymentStatus.setText(getString(R.string.payment_msg_1)+" "+ Singleton.getInstance().getORDERAMOUNT()+" "+getString(R.string.payment_msg_2));





        }

        back.setOnClickListener(this);
        verifyOTP.setOnClickListener(this);
        addSignature.setOnClickListener(this);
        completeorderSubmit.setOnClickListener(this);
        acceptPaymentByProvider.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_verify_otp:
                Intent intent=new Intent(ItemDetailsActivity.this, OtpVerificationActivity.class);
                startActivity(intent);

                break;
            case R.id.ll_add_signature:
                Intent ordersignature=new Intent(ItemDetailsActivity.this, ItemDigitalSignature.class);
                startActivity(ordersignature);

                break;

            case R.id.ll_order_item_complete:
                completeOrderItem();
                break;

            case R.id.tv_accept_payment_item:
                acceptOrderByProvider();
                break;

            default:
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



/*

        mMap.addMarker(new MarkerOptions()
                .title("Drop Point")
                .position(new LatLng(
                        dropPoint.getDropAddress().getLat(),
                        dropPoint.getDropAddress().getLong()
                ))
        );
        Polyline path = mMap.addPolyline(new PolylineOptions()
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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(dropPoint.getDropAddress().getLat(), dropPoint.getDropAddress().getLong()), 16));
*/



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
            }
        });
    }

    public void acceptOrderByProvider() {

        dialog.show();

        AcceptPaymentAPIModel acceptPaymentAPIModel = new AcceptPaymentAPIModel();
        acceptPaymentAPIModel.setOrderid(Singleton.getInstance().getORDERID());



        orderListViewModel.paymentAcceptData(acceptPaymentAPIModel).observe(this, acceptPaymentResponseModel -> {

            dialog.dismiss();

            if (acceptPaymentResponseModel.getStatus()==200)
            {
                UiUtils.showAlert(activity,"Payment",getString(R.string.aleart_accept_payment));
            }
        });
    }
}