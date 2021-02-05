package com.dev.pigeonproviderapp.ActivityAll.PaymentHistory.HistoryFragment;

import android.app.Activity;
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
import com.dev.pigeonproviderapp.datamodel.PaymentHistoryDataModel;
import com.dev.pigeonproviderapp.view.Adapter.PaymentHistory.EarnHistoryAdapter;
import com.dev.pigeonproviderapp.view.Adapter.PaymentHistory.PaymentHistoryAdapter;
import com.dev.pigeonproviderapp.view.Dataprovider.EarnHistoryDataProvider;
import com.dev.pigeonproviderapp.view.Dataprovider.PaymentHistoryDataprovider;

import java.util.ArrayList;
import java.util.List;


public class EarnHistoryFrag extends Fragment {

    View mview;
    private Activity activity;

    private RecyclerView earnHistory_recyclerview;
    private ArrayList<EarnHistoryDataProvider> earnHistory_arraylist = new ArrayList<>();
    private EarnHistoryAdapter adapter;
    private ImageView blankImage;

    public EarnHistoryFrag() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mview= inflater.inflate(R.layout.fragment_earn_history, container, false);
        activity=getActivity();

        blankImage=mview.findViewById(R.id.blank_img);
        earnHistory_recyclerview = mview.findViewById(R.id.rl_earn_history);
        earnHistory_recyclerview.setLayoutManager(new LinearLayoutManager(activity));
        earnHistory_recyclerview
                .addItemDecoration(new DividerItemDecoration(activity, LinearLayoutManager.VERTICAL));

        return mview;
    }

    private void createList() {

        adapter = new EarnHistoryAdapter(activity, earnHistory_arraylist);
        earnHistory_recyclerview.setAdapter(adapter);
    }

    public void setData(List<PaymentHistoryDataModel.EarningHistory> earningHistoryList) {

        earnHistory_arraylist.clear();

        if (earningHistoryList.size()>0){


            for (PaymentHistoryDataModel.EarningHistory earningHistory : earningHistoryList) {



                EarnHistoryDataProvider earnHistoryDataProvider = new EarnHistoryDataProvider();
                earnHistoryDataProvider.paymentType=earningHistory.getPaymentType();
                earnHistoryDataProvider.paymentAmount =earningHistory.getAmount();
                earnHistoryDataProvider.paymentDate = earningHistory.getDate();


                earnHistory_arraylist.add(earnHistoryDataProvider);

            }

            adapter.notifyDataSetChanged();

        }else {

            blankImage.setVisibility(View.VISIBLE);
        }



    }

}