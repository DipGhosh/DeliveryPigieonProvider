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
import com.dev.pigeonproviderapp.view.Adapter.PaymentHistory.BonusAdapter;
import com.dev.pigeonproviderapp.view.Adapter.PaymentHistory.EarnHistoryAdapter;
import com.dev.pigeonproviderapp.view.Dataprovider.BonusDataprovider;
import com.dev.pigeonproviderapp.view.Dataprovider.EarnHistoryDataProvider;

import java.util.ArrayList;
import java.util.List;


public class BonusFragment extends Fragment {

    View mview;
    private Activity activity;

    private RecyclerView bonusHistory_recyclerview;
    private ArrayList<BonusDataprovider> bonus_arraylist = new ArrayList<>();
    private BonusAdapter adapter;
    private ImageView blankImage;


    public BonusFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mview= inflater.inflate(R.layout.fragment_bonus, container, false);

        activity=getActivity();

        blankImage=mview.findViewById(R.id.blank_img);
        bonusHistory_recyclerview = mview.findViewById(R.id.rl_bonus);
        bonusHistory_recyclerview.setLayoutManager(new LinearLayoutManager(activity));
        bonusHistory_recyclerview
                .addItemDecoration(new DividerItemDecoration(activity, LinearLayoutManager.VERTICAL));

        createList();

        return mview;
    }

    private void createList() {

        adapter = new BonusAdapter(activity, bonus_arraylist);
        bonusHistory_recyclerview.setAdapter(adapter);
    }

    /*public void setData(List<PaymentHistoryDataModel.EarningHistory> earningHistoryList) {

        bonus_arraylist.clear();

        if (earningHistoryList.size()>0){


            for (PaymentHistoryDataModel.EarningHistory earningHistory : earningHistoryList) {



                BonusDataprovider bonusDataprovider = new BonusDataprovider();
                bonusDataprovider.paymentType=earningHistory.getPaymentType();
                bonusDataprovider.paymentAmount =earningHistory.getAmount();
                bonusDataprovider.paymentDate = earningHistory.getDate();


                bonus_arraylist.add(bonusDataprovider);

            }

            adapter.notifyDataSetChanged();

        }else {

            blankImage.setVisibility(View.VISIBLE);
        }



    }*/

}