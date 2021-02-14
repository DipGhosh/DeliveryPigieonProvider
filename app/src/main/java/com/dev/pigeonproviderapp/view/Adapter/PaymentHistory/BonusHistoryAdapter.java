package com.dev.pigeonproviderapp.view.Adapter.PaymentHistory;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.view.Dataprovider.BonusHistoryDatamodel;
import com.dev.pigeonproviderapp.view.Dataprovider.EarnHistoryDataProvider;

import java.util.Collections;
import java.util.List;

public class BonusHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    static Activity activity;
    View myView;
    private LayoutInflater inflater;
    public static List<BonusHistoryDatamodel> data= Collections.emptyList();


    public BonusHistoryAdapter(Activity activity, List<BonusHistoryDatamodel> data)  /**/
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
        BonusHistoryAdapter.MyHolder holder = new BonusHistoryAdapter.MyHolder(myView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position)
    {
        final BonusHistoryAdapter.MyHolder handler = (BonusHistoryAdapter.MyHolder) holder;
        final BonusHistoryDatamodel bonusHistoryDataProvider=data.get(position);



        handler.paymentType.setText(bonusHistoryDataProvider.paymentType);
        handler.paymentAmount.setText(""+bonusHistoryDataProvider.paymentAmount);
        handler.paymentDate.setText(bonusHistoryDataProvider.paymentDate);




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
