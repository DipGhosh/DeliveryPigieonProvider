package com.dev.pigeonproviderapp.Fragment.OrderPlacedSection;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dev.pigeonproviderapp.ActivityAll.OrderdetailsSection.OrderDetails;
import com.dev.pigeonproviderapp.ActivityAll.ProviderRegistration.Registrationactivity;
import com.dev.pigeonproviderapp.Baseclass.BaseFragment;
import com.dev.pigeonproviderapp.Fragment.OrdersFrag;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.NetworkUtils;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.datamodel.ListOrderResponseDataModel;
import com.dev.pigeonproviderapp.interfaces.onActiveOrderClickListener;
import com.dev.pigeonproviderapp.storage.SharePreference;
import com.dev.pigeonproviderapp.storage.Singleton;
import com.dev.pigeonproviderapp.view.Adapter.ActiveOrder.ActiveOrderListAdapter;
import com.dev.pigeonproviderapp.view.Dataprovider.OrderActiveDatamodel;
import com.dev.pigeonproviderapp.viewmodel.OrderListViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class ActiveOrdersFrag extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, onActiveOrderClickListener {

    View mview;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private Activity activity;
    private RecyclerView activeorderlist_recyclerview;
    private ArrayList<OrderActiveDatamodel> active_order_arraylist = new ArrayList<>();
    private ActiveOrderListAdapter adapter;
    private ImageView blankImage;
    private Dialog dialog;

    private OrderListViewModel orderListViewModel;
    private ListOrderResponseDataModel listOrderDataModel;
    private SharePreference sharePreference;



    public ActiveOrdersFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mview = inflater.inflate(R.layout.fragment_active_orders, container, false);
        activity = getActivity();
        dialog = UiUtils.showProgress(activity);
        sharePreference = new SharePreference(activity);

        
//*******GPS allow check*******//
        UiUtils.GpsPermission(activity);

        blankImage = mview.findViewById(R.id.blank_img);
        activeorderlist_recyclerview = mview.findViewById(R.id.rl_active_orderList);
        activeorderlist_recyclerview.setLayoutManager(new LinearLayoutManager(activity));
        activeorderlist_recyclerview
                .addItemDecoration(new DividerItemDecoration(activity, LinearLayoutManager.VERTICAL));

        // SwipeRefreshLayout
        mSwipeRefreshLayout = (SwipeRefreshLayout) mview.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        // ViewModel Object
        orderListViewModel = ViewModelProviders.of(this).get(OrderListViewModel.class);
        listOrderDataModel = Singleton.getInstance().getListOrderDataModel();

        adapter = new ActiveOrderListAdapter(activity, active_order_arraylist, this);
        activeorderlist_recyclerview.setAdapter(adapter);


        createList(listOrderDataModel.getData().getAvailable());

        return mview;
    }

    private void createList(List<ListOrderResponseDataModel.Available> availableList) {

        active_order_arraylist.clear();

        if (availableList.size() > 0) {

            activeorderlist_recyclerview.setVisibility(View.VISIBLE);
            blankImage.setVisibility(View.GONE);

            for (ListOrderResponseDataModel.Available available : availableList) {


                OrderActiveDatamodel orderActiveDatamodel = new OrderActiveDatamodel();
                orderActiveDatamodel.pickuptime = available.getPickupDateNew() + "  " + available.getPickupTime();
                orderActiveDatamodel.activeorder_id = available.getId();
                orderActiveDatamodel.activeorder_type = String.valueOf(available.getOrderType());
                orderActiveDatamodel.activeorder_pickup_address = available.getPickupPoint();
                orderActiveDatamodel.activeorder_delivery_address = available.getDropPoint();
                orderActiveDatamodel.activeorder_total_ammount = "₹" + available.getAmount();
                orderActiveDatamodel.provider_bonus = available.getProviderBonus();
                orderActiveDatamodel.earnAmount = available.getEarn();
                orderActiveDatamodel.orderId = available.getOrderNo();


                active_order_arraylist.add(orderActiveDatamodel);

            }


            adapter.notifyDataSetChanged();

        } else {
            activeorderlist_recyclerview.setVisibility(View.GONE);
            blankImage.setVisibility(View.VISIBLE);
        }


    }


    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);

        if (NetworkUtils.isNetworkAvailable(activity)) {
            getOrderList();
        } else {
            UiUtils.showToast(activity, getString(R.string.network_error));
        }

        active_order_arraylist.clear();

    }

    public void getOrderList() {

        orderListViewModel.getOrderListData().observe(this, new Observer<ListOrderResponseDataModel>() {
            @Override
            public void onChanged(ListOrderResponseDataModel listOrderDataModel) {

                mSwipeRefreshLayout.setRefreshing(false);
                active_order_arraylist.clear();
                if (listOrderDataModel != null) {

                    if (listOrderDataModel.getStatus() == 200) {
                        if (listOrderDataModel.getData().getAvailable().size() > 0) {
                            activeorderlist_recyclerview.setVisibility(View.VISIBLE);
                            blankImage.setVisibility(View.GONE);

                            for (ListOrderResponseDataModel.Available available : listOrderDataModel.getData().getAvailable()) {

                                OrderActiveDatamodel orderActiveDatamodel = new OrderActiveDatamodel();
                                orderActiveDatamodel.pickuptime = available.getPickupDateNew() + "  " + available.getPickupTime();
                                orderActiveDatamodel.activeorder_id = available.getId();
                                orderActiveDatamodel.activeorder_type = String.valueOf(available.getOrderType());
                                orderActiveDatamodel.activeorder_pickup_address = available.getPickupPoint();
                                orderActiveDatamodel.activeorder_delivery_address = available.getDropPoint();
                                orderActiveDatamodel.activeorder_total_ammount = "₹" + available.getAmount();
                                orderActiveDatamodel.provider_bonus = available.getProviderBonus();
                                orderActiveDatamodel.earnAmount = available.getEarn();
                                orderActiveDatamodel.orderId = available.getOrderNo();

                                active_order_arraylist.add(orderActiveDatamodel);

                            }
                            /*adapter = new ActiveOrderListAdapter(activity, active_order_arraylist);
                            activeorderlist_recyclerview.setAdapter(adapter);*/
                            adapter.notifyDataSetChanged();
                        } else {

                            active_order_arraylist.clear();
                            activeorderlist_recyclerview.setVisibility(View.GONE);
                            blankImage.setVisibility(View.VISIBLE);
                        }
                    }


                }


            }
        });
    }

    @Override
    public void onActiveOrderItemClick(int id) {
        Singleton.getInstance().setORDERID(id);
        callAcceptOrder();
    }

    //Call ACcept Prder API
    public void callAcceptOrder() {

        dialog.show();
        orderListViewModel.acceptOrderData().observe(this, acceptOrderResponseDataModel -> {
            dialog.dismiss();

            if (acceptOrderResponseDataModel != null) {
                if (acceptOrderResponseDataModel.getStatus() == 200) {

                    Intent intent = new Intent("custom-message");
                    intent.putExtra("ORDERSTATUS", "Accepted");
                    LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);

                } else {
                    //UiUtils.showAlert(activity, getString(R.string.app_name), getString(R.string.job_already_accepted));

                    dialogueShow();

                }
            } else {
                //UiUtils.showAlert(activity, getString(R.string.app_name), getString(R.string.job_already_accepted));
                dialogueShow();

            }


        });


    }


    private void dialogueShow() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
        builder.setTitle(getResources().getString(R.string.app_name));
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage(R.string.job_already_accepted);
        builder.setPositiveButton(R.string.label_ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mSwipeRefreshLayout.setRefreshing(true);

                        getOrderList();

                    }
                });

        final android.app.AlertDialog alert = builder.create();
        alert.show();
    }



}