package com.dev.pigeonproviderapp.Fragment.OrderPlacedSection;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.dev.pigeonproviderapp.Baseclass.BaseFragment;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.NetworkUtils;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.datamodel.ListOrderResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.PastOrderResponseDataModel;
import com.dev.pigeonproviderapp.storage.Singleton;
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


public class PastOrderFrag extends BaseFragment {

    View mview;
    List<ParentItem> itemList = new ArrayList<>();
    List<ParentItem> itemFinalList = new ArrayList<>();
    int count = 1;
    private Activity activity;
    private RecyclerView parentRecyclerview;
    private ImageView blankImage;
    private ParentItemAdapter parentItemAdapter;
    private OrderListViewModel orderListViewModel;
    private NestedScrollView nsvMain;
    private LinearLayoutManager mLayoutManagerMissingList = new LinearLayoutManager(getActivity());
    private Dialog dialog;
    private boolean ischecked;


    public PastOrderFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mview = inflater.inflate(R.layout.fragment_past_order, container, false);
        activity = getActivity();
        dialog = UiUtils.showProgress(activity);

        blankImage = mview.findViewById(R.id.blank_img);
        nsvMain = mview.findViewById(R.id.form_m_list_fragment_nsvMain);
        parentRecyclerview = mview.findViewById(R.id.parent_recyclerview);
        parentRecyclerview.setLayoutManager(new LinearLayoutManager(activity));
        parentRecyclerview.addItemDecoration(new DividerItemDecoration(activity, LinearLayoutManager.VERTICAL));


        // ViewModel Object
        orderListViewModel = ViewModelProviders.of(this).get(OrderListViewModel.class);


        if (!ischecked) {

            Singleton.getInstance().setPageCount(count);
            getOrderList();

            ischecked = true;
        } else {
            //itemFinalList.clear();
            Singleton.getInstance().setPageCount(1);
            getOrderList();

            ischecked = false;
        }

        if (nsvMain != null) {
            nsvMain.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {

               if (scrollY==v.getChildAt(0).getMeasuredHeight()-v.getMeasuredHeight())
               {
                   count++;
                   Log.d("Count", String.valueOf(count));

                   Singleton.getInstance().setPageCount(count);
                   getOrderList();
                   parentItemAdapter.notifyDataSetChanged();
               }


            });
        }


        return mview;
    }


    public void getOrderList() {

        dialog.show();

        itemList.clear();

        orderListViewModel.getPastOrderListData().observe(this, new Observer<PastOrderResponseDataModel>() {
            @Override
            public void onChanged(PastOrderResponseDataModel listOrderDataModel) {

                dialog.dismiss();

                if (listOrderDataModel.getData().getPastNew().isEmpty()) {
                    Toast.makeText(getContext(), "No data found", Toast.LENGTH_LONG).show();
                } else {

                    if (listOrderDataModel.getData().getPastNew().size() > 0) {

                        for (PastOrderResponseDataModel.PastNew past : listOrderDataModel.getData().getPastNew()) {

                            List<ChildItem> ChildItemList = new ArrayList<>();
                            ParentItem parentItem = new ParentItem();
                            ;
                            parentItem.setParentItemTitle(past.getDate());

                            for (PastOrderResponseDataModel.Order order : past.getOrders()) {
                                ChildItem childItem = new ChildItem();
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
                        //Add all the array element in final arraylist for pejignation
                        itemFinalList.addAll(itemList);

                        parentItemAdapter = new ParentItemAdapter(activity, itemFinalList);
                        parentRecyclerview.setAdapter(parentItemAdapter);





                    } else {

                        blankImage.setVisibility(View.VISIBLE);
                    }

                }


            }
        });
    }



}