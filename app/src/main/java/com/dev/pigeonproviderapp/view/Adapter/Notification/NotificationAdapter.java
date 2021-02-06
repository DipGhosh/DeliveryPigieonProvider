package com.dev.pigeonproviderapp.view.Adapter.Notification;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.view.Dataprovider.NotificationDataProvider;

import java.util.Collections;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private LayoutInflater inflater;
    List<NotificationDataProvider> data = Collections.emptyList();
    Activity activity;
    View view;
    private List<NotificationDataProvider> dataList;


    public NotificationAdapter(Activity activity, List<NotificationDataProvider> data) {
        if (activity!=null) {

            this.activity = activity;
            inflater = LayoutInflater.from(activity);
            this.data = data;
            this.dataList=data;
        }
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.notification_item, parent, false);
        MyHolder holder = new MyHolder(view);
        final NotificationDataProvider dataProvider = data.get(viewType);




        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        final MyHolder handler = (MyHolder) holder;
        final NotificationDataProvider notificationDataProvider = dataList.get(position);


        handler.tv_header.setText(notificationDataProvider.item_header);
        handler.tv_desc.setText(notificationDataProvider.item_desc);




    }

    // return total item from List
    @Override
    public int getItemCount() {
        return dataList.size();
    }



    public void refreshEvents() {
        this.dataList.clear();
        notifyDataSetChanged();
    }


    public class MyHolder extends RecyclerView.ViewHolder{
        public TextView tv_header,tv_desc;
        LinearLayout ll_click;

        public MyHolder(View row) {
            super(row);

            tv_header = (TextView) view.findViewById(R.id.tv_noti_header);
            tv_desc = (TextView) view.findViewById(R.id.tv_noti_desc);
            ll_click=(LinearLayout)view.findViewById(R.id.ll_click);

        }
    }
}
