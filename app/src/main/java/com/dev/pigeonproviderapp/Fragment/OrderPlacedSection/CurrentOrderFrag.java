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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dev.pigeonproviderapp.Baseclass.BaseFragment;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.NetworkUtils;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.datamodel.ListOrderResponseDataModel;
import com.dev.pigeonproviderapp.view.Adapter.CurrentOrder.CurrentOrderListAdapter;
import com.dev.pigeonproviderapp.view.Dataprovider.CurrentOrderDatamodel;
import com.dev.pigeonproviderapp.view.Dataprovider.OrderActiveDatamodel;
import com.dev.pigeonproviderapp.viewmodel.OrderListViewModel;

import java.util.ArrayList;
import java.util.List;


public class CurrentOrderFrag extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    View mview;
    private Activity activity;
    SwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerView currentorderlist_recyclerview;
    private ArrayList<CurrentOrderDatamodel> current_order_arraylist = new ArrayList<>();
    private CurrentOrderListAdapter adapter;
    private ImageView blankImage;

    private OrderListViewModel orderListViewModel;

    public CurrentOrderFrag() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mview=inflater.inflate(R.layout.fragment_current_order, container, false);
        activity=getActivity();

        blankImage=mview.findViewById(R.id.blank_img);
        currentorderlist_recyclerview = mview.findViewById(R.id.rl_current_orderList);
        currentorderlist_recyclerview.setLayoutManager(new LinearLayoutManager(activity));
        currentorderlist_recyclerview
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

        adapter = new CurrentOrderListAdapter(activity, current_order_arraylist);
        currentorderlist_recyclerview.setAdapter(adapter);
    }

    public void setData(List<ListOrderResponseDataModel.Current> currents) {

        current_order_arraylist.clear();

        if (currents.size()>0){
            blankImage.setVisibility(View.GONE);

            for (ListOrderResponseDataModel.Current current : currents) {

                CurrentOrderDatamodel currentOrderDatamodel = new CurrentOrderDatamodel();
                currentOrderDatamodel.pickuptime=current.getPickupDate()+"  "+current.getPickupTime();
                currentOrderDatamodel.currentorder_type = String.valueOf(current.getOrderType());
                currentOrderDatamodel.currentorder_pickup_address = current.getPickupPoint();
                currentOrderDatamodel.currentorder_delivery_address = current.getDropPoint();
                currentOrderDatamodel.currentorder_total_ammount = "₹ " + current.getAmount();
                currentOrderDatamodel.currentorder_id=current.getId();
                currentOrderDatamodel.provider_bonus=current.getProviderBonus();
                currentOrderDatamodel.earnAmount=current.getEarn();
                currentOrderDatamodel.orderId=current.getOrderNo();

                current_order_arraylist.add(currentOrderDatamodel);

            }

            adapter.notifyDataSetChanged();

        }else {
            blankImage.setVisibility(View.VISIBLE);
        }




    }

    @Override
    public void onRefresh() {

        if (NetworkUtils.isNetworkAvailable(activity)) {

            getOrderList();

        } else {
            UiUtils.showToast(activity, getString(R.string.network_error));
        }

        mSwipeRefreshLayout.setRefreshing(true);
    }

    public  void getOrderList() {

        orderListViewModel.getOrderListData().observe(this, new Observer<ListOrderResponseDataModel>() {
            @Override
            public void onChanged(ListOrderResponseDataModel listOrderDataModel) {

                mSwipeRefreshLayout.setRefreshing(false);
                current_order_arraylist.clear();
                blankImage.setVisibility(View.GONE);

                if (listOrderDataModel != null && listOrderDataModel.getStatus() == 200){


                    for (ListOrderResponseDataModel.Current current : listOrderDataModel.getData().getCurrent()) {

                        CurrentOrderDatamodel currentOrderDatamodel = new CurrentOrderDatamodel();
                        currentOrderDatamodel.pickuptime=current.getPickupDate()+"  "+current.getPickupTime();
                        currentOrderDatamodel.currentorder_type = String.valueOf(current.getOrderType());
                        currentOrderDatamodel.currentorder_pickup_address = current.getPickupPoint();
                        currentOrderDatamodel.currentorder_delivery_address = current.getDropPoint();
                        currentOrderDatamodel.currentorder_total_ammount = "₹ " + current.getAmount();
                        currentOrderDatamodel.currentorder_id=current.getId();
                        currentOrderDatamodel.provider_bonus=current.getProviderBonus();
                        currentOrderDatamodel.earnAmount=current.getEarn();
                        currentOrderDatamodel.orderId=current.getOrderNo();

                        current_order_arraylist.add(currentOrderDatamodel);
                    }

                    if (listOrderDataModel.getData().getCurrent().size() > 0) {
                        blankImage.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                    } else {

                        blankImage.setVisibility(View.VISIBLE);
                    }
                }



            }
        });
    }
}