package com.dev.pigeonproviderapp.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.pigeonproviderapp.ActivityAll.ProviderRegistration.Registrationactivity;
import com.dev.pigeonproviderapp.Baseclass.BaseFragment;
import com.dev.pigeonproviderapp.Fragment.OrderPlacedSection.ActiveOrdersFrag;
import com.dev.pigeonproviderapp.Fragment.OrderPlacedSection.CurrentOrderFrag;
import com.dev.pigeonproviderapp.Fragment.OrderPlacedSection.PastOrderFrag;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.Utility.Utility;
import com.dev.pigeonproviderapp.datamodel.ListOrderResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.OrderDetailsResponseDatamodel;
import com.dev.pigeonproviderapp.storage.Singleton;
import com.dev.pigeonproviderapp.viewmodel.OrderListViewModel;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


public class OrdersFrag extends BaseFragment {

    private View mView;
    private TabLayout tabLayout;
    private TabItem tabActive;
    private TabItem tabCurrent;
    private TabItem tabPast;
    private ViewPager viewPager;
    private PageAdapter pageAdapter;

    private OrderListViewModel orderListViewModel;

    private Activity activity;
    private Dialog dialog;

    private ActiveOrdersFrag activeOrdersFrag = new ActiveOrdersFrag();
    private CurrentOrderFrag currentOrderFrag = new CurrentOrderFrag();
    private PastOrderFrag pastOrderFrag = new PastOrderFrag();

    public OrdersFrag() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView= inflater.inflate(R.layout.fragment_orders, container, false);
        activity=getActivity();
        dialog = UiUtils.showProgress(activity);

        tabLayout=mView.findViewById(R.id.tablayout);
        tabActive=mView.findViewById(R.id.tabActiveOrder);
        tabCurrent=mView.findViewById(R.id.tabCurrentOrders);
        tabPast=mView.findViewById(R.id.tabPastOrders);
        viewPager=mView.findViewById(R.id.viewPager);


        pageAdapter = new PageAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // ViewModel Object
        orderListViewModel = ViewModelProviders.of(this).get(OrderListViewModel.class);

        getOrderList();

        LocalBroadcastManager.getInstance(activity).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));



        return mView;
    }
    @Override
    public void onResume(){
        super.onResume();
        //OnResume Fragment

        if (Singleton.getInstance().isOrderaccept()==true)
        {
            getOrderList();
        }
    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent

            if (intent.getStringExtra("ORDERSTATUS").equals("Accepted"))
            {
                int orderID= Integer.parseInt(intent.getStringExtra("ORDERID"));
                Singleton.getInstance().setORDERID(orderID);

                dialogue();

            }

        }
    };

    public  void getOrderList() {
        dialog.show();
        orderListViewModel.getOrderListData().observe(this, new Observer<ListOrderResponseDataModel>() {
            @Override
            public void onChanged(ListOrderResponseDataModel listOrderDataModel) {
                dialog.dismiss();
                pageAdapter = new PageAdapter(getChildFragmentManager(), tabLayout.getTabCount());
                viewPager.setAdapter(pageAdapter);
                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

                // set data
                if (listOrderDataModel.getData().getAvailable()!=null)
                {
                    activeOrdersFrag.setData(listOrderDataModel.getData().getAvailable());
                    currentOrderFrag.setData(listOrderDataModel.getData().getCurrent());
                    pastOrderFrag.setData(listOrderDataModel.getData().getPast());

                }


            }
        });
    }

    //Call ACcept Prder API
    public void callAcceptOrder() {

        dialog.show();
        orderListViewModel.acceptOrderData().observe(this, acceptOrderResponseDataModel -> {
            dialog.dismiss();

            if (acceptOrderResponseDataModel.getStatus() == 200) {

                UiUtils.showAlert(activity,"Order Accept",getString(R.string.order_accept_message));
                getOrderList();
            } else {

            }

        });


    }

    public void dialogue() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
        builder.setTitle(getResources().getString(R.string.app_name));
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage(R.string.aleart_accept_order);
        builder.setPositiveButton(R.string.label_ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callAcceptOrder();
                    }
                });
        builder.setNegativeButton(R.string.label_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final android.app.AlertDialog alert = builder.create();
        alert.show();
    }



    public class PageAdapter extends FragmentPagerAdapter {

        private int numOfTabs;

        PageAdapter(FragmentManager fm, int numOfTabs) {
            super(fm);
            this.numOfTabs = numOfTabs;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return activeOrdersFrag;
                case 1:
                    return currentOrderFrag;
                case 2:
                    return pastOrderFrag;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return numOfTabs;
        }
    }
}