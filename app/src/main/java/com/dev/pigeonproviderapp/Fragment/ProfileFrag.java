package com.dev.pigeonproviderapp.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.pigeonproviderapp.ActivityAll.ProfileEdit;
import com.dev.pigeonproviderapp.Baseclass.BaseFragment;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.datamodel.OTPSendResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.ProfileGetResponseDataModel;
import com.dev.pigeonproviderapp.httpRequest.ProfileUpdateAPI;
import com.dev.pigeonproviderapp.viewmodel.ProfileViewModel;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ProfileFrag extends BaseFragment {

   View view;
   private ImageView profileEdit,profileImage;
   private Activity activity;
   private TextView userPhoneNumber,userEmailId,userName;

   ProfileViewModel profileViewModel;

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
        profileImage=view.findViewById(R.id.ic_profile_img);
        userPhoneNumber=view.findViewById(R.id.tv_phoneNumber);
        userEmailId=view.findViewById(R.id.tv_emailId);
        userName=view.findViewById(R.id.tv_userName);

        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        callGetProfile();

        profileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, ProfileEdit.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void callGetProfile() {

        profileViewModel.gerProfile().observe(this, new Observer<ProfileGetResponseDataModel>() {
            @Override
            public void onChanged(ProfileGetResponseDataModel profileGetResponseDataModel) {
                int phonenumber=profileGetResponseDataModel.getData().getUser().getUser().getPhone();
               userPhoneNumber.setText("+"+String.valueOf(phonenumber));
               userEmailId.setText(profileGetResponseDataModel.getData().getUser().getUser().getEmail());
               userName.setText(profileGetResponseDataModel.getData().getUser().getUser().getName());

               String profile_pic_url=profileGetResponseDataModel.getData().getUser().getUser().getProfilePicture();

                OkHttpClient client = new OkHttpClient.Builder()
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request newRequest = chain.request().newBuilder()
                                        .build();
                                return chain.proceed(newRequest);
                            }
                        })
                        .build();

                Picasso picasso = new Picasso.Builder(activity)
                        .downloader(new OkHttp3Downloader(client))
                        .build();
                picasso.load(profile_pic_url).into(profileImage);

            }
        });

        }


}