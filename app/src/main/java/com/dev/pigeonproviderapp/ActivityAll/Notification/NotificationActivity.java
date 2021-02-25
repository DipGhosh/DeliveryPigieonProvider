package com.dev.pigeonproviderapp.ActivityAll.Notification;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.pigeonproviderapp.ActivityAll.AccountSettings.AccountSetting;
import com.dev.pigeonproviderapp.ActivityAll.PaymentHistory.PaymentHistoryActivity;
import com.dev.pigeonproviderapp.Baseclass.BaseActivity;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.NetworkUtils;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.datamodel.NotificationDatamodel;
import com.dev.pigeonproviderapp.datamodel.OrderDetailsResponseDatamodel;
import com.dev.pigeonproviderapp.datamodel.PaymentHistoryDataModel;
import com.dev.pigeonproviderapp.view.Adapter.Notification.NotificationAdapter;
import com.dev.pigeonproviderapp.view.Adapter.OrderDetails.OrderDetailsAdapter;
import com.dev.pigeonproviderapp.view.Adapter.PaymentHistory.EarnHistoryAdapter;
import com.dev.pigeonproviderapp.view.Dataprovider.DeliveryPointListingDatamodel;
import com.dev.pigeonproviderapp.view.Dataprovider.NotificationDataProvider;
import com.dev.pigeonproviderapp.viewmodel.NotificationViewModel;
import com.dev.pigeonproviderapp.viewmodel.ProfileViewModel;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class NotificationActivity extends BaseActivity implements View.OnClickListener{

    private Activity activity = NotificationActivity.this;

    private TextView back;

    private RecyclerView notification_recyclerview;
    private ArrayList<NotificationDataProvider> notification_arraylist = new ArrayList<>();
    private NotificationAdapter adapter;
    private ImageView blankImage;

    private NotificationViewModel notificationViewModel;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        dialog = UiUtils.showProgress(activity);

        back=findViewById(R.id.tv_cancel);
        blankImage=findViewById(R.id.blank_img);
        notification_recyclerview = findViewById(R.id.rl_notification);
        notification_recyclerview.setLayoutManager(new LinearLayoutManager(activity));
        notification_recyclerview
                .addItemDecoration(new DividerItemDecoration(activity, LinearLayoutManager.VERTICAL));

        // ViewModel Object
        notificationViewModel = ViewModelProviders.of(this).get(NotificationViewModel.class);

        back.setOnClickListener(this);

        if (NetworkUtils.isNetworkAvailable(activity))
        {
            getAllNotificationAPICall();

        }else {
            UiUtils.showToast(activity, getString(R.string.network_error));
        }



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_cancel:
                finish();
                break;

            default:
                break;

        }
    }

    public void getAllNotificationAPICall() {

        dialog.show();

        notificationViewModel.getAllNotificationCall().observe(this, new Observer<NotificationDatamodel>() {
            @Override
            public void onChanged(NotificationDatamodel notificationDatamodel) {

                dialog.dismiss();

                if (notificationDatamodel.getData().size()>0)
                {

                    if (notificationDatamodel.getStatus() == 200) {

                        notification_arraylist.clear();

                        for (NotificationDatamodel.Datum notification : notificationDatamodel.getData())
                        {
                            NotificationDataProvider notificationDataProvider = new NotificationDataProvider();
                            notificationDataProvider.item_header=notification.getTitle();
                            notificationDataProvider.item_desc=notification.getBody();

                            notification_arraylist.add(notificationDataProvider);
                        }

                        adapter = new NotificationAdapter(activity, notification_arraylist);
                        notification_recyclerview.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }else {
                    blankImage.setVisibility(View.VISIBLE);
                }



            }
        });
    }
}