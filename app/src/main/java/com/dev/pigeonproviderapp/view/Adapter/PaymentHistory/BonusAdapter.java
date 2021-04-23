package com.dev.pigeonproviderapp.view.Adapter.PaymentHistory;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.view.Dataprovider.BonusDataprovider;
import com.dev.pigeonproviderapp.view.Dataprovider.BonusHistoryDatamodel;

import java.util.Collections;
import java.util.List;

public class BonusAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    static Activity activity;
    View myView;
    private LayoutInflater inflater;
    public static List<BonusDataprovider> data= Collections.emptyList();

    public BonusAdapter(Activity activity, List<BonusDataprovider> data)  /**/
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
        final BonusAdapter.MyHolder handler = (BonusAdapter.MyHolder) holder;
        final BonusDataprovider bonusDataprovider=data.get(position);



        handler.paymentType.setText(bonusDataprovider.paymentType);
        handler.paymentAmount.setText(""+bonusDataprovider.paymentAmount);
        handler.paymentDate.setText(bonusDataprovider.paymentDate);




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
