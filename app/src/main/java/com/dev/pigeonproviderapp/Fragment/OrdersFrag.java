package com.dev.pigeonproviderapp.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.dev.pigeonproviderapp.ActivityAll.Notification.NotificationActivity;
import com.dev.pigeonproviderapp.ActivityAll.ProviderDashboard;
import com.dev.pigeonproviderapp.ActivityAll.ProviderRegistration.Registrationactivity;
import com.dev.pigeonproviderapp.Baseclass.BaseFragment;
import com.dev.pigeonproviderapp.Fragment.OrderPlacedSection.ActiveOrdersFrag;
import com.dev.pigeonproviderapp.Fragment.OrderPlacedSection.CurrentOrderFrag;
import com.dev.pigeonproviderapp.Fragment.OrderPlacedSection.PastOrderFrag;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.GPSTracker;
import com.dev.pigeonproviderapp.Utility.NetworkUtils;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.Utility.Utility;
import com.dev.pigeonproviderapp.datamodel.ListOrderResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.LocationDatamodel;
import com.dev.pigeonproviderapp.datamodel.OrderDetailsResponseDatamodel;
import com.dev.pigeonproviderapp.datamodel.ProfileGetResponseDataModel;
import com.dev.pigeonproviderapp.httpRequest.LocationRequestSendModel;
import com.dev.pigeonproviderapp.httpRequest.ProviderAvailabilityAPIModel;
import com.dev.pigeonproviderapp.interfaces.onActiveOrderClickListener;
import com.dev.pigeonproviderapp.storage.SharePreference;
import com.dev.pigeonproviderapp.storage.Singleton;
import com.dev.pigeonproviderapp.view.Adapter.ActiveOrder.ActiveOrderListAdapter;
import com.dev.pigeonproviderapp.viewmodel.LocationSendViewModel;
import com.dev.pigeonproviderapp.viewmodel.OrderListViewModel;
import com.dev.pigeonproviderapp.viewmodel.ProfileViewModel;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;


public class OrdersFrag extends BaseFragment implements View.OnClickListener{

    GPSTracker gpsTracker;
    double provider_lat, provider_long;
    LocationSendViewModel locationSendViewModel;
    private View mView;
    private TabLayout tabLayout;
    private TabItem tabActive;
    private TabItem tabCurrent;
    private TabItem tabPast;
    private ViewPager viewPager;
    private PageAdapter pageAdapter;
    private ImageView notificationImage;
    private OrderListViewModel orderListViewModel;
    private ProfileViewModel profileViewModel;
    private SharePreference sharePreference;
    private ToggleButton simpleToggleButton;
    private int toggleValue = 1;


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
        mView = inflater.inflate(R.layout.fragment_orders, container, false);
        activity = getActivity();
        dialog = UiUtils.showProgress(activity);
        sharePreference = new SharePreference(activity);

        tabLayout = mView.findViewById(R.id.tablayout);
        tabActive = mView.findViewById(R.id.tabActiveOrder);
        tabCurrent = mView.findViewById(R.id.tabCurrentOrders);
        tabPast = mView.findViewById(R.id.tabPastOrders);
        viewPager = mView.findViewById(R.id.viewPager);
        simpleToggleButton = mView.findViewById(R.id.chkState);

        notificationImage = mView.findViewById(R.id.img_notification);

        notificationImage.setOnClickListener(this);
        simpleToggleButton.setOnClickListener(this);

        System.out.println("Check_Value" + sharePreference.GetProviderAvailable());




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
        locationSendViewModel = ViewModelProviders.of(this).get(LocationSendViewModel.class);
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        // restrict refresh fragments
        viewPager.setOffscreenPageLimit(2);



        if (sharePreference.GetProviderAvailable() == true) {
            simpleToggleButton.setChecked(true);
            toggleValue = 1;
            if(!Singleton.getInstance().isAlreadyExecuted()) {
                callGetProfile();
                Singleton.getInstance().setAlreadyExecuted(true);
            }

        } else {
            simpleToggleButton.setChecked(false);
            toggleValue = 0;
            callGetProfile();
        }

        if (Singleton.getInstance().isProfileUpdated() == false) {
            getOrderList();
        }




        gpsTracker = new GPSTracker(activity);

        if (gpsTracker.canGetLocation()) {
            provider_lat = gpsTracker.getLatitude();
            provider_long = gpsTracker.getLongitude();

            System.out.println("LAt" + provider_lat);
            CallLocationAPI();
        }


        LocalBroadcastManager.getInstance(activity).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));



        return mView;
    }



    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent

            if (intent.getStringExtra("ORDERSTATUS").equals("Accepted")) {

                Singleton.getInstance().setOrderaccept(true);

                for (int count = 0;count<1;count++) {

                    getOrderList();

                }


            }


        }


    };


    @Override
    public void onClick(View v) {

        switch (v.getId() /*to get clicked view id**/) {
            case R.id.img_notification:
                Intent notification = new Intent(activity, NotificationActivity.class);
                startActivity(notification);
                break;
            case R.id.chkState:
                if (simpleToggleButton.isChecked()) {
                    //System.out.println("Check"+"Y");
                    toggleValue = 1;
                    ProviderAvailableToggle();

                } else {
                    //System.out.println("Check"+"N");
                    toggleValue = 0;
                    ProviderAvailableToggle();


                }

                break;

            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //OnResume Fragment



        if (Singleton.getInstance().isOrderaccept() == true) {

            //Singleton.getInstance().setOrderaccept(false);
            getOrderList();
            //Singleton.getInstance().setOrderaccept(false);
        }/*else if (Singleton.getInstance().isItemcomplete()==true)
        {
            Singleton.getInstance().setItemcomplete(false);
            getOrderList();
            Singleton.getInstance().setItemcomplete(false);
        }*/ else if (Singleton.getInstance().isALLDROPPOINTCOMPLETE() == true) {
            //Singleton.getInstance().setALLDROPPOINTCOMPLETE(false);
            getOrderList();
            //Singleton.getInstance().setALLDROPPOINTCOMPLETE(false);
        }

        if (gpsTracker.canGetLocation()) {
            provider_lat = gpsTracker.getLatitude();
            provider_long = gpsTracker.getLongitude();

            System.out.println("LAt" + provider_lat);
            CallLocationAPI();
        }
    }

    public void CallLocationAPI() {


        LocationRequestSendModel locationRequestSendModel = new LocationRequestSendModel();
        locationRequestSendModel.setLatitude(provider_lat);
        locationRequestSendModel.setLongitude(provider_long);


        locationSendViewModel.locationSendAPI(locationRequestSendModel)
                .observe(this, new Observer<LocationDatamodel>() {
                    @Override
                    public void onChanged(LocationDatamodel locationDatamodel) {


                    }
                });

    }

    public void getOrderList() {
        dialog.show();
        orderListViewModel.getOrderListData().observe(this, new Observer<ListOrderResponseDataModel>() {
            @Override
            public void onChanged(ListOrderResponseDataModel listOrderDataModel) {
                dialog.dismiss();

                if (listOrderDataModel != null) {

                    if(listOrderDataModel.getStatus() == 200){

                        pageAdapter = new PageAdapter(getChildFragmentManager(), tabLayout.getTabCount());
                        viewPager.setAdapter(pageAdapter);
                        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

                        // set data
                        if (listOrderDataModel.getData().getAvailable() != null) {
                            activeOrdersFrag.setData(listOrderDataModel.getData().getAvailable());
                            currentOrderFrag.setData(listOrderDataModel.getData().getCurrent());
                            pastOrderFrag.setData(listOrderDataModel.getData().getPast());

                        }

                        if (Singleton.getInstance().isOrderaccept()) {
                            //System.out.println("Mangaldip"+"Hello");
                            Singleton.getInstance().setOrderaccept(false);
                            viewPager.setCurrentItem(1, true);
                        }

                        if (Singleton.getInstance().isALLDROPPOINTCOMPLETE()==true)
                        {
                            Singleton.getInstance().setALLDROPPOINTCOMPLETE(false);
                            viewPager.setCurrentItem(1, true);
                        }

                    }


                }




            }
        });
    }


    public void ProviderAvailableToggle() {

        dialog.show();

        ProviderAvailabilityAPIModel providerAvailabilityAPIModel = new ProviderAvailabilityAPIModel();
        providerAvailabilityAPIModel.setIs_available(toggleValue);


        profileViewModel.isAvailableCall(providerAvailabilityAPIModel).observe(this, providerAvailabilityDatamodel -> {

            dialog.dismiss();

            if (providerAvailabilityDatamodel != null) {
                if (providerAvailabilityDatamodel.getStatus() == 200) {


                    sharePreference.setProviderAvailable(providerAvailabilityDatamodel.getData().getIsAvailable());

                }
            }else {
                UiUtils.showAlert(activity, getString(R.string.app_name), getString(R.string.network_error));
            }


        });
    }

    public void callGetProfile() {



        profileViewModel.gerProfile().observe(this, new Observer<ProfileGetResponseDataModel>() {
            @Override
            public void onChanged(ProfileGetResponseDataModel profileGetResponseDataModel) {





                if (profileGetResponseDataModel.getData().getUser().getIsAvailable()==true)
                {
                     simpleToggleButton.setChecked(true);
                    sharePreference.setProviderAvailable(true);
                }else {
                    simpleToggleButton.setChecked(false);
                    sharePreference.setProviderAvailable(false);
                }
                Singleton.getInstance().setProfileImageUrl(profileGetResponseDataModel.getData().getUser().getProfilePicture());


                if (profileGetResponseDataModel.getData().getUser().getProviderAppAndroidVersion()!=null)
                {
                    int latestAppVersion= Integer.parseInt(profileGetResponseDataModel.getData().getUser().getProviderAppAndroidVersion());


                    if (Utility.APP_VERSION<latestAppVersion)
                    {
                        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
                        builder.setTitle(getResources().getString(R.string.app_name));
                        builder.setIcon(R.mipmap.ic_launcher);
                        builder.setMessage(R.string.update_aleart);
                        builder.setCancelable(false);
                        builder.setPositiveButton(R.string.label_ok,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent buinesspartnerIntent = new Intent(Intent.ACTION_VIEW);
                                        buinesspartnerIntent.setData(Uri.parse(Utility.DELIVERYPARTNER_LINK));
                                        startActivity(buinesspartnerIntent);

                                    }
                                });
                        builder.setNegativeButton(R.string.aleart_later, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        final android.app.AlertDialog alert = builder.create();
                        alert.show();
                    }
                }






            }
        });

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