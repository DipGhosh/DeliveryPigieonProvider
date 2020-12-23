package com.dev.pigeonproviderapp.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dev.pigeonproviderapp.ProfileEdit;
import com.dev.pigeonproviderapp.ProviderDetails;
import com.dev.pigeonproviderapp.R;


public class ProfileFrag extends Fragment {

   View view;
   ImageView profileEdit;
   Activity activity;

    public ProfileFrag() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_profile, container, false);
        activity=getActivity();
        profileEdit=view.findViewById(R.id.img_edit);
        profileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, ProfileEdit.class);
                startActivity(intent);
            }
        });

        return view;
    }
}