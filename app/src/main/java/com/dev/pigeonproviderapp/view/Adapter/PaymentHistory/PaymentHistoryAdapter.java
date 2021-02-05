package com.dev.pigeonproviderapp.view.Adapter.PaymentHistory;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.pigeonproviderapp.ActivityAll.OrderdetailsSection.OrderDetails;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.storage.Singleton;
import com.dev.pigeonproviderapp.view.Adapter.ActiveOrder.ActiveOrderListAdapter;
import com.dev.pigeonproviderapp.view.Dataprovider.OrderActiveDatamodel;
import com.dev.pigeonproviderapp.view.Dataprovider.PaymentHistoryDataprovider;

import java.util.Collections;
import java.util.List;

public class PaymentHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    static Activity activity;
    View myView;
    private LayoutInflater inflater;
    public static List<PaymentHistoryDataprovider> data= Collections.emptyList();


    public PaymentHistoryAdapter(Activity activity, List<PaymentHistoryDataprovider> data)  /**/
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
        myView = inflater.inflate(R.layout.payment_history_layout, parent, false);
        PaymentHistoryAdapter.MyHolder holder = new PaymentHistoryAdapter.MyHolder(myView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position)
    {
        final PaymentHistoryAdapter.MyHolder handler = (PaymentHistoryAdapter.MyHolder) holder;
        final PaymentHistoryDataprovider paymentHistoryDataprovider=data.get(position);



        handler.paymentType.setText(paymentHistoryDataprovider.paymentType);
        handler.paymentAmount.setText("₹"+""+paymentHistoryDataprovider.paymentAmount);
        handler.paymentDate.setText(paymentHistoryDataprovider.paymentDate);




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
        TextView paymentType,paymentAmount,paymentDate;


        public MyHolder(View row)
        {
            super(row);
            paymentType=(TextView) row.findViewById(R.id.tv_payment_type);
            paymentAmount=(TextView) row.findViewById(R.id.tv_payment_amount);
            paymentDate=(TextView)row.findViewById(R.id.tv_payment_date);





        }
    }
}
