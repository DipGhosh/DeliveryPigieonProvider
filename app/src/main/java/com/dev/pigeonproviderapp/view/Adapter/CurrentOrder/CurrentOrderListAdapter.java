package com.dev.pigeonproviderapp.view.Adapter.CurrentOrder;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dev.pigeonproviderapp.ActivityAll.OrderdetailsSection.ActiveorderDetails;
import com.dev.pigeonproviderapp.ActivityAll.OrderdetailsSection.CurrentOrderDetails;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.view.Adapter.ActiveOrder.ActiveOrderListAdapter;
import com.dev.pigeonproviderapp.view.Dataprovider.CurrentOrderDatamodel;
import com.dev.pigeonproviderapp.view.Dataprovider.OrderActiveDatamodel;

import java.util.Collections;
import java.util.List;

public class CurrentOrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    static Activity activity;
    View myView;
    private LayoutInflater inflater;
    public static List<CurrentOrderDatamodel> data= Collections.emptyList();

    public CurrentOrderListAdapter(Activity activity, List<CurrentOrderDatamodel> data)  /**/
    {
        if (activity!=null)
        {
            this.activity = activity;
            inflater = LayoutInflater.from(activity);
            this.data = data;
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        myView = inflater.inflate(R.layout.current_order_listing_layout, parent, false);
        CurrentOrderListAdapter.MyHolder holder = new CurrentOrderListAdapter.MyHolder(myView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position)
    {
        final CurrentOrderListAdapter.MyHolder handler = (CurrentOrderListAdapter.MyHolder) holder;
        final CurrentOrderDatamodel currentOrderDatamodel=data.get(position);

        if(currentOrderDatamodel.currentorder_type.equals("1"))
        {
            handler.deliveryType.setText("Local");
        }else if(currentOrderDatamodel.currentorder_type.equals("2"))
        {
            handler.deliveryType.setText("Hyper Local");
        }else {
            handler.deliveryType.setText("Multi Points");
        }

        handler.currentOrderPickupAddress.setText(currentOrderDatamodel.currentorder_pickup_address);
        handler.currentOrderDeliveryAddress.setText(currentOrderDatamodel.currentorder_delivery_address);
        handler.currentOrderPrice.setText(currentOrderDatamodel.currentorder_total_ammount);
        handler.vieworderDetailsClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, CurrentOrderDetails.class);
                activity.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void refreshEvents() {
        this.data.clear();
        notifyDataSetChanged();
    }
    public static class MyHolder extends RecyclerView.ViewHolder
    {
        TextView deliveryType,currentOrderPickupAddress,currentOrderDeliveryAddress,currentOrderPrice,vieworderDetailsClick;


        public MyHolder(View row)
        {
            super(row);
            deliveryType=(TextView) row.findViewById(R.id.tv_deliveryType);
            currentOrderPickupAddress=(TextView) row.findViewById(R.id.tv_pickup_address);
            currentOrderDeliveryAddress=(TextView)row.findViewById(R.id.tv_delivery_address);
            currentOrderPrice=(TextView)row.findViewById(R.id.tv_price);
            vieworderDetailsClick=(TextView)row.findViewById(R.id.tv_view_details);


        }
    }
}
