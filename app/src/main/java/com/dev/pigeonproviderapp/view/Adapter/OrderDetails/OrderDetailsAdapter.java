package com.dev.pigeonproviderapp.view.Adapter.OrderDetails;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.view.Dataprovider.OrderDetailsListingDatamodel;

import java.util.Collections;
import java.util.List;

public class OrderDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    static Activity activity;
    View myView;
    private LayoutInflater inflater;
    public static List<OrderDetailsListingDatamodel> data= Collections.emptyList();

    public OrderDetailsAdapter(Activity activity, List<OrderDetailsListingDatamodel> data)  /**/
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
        myView = inflater.inflate(R.layout.detailsorder_list_item, parent, false);
        OrderDetailsAdapter.MyHolder holder = new OrderDetailsAdapter.MyHolder(myView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position)
    {
        final OrderDetailsAdapter.MyHolder handler = (OrderDetailsAdapter.MyHolder) holder;
        final OrderDetailsListingDatamodel orderDetailsListingDatamodel=data.get(position);


        handler.orderPointName.setText(orderDetailsListingDatamodel.orderpoint_name);
        handler.orderstatus.setText(orderDetailsListingDatamodel.order_status);
        handler.orderAddress.setText(orderDetailsListingDatamodel.order_point_address);





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
        TextView orderPointName,orderstatus,orderAddress;


        public MyHolder(View row)
        {
            super(row);
            orderPointName=(TextView) row.findViewById(R.id.tv_item_point);
            orderstatus=(TextView) row.findViewById(R.id.tv_pickup_address);
            orderAddress=(TextView)row.findViewById(R.id.order_address_details);



        }
    }
}
