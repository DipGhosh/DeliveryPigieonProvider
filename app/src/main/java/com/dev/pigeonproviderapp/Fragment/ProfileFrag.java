package com.dev.pigeonproviderapp.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dev.pigeonproviderapp.ActivityAll.ProfileEdit;
import com.dev.pigeonproviderapp.ActivityAll.ProviderRegistration.Registrationactivity;
import com.dev.pigeonproviderapp.Baseclass.BaseFragment;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.datamodel.ProfileGetResponseDataModel;
import com.dev.pigeonproviderapp.storage.SharePreference;
import com.dev.pigeonproviderapp.viewmodel.ProfileViewModel;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class ProfileFrag extends BaseFragment implements View.OnClickListener {

   View view;
   private ImageView profileEdit,profileImage;
   private Activity activity;
   private TextView userPhoneNumber,userEmailId,userName;
   private ProgressBar profileFragProgress;
   private String profile_pic_url;
   private LinearLayout logout;
   private SharePreference sharePreference;
   private Dialog dialog;

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
        sharePreference=new SharePreference(activity);

        profileEdit=view.findViewById(R.id.img_edit);
        profileImage=view.findViewById(R.id.ic_profile_img);
        userPhoneNumber=view.findViewById(R.id.tv_phoneNumber);
        userEmailId=view.findViewById(R.id.tv_emailId);
        userName=view.findViewById(R.id.tv_userName);
        profileFragProgress=view.findViewById(R.id.profile_frag_image_progress);
        logout=view.findViewById(R.id.ll_logout);

        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        dialog = UiUtils.showProgress(activity);

        callGetProfile();

        //Registered click listener
        profileEdit.setOnClickListener(this);
        logout.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_edit:
                Intent profileEdit=new Intent(activity, ProfileEdit.class);
                profileEdit.putExtra("NAME", userName.getText().toString());
                profileEdit.putExtra("EMAIL",userEmailId.getText().toString());
                profileEdit.putExtra("URL",profile_pic_url);
                startActivity(profileEdit);
                break;

            case R.id.ll_logout:
                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
                builder.setTitle(getResources().getString(R.string.app_name));
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage(R.string.logout_aleart_message);
                builder.setPositiveButton(R.string.label_ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                sharePreference.LogOut();
                                Intent logout=new Intent(activity, Registrationactivity.class);
                                startActivity(logout);
                            }
                        });
                builder.setNegativeButton(R.string.label_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                final android.app.AlertDialog alert = builder.create();
                alert.show();


                break;

            default:
                break;
        }
    }


    public void callGetProfile() {

         dialog.show();

        profileViewModel.gerProfile().observe(this, new Observer<ProfileGetResponseDataModel>() {
            @Override
            public void onChanged(ProfileGetResponseDataModel profileGetResponseDataModel) {

                dialog.dismiss();

                long number=profileGetResponseDataModel.getData().getUser().getPhone();


               userPhoneNumber.setText("+"+number);
               userEmailId.setText(profileGetResponseDataModel.getData().getUser().getEmail());
               userName.setText(profileGetResponseDataModel.getData().getUser().getName());

               profile_pic_url=profileGetResponseDataModel.getData().getUser().getProfilePicture();

               //Image download and show
                if (profile_pic_url != null)
                {
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
                    profileFragProgress.setVisibility(View.VISIBLE);
                    picasso.with(activity)
                            .load(profile_pic_url)
                            .into(profileImage, new Callback() {

                                @Override
                                public void onSuccess() {
                                    profileFragProgress.setVisibility(View.GONE);
                                }

                                @Override
                                public void onError() {
                                    profileFragProgress.setVisibility(View.GONE);
                                    Picasso.with(activity).load(R.drawable.dummy_image).into(profileImage);
                                }
                            });
                }else {
                    Picasso.with(activity).load(R.drawable.dummy_image).into(profileImage);
                    profileFragProgress.setVisibility(View.GONE);
                }


            }
        });

        }


}