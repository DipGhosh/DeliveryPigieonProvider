package com.dev.pigeonproviderapp.view.Adapter.OrderDetails;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dev.pigeonproviderapp.ActivityAll.OrderdetailsSection.ItemDetailsActivity;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.Utility;
import com.dev.pigeonproviderapp.storage.Singleton;
import com.dev.pigeonproviderapp.view.Dataprovider.DeliveryPointListingDatamodel;

import java.util.Collections;
import java.util.List;

public class OrderDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    static Activity activity;
    View myView;
    private LayoutInflater inflater;
    public static List<DeliveryPointListingDatamodel> data= Collections.emptyList();

    public OrderDetailsAdapter(Activity activity, List<DeliveryPointListingDatamodel> data)  /**/
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
        final DeliveryPointListingDatamodel deliveryPointListingDatamodel =data.get(position);

        handler.orderPointName.setText(deliveryPointListingDatamodel.orderpoint_name);


        handler.orderstatus.setText(deliveryPointListingDatamodel.droppoint_status_message);
        handler.orderAddress.setText(deliveryPointListingDatamodel.order_point_address);

        if (deliveryPointListingDatamodel.flatName!=null)
        {
            handler.order_flatNumber.setText(deliveryPointListingDatamodel.flatName);
        }else {
            handler.order_flatNumber.setVisibility(View.GONE);
        }
        handler.dropItemClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Singleton.getInstance().setORDERITEMID(deliveryPointListingDatamodel.delivery_order_id);
                Singleton.getInstance().setORDERITEMSTATUS(deliveryPointListingDatamodel.order_droppoint_status);
                Singleton.getInstance().setPHONENUMBER(deliveryPointListingDatamodel.item_phone_number);
                Singleton.getInstance().setItemcomplete(false);
                Singleton.getInstance().setCollectPayment(deliveryPointListingDatamodel.drop_collect_apyment);

                Intent itemdetails=new Intent(activity, ItemDetailsActivity.class);
                itemdetails.putExtra(Utility.DROPPOINT_TYPE,deliveryPointListingDatamodel.orderpoint_name);
                itemdetails.putExtra(Utility.ADDRESS_KEY,deliveryPointListingDatamodel.order_point_address);
                itemdetails.putExtra(Utility.TIME_KEY,deliveryPointListingDatamodel.delivery_time);
                itemdetails.putExtra(Utility.COMMENT_KEY,deliveryPointListingDatamodel.delivery_comments);
                itemdetails.putExtra(Utility.LAT_KEY, deliveryPointListingDatamodel.droppoint_lat);
                itemdetails.putExtra(Utility.LONG_KEY, deliveryPointListingDatamodel.droppoint_long);
                itemdetails.putExtra(Utility.FLATNAME_KEY, deliveryPointListingDatamodel.flatName);
                itemdetails.putExtra(Utility.REACHADDRESS_KEY, deliveryPointListingDatamodel.addressToReach);
                activity.startActivity(itemdetails);
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
        TextView orderPointName,orderstatus,orderAddress,order_flatNumber;
        LinearLayout dropItemClick;


        public MyHolder(View row)
        {
            super(row);
            orderPointName=(TextView) row.findViewById(R.id.tv_item_point);
            orderstatus=(TextView) row.findViewById(R.id.tv_pickup_address);
            orderAddress=(TextView)row.findViewById(R.id.order_address_details);
            dropItemClick=(LinearLayout)row.findViewById(R.id.ll_drop_item_click);
            order_flatNumber=row.findViewById(R.id.order_flatNumber);



        }
    }
}
