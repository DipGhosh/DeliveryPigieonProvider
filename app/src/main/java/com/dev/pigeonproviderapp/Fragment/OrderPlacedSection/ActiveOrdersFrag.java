package com.dev.pigeonproviderapp.Fragment.OrderPlacedSection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.pigeonproviderapp.ActivityAll.OrderdetailsSection.ActiveorderDetails;
import com.dev.pigeonproviderapp.ActivityAll.OrderdetailsSection.CurrentOrderDetails;
import com.dev.pigeonproviderapp.Baseclass.BaseFragment;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.datamodel.ListOrderResponseDataModel;
import com.dev.pigeonproviderapp.view.Adapter.ActiveOrder.ActiveOrderListAdapter;
import com.dev.pigeonproviderapp.view.Dataprovider.OrderActiveDatamodel;

import java.util.ArrayList;
import java.util.List;


public class ActiveOrdersFrag extends BaseFragment {

    View mview;
    private Activity activity;

    private RecyclerView activeorderlist_recyclerview;
    private ArrayList<OrderActiveDatamodel> active_order_arraylist = new ArrayList<>();
    private ActiveOrderListAdapter adapter;


    public ActiveOrdersFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mview=inflater.inflate(R.layout.fragment_active_orders, container, false);
        activity=getActivity();

        activeorderlist_recyclerview = mview.findViewById(R.id.rl_active_orderList);
        activeorderlist_recyclerview.setLayoutManager(new LinearLayoutManager(activity));
        activeorderlist_recyclerview
                .addItemDecoration(new DividerItemDecoration(activity, LinearLayoutManager.VERTICAL));

        createList();

        return mview;
    }

    private void createList() {

        adapter = new ActiveOrderListAdapter(activity, active_order_arraylist);
        activeorderlist_recyclerview.setAdapter(adapter);
    }

    public void setData(List<ListOrderResponseDataModel.Available> availableList) {

        for (ListOrderResponseDataModel.Available available : availableList) {

            OrderActiveDatamodel orderActiveDatamodel = new OrderActiveDatamodel();

            orderActiveDatamodel.activeorder_type = "Local";
            orderActiveDatamodel.activeorder_pickup_address = available.getPickupPoint();
            orderActiveDatamodel.activeorder_delivery_address = available.getDropPoint();
            orderActiveDatamodel.activeorder_total_ammount = "₹" + available.getAmount();

            active_order_arraylist.add(orderActiveDatamodel);
            System.out.println("Arralist"+active_order_arraylist);


        }


        adapter.notifyDataSetChanged();

    }

}