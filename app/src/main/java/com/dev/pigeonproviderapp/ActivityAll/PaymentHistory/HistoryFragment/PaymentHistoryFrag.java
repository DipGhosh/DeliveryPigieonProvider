package com.dev.pigeonproviderapp.ActivityAll.PaymentHistory.HistoryFragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.datamodel.ListOrderResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.PaymentHistoryDataModel;
import com.dev.pigeonproviderapp.view.Adapter.ActiveOrder.ActiveOrderListAdapter;
import com.dev.pigeonproviderapp.view.Adapter.PaymentHistory.PaymentHistoryAdapter;
import com.dev.pigeonproviderapp.view.Dataprovider.OrderActiveDatamodel;
import com.dev.pigeonproviderapp.view.Dataprovider.PaymentHistoryDataprovider;

import java.util.ArrayList;
import java.util.List;


public class PaymentHistoryFrag extends Fragment {

    View mview;
    private Activity activity;

    private RecyclerView paymentHistory_recyclerview;
    private ArrayList<PaymentHistoryDataprovider> paymentHistory_arraylist = new ArrayList<>();
    private PaymentHistoryAdapter adapter;
    private ImageView blankImage;


    public PaymentHistoryFrag() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mview= inflater.inflate(R.layout.fragment_payment_history, container, false);
        activity=getActivity();

        blankImage=mview.findViewById(R.id.blank_img);
        paymentHistory_recyclerview = mview.findViewById(R.id.rl_payment_history);
        paymentHistory_recyclerview.setLayoutManager(new LinearLayoutManager(activity));
        paymentHistory_recyclerview
                .addItemDecoration(new DividerItemDecoration(activity, LinearLayoutManager.VERTICAL));

        createList();

        return mview;
    }

    private void createList() {

        adapter = new PaymentHistoryAdapter(activity, paymentHistory_arraylist);
        paymentHistory_recyclerview.setAdapter(adapter);
    }

    public void setData(List<PaymentHistoryDataModel.PaymentHistory> paymentHistories) {

        paymentHistory_arraylist.clear();

        if (paymentHistories.size()>0){


            for (PaymentHistoryDataModel.PaymentHistory paymentHistory : paymentHistories) {



                PaymentHistoryDataprovider paymentHistoryDataprovider = new PaymentHistoryDataprovider();
                paymentHistoryDataprovider.paymentType=paymentHistory.getPaymentType();
                paymentHistoryDataprovider.paymentAmount =paymentHistory.getAmount();
                paymentHistoryDataprovider.paymentDate = paymentHistory.getDate();


                paymentHistory_arraylist.add(paymentHistoryDataprovider);

            }

            adapter.notifyDataSetChanged();

        }else {

            blankImage.setVisibility(View.VISIBLE);
        }



    }
}