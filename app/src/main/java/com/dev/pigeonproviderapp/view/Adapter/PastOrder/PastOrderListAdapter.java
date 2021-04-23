package com.dev.pigeonproviderapp.view.Adapter.PastOrder;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dev.pigeonproviderapp.ActivityAll.OrderdetailsSection.OrderDetails;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.storage.Singleton;
import com.dev.pigeonproviderapp.view.Dataprovider.PastOrderDatamodel;

import java.util.Collections;
import java.util.List;

public class PastOrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    static Activity activity;
    View myView;
    private LayoutInflater inflater;
    public static List<PastOrderDatamodel> data= Collections.emptyList();



    public PastOrderListAdapter(Activity activity, List<PastOrderDatamodel> data)  /**/
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
        PastOrderListAdapter.MyHolder holder = new PastOrderListAdapter.MyHolder(myView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position)
    {
        final PastOrderListAdapter.MyHolder handler = (PastOrderListAdapter.MyHolder) holder;
        final PastOrderDatamodel pastOrderDatamodel=data.get(position);

        if(pastOrderDatamodel.pastorder_type.equals("1"))
        {
            handler.deliveryType.setText("Hyper Local");
        }else if(pastOrderDatamodel.pastorder_type.equals("2"))
        {
            handler.deliveryType.setText("Hyper Local");
        }else {
            handler.deliveryType.setText("Multi Points");
        }

        handler.currentOrderPickupAddress.setText(pastOrderDatamodel.pastorder_pickup_address);
        handler.currentOrderDeliveryAddress.setText(pastOrderDatamodel.pastorder_delivery_address);
        handler.currentOrderPrice.setText("Earn: "+"₹ "+pastOrderDatamodel.earnAmount);
        handler.orderId.setText("Order "+pastOrderDatamodel.orderId);
        handler.pickupTime.setVisibility(View.GONE);

        if (pastOrderDatamodel.provider_bonus<=0)
        {
            handler.bonusPrice.setVisibility(View.GONE);
        }else {
            handler.bonusPrice.setText("Bonus: "+"₹ " +pastOrderDatamodel.provider_bonus);
        }
        handler.vieworderDetailsClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, OrderDetails.class);
                intent.putExtra("TypeOfOrder","pastorder");
                activity.startActivity(intent);
                Singleton.getInstance().setORDERID(pastOrderDatamodel.pastorder_id);
                Singleton.getInstance().setOrderaccept(false);
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
        TextView deliveryType,currentOrderPickupAddress,currentOrderDeliveryAddress,currentOrderPrice,vieworderDetailsClick,bonusPrice,orderId,pickupTime;


        public MyHolder(View row)
        {
            super(row);
            deliveryType=(TextView) row.findViewById(R.id.tv_deliveryType);
            currentOrderPickupAddress=(TextView) row.findViewById(R.id.tv_pickup_address);
            currentOrderDeliveryAddress=(TextView)row.findViewById(R.id.tv_delivery_address);
            currentOrderPrice=(TextView)row.findViewById(R.id.tv_price);
            vieworderDetailsClick=(TextView)row.findViewById(R.id.tv_view_details);
            bonusPrice=row.findViewById(R.id.tvbonus_bounus);
            orderId=row.findViewById(R.id.tv_order_id);
            pickupTime=row.findViewById(R.id.tv_pickup_time);


        }
    }
}
