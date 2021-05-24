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
import com.dev.pigeonproviderapp.Utility.NetworkUtils;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.datamodel.ListOrderResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.PastOrderResponseDataModel;
import com.dev.pigeonproviderapp.view.Adapter.CurrentOrder.CurrentOrderListAdapter;
import com.dev.pigeonproviderapp.view.Adapter.PastOrder.ParentItemAdapter;
import com.dev.pigeonproviderapp.view.Adapter.PastOrder.PastOrderListAdapter;
import com.dev.pigeonproviderapp.view.Dataprovider.ChildItem;
import com.dev.pigeonproviderapp.view.Dataprovider.CurrentOrderDatamodel;
import com.dev.pigeonproviderapp.view.Dataprovider.OrderActiveDatamodel;
import com.dev.pigeonproviderapp.view.Dataprovider.ParentItem;
import com.dev.pigeonproviderapp.view.Dataprovider.PastOrderDatamodel;
import com.dev.pigeonproviderapp.viewmodel.OrderListViewModel;

import java.util.ArrayList;
import java.util.List;


public class PastOrderFrag extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    View mview;
    private Activity activity;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView parentRecyclerview;
    private ImageView blankImage;
    private ParentItemAdapter parentItemAdapter;
    private OrderListViewModel orderListViewModel;

    List<ParentItem> itemList = new ArrayList<>();

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
        parentRecyclerview = mview.findViewById(R.id.parent_recyclerview);
        parentRecyclerview.setLayoutManager(new LinearLayoutManager(activity));
        parentRecyclerview.addItemDecoration(new DividerItemDecoration(activity, LinearLayoutManager.VERTICAL));

        // SwipeRefreshLayout
        mSwipeRefreshLayout = (SwipeRefreshLayout) mview.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        // ViewModel Object
        orderListViewModel = ViewModelProviders.of(this).get(OrderListViewModel.class);

        parentItemAdapter = new ParentItemAdapter(activity,itemList);

        parentRecyclerview.setAdapter(parentItemAdapter);

        getOrderList();
        mSwipeRefreshLayout.setRefreshing(true);





        return mview;
    }




    @Override
    public void onRefresh() {
        getOrderList();
        mSwipeRefreshLayout.setRefreshing(true);
    }

    public void getOrderList() {

        itemList.clear();

        orderListViewModel.getPastOrderListData().observe(this, new Observer<PastOrderResponseDataModel>() {
            @Override
            public void onChanged(PastOrderResponseDataModel listOrderDataModel) {
                mSwipeRefreshLayout.setRefreshing(false);

                for (PastOrderResponseDataModel.PastNew past : listOrderDataModel.getData().getPastNew()) {

                    List<ChildItem> ChildItemList = new ArrayList<>();
                    ParentItem parentItem = new ParentItem();
                    ;
                    parentItem.setParentItemTitle(past.getDate());

                    for (PastOrderResponseDataModel.Order order: past.getOrders())
                    {
                        ChildItem childItem=new ChildItem();
                        childItem.setPlacedorder_type(String.valueOf(order.getOrderType()));
                        childItem.setOrderplace_pickup_address(order.getPickupPoint());
                        childItem.setOrderplace_delivery_address(order.getDropPoint());
                        childItem.setPlaceorder_total_ammount("₹ " + order.getAmount());
                        childItem.setCurrentorder_id(order.getId());
                        childItem.setOrderId(order.getOrderNo());
                        childItem.setEarnAmount(order.getEarn());
                        childItem.setProvider_bonus(order.getProviderBonus());
                        childItem.setOrder_status_message(order.getStatus());

                        ChildItemList.add(childItem);
                    }
                    parentItem.setChildItemList(ChildItemList);
                    itemList.add(parentItem);

                }


                if (listOrderDataModel.getData().getPastNew().size() > 0) {
                    parentItemAdapter.notifyDataSetChanged();
                }else {

                    blankImage.setVisibility(View.VISIBLE);
                }
            }
        });
    }

}