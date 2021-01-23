package com.dev.pigeonproviderapp.view.Adapter.ActiveOrder;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.view.Dataprovider.OrderActiveDatamodel;

import java.util.Collections;
import java.util.List;

public class ActiveOrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    static Activity activity;
    View myView;
    private LayoutInflater inflater;
    public static List<OrderActiveDatamodel> data= Collections.emptyList();

    public ActiveOrderListAdapter(Activity activity, List<OrderActiveDatamodel> data)  /**/
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
        myView = inflater.inflate(R.layout.active_order_listing_layout, parent, false);
        MyHolder holder = new MyHolder(myView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position)
    {
        final MyHolder handler = (MyHolder) holder;
        final OrderActiveDatamodel orderActiveDatamodel=data.get(position);

        System.out.println("ADapterPOINT"+orderActiveDatamodel.activeorder_type);

        handler.deliveryType.setText(orderActiveDatamodel.activeorder_type);
        handler.currentOrderPickupAddress.setText(orderActiveDatamodel.activeorder_pickup_address);
        handler.currentOrderDeliveryAddress.setText(orderActiveDatamodel.activeorder_delivery_address);
        handler.currentOrderPrice.setText(orderActiveDatamodel.activeorder_total_ammount);
        /*handler.vieworderDetailsClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, CurrentOrderDetails.class);
                activity.startActivity(intent);
            }
        });*/



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
