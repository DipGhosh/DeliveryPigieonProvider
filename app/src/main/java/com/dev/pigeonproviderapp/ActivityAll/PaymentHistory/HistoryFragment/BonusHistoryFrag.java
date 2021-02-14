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

import com.dev.pigeonproviderapp.Baseclass.BaseFragment;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.datamodel.PaymentHistoryDataModel;
import com.dev.pigeonproviderapp.view.Adapter.PaymentHistory.BonusHistoryAdapter;
import com.dev.pigeonproviderapp.view.Adapter.PaymentHistory.PaymentHistoryAdapter;
import com.dev.pigeonproviderapp.view.Dataprovider.BonusHistoryDatamodel;
import com.dev.pigeonproviderapp.view.Dataprovider.PaymentHistoryDataprovider;

import java.util.ArrayList;
import java.util.List;


public class BonusHistoryFrag extends BaseFragment {

    View mview;
    private Activity activity;

    private RecyclerView bonusHistory_recyclerview;
    private ArrayList<BonusHistoryDatamodel>bonusHistory_arraylist = new ArrayList<>();
    private BonusHistoryAdapter adapter;
    private ImageView blankImage;

    public BonusHistoryFrag() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mview= inflater.inflate(R.layout.fragment_bonus_history, container, false);

        activity=getActivity();

        blankImage=mview.findViewById(R.id.blank_img);
        bonusHistory_recyclerview = mview.findViewById(R.id.rl_bonus_history);
        bonusHistory_recyclerview.setLayoutManager(new LinearLayoutManager(activity));
        bonusHistory_recyclerview
                .addItemDecoration(new DividerItemDecoration(activity, LinearLayoutManager.VERTICAL));

        createList();

        return mview;
    }

    private void createList() {

        adapter = new BonusHistoryAdapter(activity, bonusHistory_arraylist);
        bonusHistory_recyclerview.setAdapter(adapter);
    }


    public void setData(List<PaymentHistoryDataModel.BonusHistory> bonusHistories) {

        bonusHistory_arraylist.clear();

        if (bonusHistories.size()>0){


            for (PaymentHistoryDataModel.BonusHistory bonusHistory : bonusHistories) {



                BonusHistoryDatamodel bonusHistoryDatamodel = new BonusHistoryDatamodel();
                bonusHistoryDatamodel.paymentType=bonusHistory.getPaymentType();
                bonusHistoryDatamodel.paymentAmount =bonusHistory.getAmount();
                bonusHistoryDatamodel.paymentDate = bonusHistory.getDate();


                bonusHistory_arraylist.add(bonusHistoryDatamodel);

            }

            adapter.notifyDataSetChanged();

        }else {

            blankImage.setVisibility(View.VISIBLE);
        }



    }
}