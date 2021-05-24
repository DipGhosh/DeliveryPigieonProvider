package com.dev.pigeonproviderapp.view.Adapter.PastOrder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.dev.pigeonproviderapp.ActivityAll.OrderdetailsSection.OrderDetails;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.storage.Singleton;
import com.dev.pigeonproviderapp.view.Dataprovider.ChildItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChildItemAdapter extends RecyclerView.Adapter<ChildItemAdapter.ChildViewHolder>{

    private List<ChildItem> ChildItemList;
    static Activity activity;

    // Constuctor
    ChildItemAdapter(Activity activity, List<ChildItem> childItemList)
    {
        this.activity = activity;
        this.ChildItemList = childItemList;
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(
            @NonNull ViewGroup viewGroup,
            int i)
    {

        // Here we inflate the corresponding
        // layout of the child item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.child_item, viewGroup, false);

        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ChildViewHolder childViewHolder,
            int position)
    {

        // Create an instance of the ChildItem
        // class for the given position
        ChildItem childItem = ChildItemList.get(position);

        // For the created instance, set title.
        // No need to set the image for
        // the ImageViews because we have
        // provided the source for the images
        // in the layout file itself
        childViewHolder.deliveryType.setText(childItem.getPlacedorder_type());

        if(childItem.getPlacedorder_type().equals("1"))
        {
            childViewHolder.deliveryType.setText("Hyper Local");
        }else if(childItem.getPlacedorder_type().equals("2"))
        {
            childViewHolder.deliveryType.setText("Hyper Local");
        }else {
            childViewHolder.deliveryType.setText("Multi Points");
        }

        if (childItem.getOrder_status_message().equals("Cancelled"))
        {
            childViewHolder.orderStatus.setTextColor(Color.RED);
        }else {
            childViewHolder.orderStatus.setTextColor(Color.BLACK);
        }


        childViewHolder.currentOrderPickupAddress.setText(childItem.getOrderplace_pickup_address());
        childViewHolder.currentOrderDeliveryAddress.setText(childItem.getOrderplace_delivery_address());
        childViewHolder.currentOrderPrice.setText("Earn: "+"₹ "+childItem.earnAmount);
        childViewHolder.orderId.setText("Order "+childItem.getOrderId());
        childViewHolder.pickupTime.setVisibility(View.GONE);
        childViewHolder.orderStatus.setText(childItem.getOrder_status_message());

        if (childItem.getProvider_bonus()<=0)
        {
            childViewHolder.bonusPrice.setVisibility(View.GONE);
        }else {
            childViewHolder.bonusPrice.setText("Bonus: "+"₹ " +childItem.getProvider_bonus());
        }


        childViewHolder.vieworderDetailsClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, OrderDetails.class);
                intent.putExtra("TypeOfOrder","pastorder");
                activity.startActivity(intent);
                Singleton.getInstance().setORDERID(childItem.getCurrentorder_id());
                Singleton.getInstance().setOrderaccept(false);
            }
        });



    }

    @Override
    public int getItemCount()
    {

        // This method returns the number
        // of items we have added
        // in the ChildItemList
        // i.e. the number of instances
        // of the ChildItemList
        // that have been created
        return ChildItemList.size();
    }

    // This class is to initialize
    // the Views present
    // in the child RecyclerView
    class ChildViewHolder extends RecyclerView.ViewHolder {

        TextView deliveryType,currentOrderPickupAddress,currentOrderDeliveryAddress,currentOrderPrice,vieworderDetailsClick,orderId,pickupTime,bonusPrice,orderStatus;


        ChildViewHolder(View row)
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
            orderStatus=row.findViewById(R.id.tv_order_status);
        }
    }
}
