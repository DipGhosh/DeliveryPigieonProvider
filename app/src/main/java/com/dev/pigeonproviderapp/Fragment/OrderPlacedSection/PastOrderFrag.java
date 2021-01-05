package com.dev.pigeonproviderapp.Fragment.OrderPlacedSection;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.pigeonproviderapp.R;


public class PastOrderFrag extends Fragment {



    public PastOrderFrag() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_past_order, container, false);
    }
}