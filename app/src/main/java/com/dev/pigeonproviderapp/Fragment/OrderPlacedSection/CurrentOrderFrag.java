package com.dev.pigeonproviderapp.Fragment.OrderPlacedSection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.pigeonproviderapp.ActivityAll.OrderdetailsSection.ActiveorderDetails;
import com.dev.pigeonproviderapp.R;


public class CurrentOrderFrag extends Fragment {


    View mview;
    private Activity activity;
    private TextView viewDetails;
    public CurrentOrderFrag() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mview=inflater.inflate(R.layout.fragment_current_order, container, false);
        activity=getActivity();
        viewDetails=mview.findViewById(R.id.tv_view_details);
        viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, ActiveorderDetails.class);
                startActivity(intent);
            }
        });
        return mview;
    }
}