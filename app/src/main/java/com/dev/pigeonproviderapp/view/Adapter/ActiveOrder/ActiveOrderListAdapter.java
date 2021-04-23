package com.dev.pigeonproviderapp.view.Adapter.ActiveOrder;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.pigeonproviderapp.ActivityAll.OrderdetailsSection.OrderDetails;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.interfaces.onActiveOrderClickListener;
import com.dev.pigeonproviderapp.storage.Singleton;
import com.dev.pigeonproviderapp.view.Dataprovider.OrderActiveDatamodel;
import com.dev.pigeonproviderapp.viewmodel.OrderListViewModel;

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


        if(orderActiveDatamodel.activeorder_type.equals("1"))
        {
            handler.deliveryType.setText("Hyper Local");
        }else if(orderActiveDatamodel.activeorder_type.equals("2"))
        {
            handler.deliveryType.setText("Hyper Local");
        }else {
            handler.deliveryType.setText("Multi Points");
        }

        handler.currentOrderPickupAddress.setText(orderActiveDatamodel.activeorder_pickup_address);
        handler.currentOrderDeliveryAddress.setText(orderActiveDatamodel.activeorder_delivery_address);
        handler.currentOrderPrice.setText("Earn: "+"₹ "+orderActiveDatamodel.earnAmount);
        handler.orderId.setText("Order "+orderActiveDatamodel.orderId);
        handler.pickupTime.setText("Pickup Time: "+orderActiveDatamodel.pickuptime);

        if (orderActiveDatamodel.provider_bonus<=0)
        {
            handler.bonusPrice.setVisibility(View.GONE);
        }else {
            handler.bonusPrice.setText("Bonus: "+"₹ " +orderActiveDatamodel.provider_bonus);
        }


        handler.vieworderDetailsClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, OrderDetails.class);
                intent.putExtra("TypeOfOrder","activeorder");
                activity.startActivity(intent);
                Singleton.getInstance().setORDERID(orderActiveDatamodel.activeorder_id);
                Singleton.getInstance().setOrderaccept(false);
            }
        });
        handler.acceptOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
                builder.setTitle("Accept Order");
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage(R.string.aleart_accept_order);
                builder.setPositiveButton(R.string.label_ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String orderID= String.valueOf(orderActiveDatamodel.activeorder_id);
                                Intent intent = new Intent("custom-message");
                                intent.putExtra("ORDERSTATUS" ,"Accepted");
                                intent.putExtra("ORDERID",orderID);
                                LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);
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
        TextView deliveryType,currentOrderPickupAddress,currentOrderDeliveryAddress,currentOrderPrice,vieworderDetailsClick,acceptOrder,bonusPrice,orderId,pickupTime;


        public MyHolder(View row)
        {
            super(row);
            deliveryType=(TextView) row.findViewById(R.id.tv_deliveryType);
            currentOrderPickupAddress=(TextView) row.findViewById(R.id.tv_pickup_address);
            currentOrderDeliveryAddress=(TextView)row.findViewById(R.id.tv_delivery_address);
            currentOrderPrice=(TextView)row.findViewById(R.id.tv_price);
            vieworderDetailsClick=(TextView)row.findViewById(R.id.tv_view_details);
            acceptOrder=row.findViewById(R.id.tv_accept_order);
            bonusPrice=row.findViewById(R.id.tvbonus_bounus);
            orderId=row.findViewById(R.id.tv_order_id);
            pickupTime=row.findViewById(R.id.tv_pickup_time);





        }
    }
}
