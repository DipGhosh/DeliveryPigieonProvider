package com.dev.pigeonproviderapp.Fragment.OrderPlacedSection;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.dev.pigeonproviderapp.view.Dataprovider.OrderActiveDatamodel;
import com.dev.pigeonproviderapp.view.Dataprovider.PastOrderDatamodel;
import com.dev.pigeonproviderapp.viewmodel.OrderListViewModel;

import java.util.ArrayList;
import java.util.List;


public class PastOrderFrag extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    View mview;
    private Activity activity;
    SwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerView pastorderlist_recyclerview;
    private ArrayList<PastOrderDatamodel> past_order_arraylist = new ArrayList<>();
    private PastOrderListAdapter adapter;
    private ImageView blankImage;

    private OrderListViewModel orderListViewModel;

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

        // SwipeRefreshLayout
        mSwipeRefreshLayout = (SwipeRefreshLayout) mview.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        // ViewModel Object
        orderListViewModel = ViewModelProviders.of(this).get(OrderListViewModel.class);



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

            blankImage.setVisibility(View.GONE);

            for (ListOrderResponseDataModel.Past past : pasts) {


                PastOrderDatamodel pastOrderDatamodel = new PastOrderDatamodel();

                pastOrderDatamodel.pastorder_type = String.valueOf(past.getOrderType());
                pastOrderDatamodel.pastorder_pickup_address = past.getPickupPoint();
                pastOrderDatamodel.pastorder_delivery_address = past.getDropPoint();
                pastOrderDatamodel.pastorder_total_ammount = "₹" + past.getAmount();
                pastOrderDatamodel.pastorder_id = past.getId();
                pastOrderDatamodel.provider_bonus=past.getProviderBonus();
                pastOrderDatamodel.earnAmount=past.getEarn();

                past_order_arraylist.add(pastOrderDatamodel);

            }
            adapter.notifyDataSetChanged();

        } else {
           blankImage.setVisibility(View.VISIBLE);

        }




    }

    @Override
    public void onRefresh() {
        getOrderList();
        mSwipeRefreshLayout.setRefreshing(true);
    }

    public  void getOrderList() {

        orderListViewModel.getOrderListData().observe(this, new Observer<ListOrderResponseDataModel>() {
            @Override
            public void onChanged(ListOrderResponseDataModel listOrderDataModel) {

                mSwipeRefreshLayout.setRefreshing(false);
                past_order_arraylist.clear();

                for (ListOrderResponseDataModel.Past past : listOrderDataModel.getData().getPast()) {

                    PastOrderDatamodel pastOrderDatamodel = new PastOrderDatamodel();

                    pastOrderDatamodel.pastorder_type = String.valueOf(past.getOrderType());
                    pastOrderDatamodel.pastorder_pickup_address = past.getPickupPoint();
                    pastOrderDatamodel.pastorder_delivery_address = past.getDropPoint();
                    pastOrderDatamodel.pastorder_total_ammount = "₹" + past.getAmount();
                    pastOrderDatamodel.pastorder_id = past.getId();
                    pastOrderDatamodel.provider_bonus=past.getProviderBonus();
                    pastOrderDatamodel.earnAmount=past.getEarn();

                    past_order_arraylist.add(pastOrderDatamodel);


                }

                if (listOrderDataModel.getData().getPast().size() > 0) {

                    blankImage.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                } else {

                    blankImage.setVisibility(View.VISIBLE);
                }

            }
        });
    }
}