package com.dev.pigeonproviderapp.Fragment.OrderPlacedSection;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dev.pigeonproviderapp.Baseclass.BaseFragment;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.datamodel.ListOrderResponseDataModel;
import com.dev.pigeonproviderapp.view.Adapter.CurrentOrder.CurrentOrderListAdapter;
import com.dev.pigeonproviderapp.view.Adapter.PastOrder.PastOrderListAdapter;
import com.dev.pigeonproviderapp.view.Dataprovider.CurrentOrderDatamodel;
import com.dev.pigeonproviderapp.view.Dataprovider.PastOrderDatamodel;

import java.util.ArrayList;
import java.util.List;


public class PastOrderFrag extends BaseFragment {

    View mview;
    private Activity activity;

    private RecyclerView pastorderlist_recyclerview;
    private ArrayList<PastOrderDatamodel> past_order_arraylist = new ArrayList<>();
    private PastOrderListAdapter adapter;
    private ImageView blankImage;

    public PastOrderFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mview = inflater.inflate(R.layout.fragment_past_order, container, false);
        activity = getActivity();

        blankImage =mview.findViewById(R.id.blank_img);
        pastorderlist_recyclerview = mview.findViewById(R.id.rl_past_orderList);
        pastorderlist_recyclerview.setLayoutManager(new LinearLayoutManager(activity));
        pastorderlist_recyclerview
                .addItemDecoration(new DividerItemDecoration(activity, LinearLayoutManager.VERTICAL));



        createList();

        return mview;
    }

    private void createList() {

        adapter = new PastOrderListAdapter(activity, past_order_arraylist);
        pastorderlist_recyclerview.setAdapter(adapter);


    }

    public void setData(List<ListOrderResponseDataModel.Past> pasts) {

        past_order_arraylist.clear();

        if (pasts.size() > 0) {

            for (ListOrderResponseDataModel.Past past : pasts) {


                PastOrderDatamodel pastOrderDatamodel = new PastOrderDatamodel();

                pastOrderDatamodel.pastorder_type = String.valueOf(past.getOrderType());
                pastOrderDatamodel.pastorder_pickup_address = past.getPickupPoint();
                pastOrderDatamodel.pastorder_delivery_address = past.getDropPoint();
                pastOrderDatamodel.pastorder_total_ammount = "₹" + past.getAmount();
                pastOrderDatamodel.pastorder_id = past.getId();

                past_order_arraylist.add(pastOrderDatamodel);

            }
            adapter.notifyDataSetChanged();

        } else {
           blankImage.setVisibility(View.VISIBLE);

        }




    }
}