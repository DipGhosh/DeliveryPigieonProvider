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
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.datamodel.ListOrderResponseDataModel;
import com.dev.pigeonproviderapp.view.Adapter.ActiveOrder.ActiveOrderListAdapter;
import com.dev.pigeonproviderapp.view.Adapter.CurrentOrder.CurrentOrderListAdapter;
import com.dev.pigeonproviderapp.view.Dataprovider.CurrentOrderDatamodel;
import com.dev.pigeonproviderapp.view.Dataprovider.OrderActiveDatamodel;

import java.util.ArrayList;
import java.util.List;


public class CurrentOrderFrag extends Fragment {


    View mview;
    private Activity activity;

    private RecyclerView currentorderlist_recyclerview;
    private ArrayList<CurrentOrderDatamodel> current_order_arraylist = new ArrayList<>();
    private CurrentOrderListAdapter adapter;

    public CurrentOrderFrag() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mview=inflater.inflate(R.layout.fragment_current_order, container, false);
        activity=getActivity();

        currentorderlist_recyclerview = mview.findViewById(R.id.rl_current_orderList);
        currentorderlist_recyclerview.setLayoutManager(new LinearLayoutManager(activity));
        currentorderlist_recyclerview
                .addItemDecoration(new DividerItemDecoration(activity, LinearLayoutManager.VERTICAL));

        createList();

        return mview;
    }

    private void createList() {

        adapter = new CurrentOrderListAdapter(activity, current_order_arraylist);
        currentorderlist_recyclerview.setAdapter(adapter);
    }

    public void setData(List<ListOrderResponseDataModel.Current> currents) {

        for (ListOrderResponseDataModel.Current current : currents) {

            CurrentOrderDatamodel currentOrderDatamodel = new CurrentOrderDatamodel();

            currentOrderDatamodel.currentorder_type = String.valueOf(current.getOrderType());
            currentOrderDatamodel.currentorder_pickup_address = current.getPickupPoint();
            currentOrderDatamodel.currentorder_delivery_address = current.getDropPoint();
            currentOrderDatamodel.currentorder_total_ammount = "₹" + current.getAmount();

            current_order_arraylist.add(currentOrderDatamodel);



        }


        adapter.notifyDataSetChanged();

    }
}