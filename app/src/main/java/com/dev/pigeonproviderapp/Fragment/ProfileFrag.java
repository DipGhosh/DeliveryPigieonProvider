package com.dev.pigeonproviderapp.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dev.pigeonproviderapp.ActivityAll.AccountSettings.AccountSetting;
import com.dev.pigeonproviderapp.ActivityAll.PaymentHistory.PaymentHistoryActivity;
import com.dev.pigeonproviderapp.ActivityAll.ProfileEdit;
import com.dev.pigeonproviderapp.ActivityAll.ProviderRegistration.Registrationactivity;
import com.dev.pigeonproviderapp.Baseclass.BaseFragment;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.NetworkUtils;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.Utility.Utility;
import com.dev.pigeonproviderapp.datamodel.ProfileGetResponseDataModel;
import com.dev.pigeonproviderapp.httpRequest.AddDocumentAPIModel;
import com.dev.pigeonproviderapp.httpRequest.ProviderAvailabilityAPIModel;
import com.dev.pigeonproviderapp.storage.SharePreference;
import com.dev.pigeonproviderapp.storage.Singleton;
import com.dev.pigeonproviderapp.view.WebViewLinkShow.WebserviceActivity;
import com.dev.pigeonproviderapp.viewmodel.ProfileViewModel;
import com.google.android.gms.common.api.Status;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;


public class ProfileFrag extends BaseFragment implements View.OnClickListener {

   View view;
   private ImageView profileImage;
   private Activity activity;
   private TextView userPhoneNumber,userEmailId,userName,approvalStatus,profileEdit;
   private ProgressBar profileFragProgress;
   private String profile_pic_url;
   private LinearLayout logout,privacyPolicyClick,aboutUsClik,termsofServicesClick,accountSettingClick,PaymentHistoryClick;
   private SharePreference sharePreference;
   private Dialog dialog;
   private ToggleButton simpleToggleButton;
   private int toggleValue=1;

   ProfileViewModel profileViewModel;

   private int INTENT_REQUEST_ORDERTYPE = 3;

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
        privacyPolicyClick=view.findViewById(R.id.ll_privacy_policy);
        aboutUsClik=view.findViewById(R.id.ll_about_us);
        termsofServicesClick=view.findViewById(R.id.ll_terms_of_services);
        accountSettingClick=view.findViewById(R.id.ll_account_settingsClick);
        PaymentHistoryClick=view.findViewById(R.id.ll_payment_historyClick);
        approvalStatus=view.findViewById(R.id.tv_profile_approval_status);
        simpleToggleButton =view.findViewById(R.id.chkState);


        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        dialog = UiUtils.showProgress(activity);



        //Registered click listener
        profileEdit.setOnClickListener(this);
        logout.setOnClickListener(this);
        privacyPolicyClick.setOnClickListener(this);
        aboutUsClik.setOnClickListener(this);
        termsofServicesClick.setOnClickListener(this);
        accountSettingClick.setOnClickListener(this);
        PaymentHistoryClick.setOnClickListener(this);
        simpleToggleButton.setOnClickListener(this);

        if (NetworkUtils.isNetworkAvailable(activity))
        {
            callGetProfile();

        }else {
            UiUtils.showToast(activity, getString(R.string.network_error));
        }




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
                startActivityForResult(profileEdit, INTENT_REQUEST_ORDERTYPE);
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
            case R.id.ll_privacy_policy:
                Intent privacyPolicyIntent=new Intent(activity, WebserviceActivity.class);
                privacyPolicyIntent.putExtra(Utility.HEADER_KEY,Utility.PRIVACY_POLICY_HEADER);
                privacyPolicyIntent.putExtra(Utility.LINK_KEY,Utility.PRIVACYPOLICY_LINK);
                startActivity(privacyPolicyIntent);

                break;
            case R.id.ll_about_us:
                Intent aboutusIntent=new Intent(activity, WebserviceActivity.class);
                aboutusIntent.putExtra(Utility.HEADER_KEY,Utility.ABOUTUS_HEADER);
                aboutusIntent.putExtra(Utility.LINK_KEY,Utility.ABOUTUS_LINK);
                startActivity(aboutusIntent);

                break;
            case R.id.ll_terms_of_services:
                Intent termsIntent=new Intent(activity, WebserviceActivity.class);
                termsIntent.putExtra(Utility.HEADER_KEY,Utility.TERMRS_OF_SERVICE_HEADER);
                termsIntent.putExtra(Utility.LINK_KEY,Utility.TERMSSERVICES_LINK);
                startActivity(termsIntent);

                break;
            case R.id.ll_account_settingsClick:
                Intent accountsettings=new Intent(activity, AccountSetting.class);
                startActivity(accountsettings);
                break;
            case R.id.ll_payment_historyClick:
                Intent paymentHistory=new Intent(activity, PaymentHistoryActivity.class);
                startActivity(paymentHistory);

                break;
            case R.id.chkState:
                /*if (simpleToggleButton.isChecked()) {
                    //System.out.println("Check"+"Y");
                    toggleValue=1;
                    ProviderAvailableToggle();

                } else {
                    //System.out.println("Check"+"N");
                    toggleValue=0;
                    ProviderAvailableToggle();


                }*/

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


               userPhoneNumber.setText(""+number);
               userEmailId.setText(profileGetResponseDataModel.getData().getUser().getEmail());
               userName.setText(profileGetResponseDataModel.getData().getUser().getName());

               profile_pic_url=profileGetResponseDataModel.getData().getUser().getProfilePicture();

               //Profile update boolean value set in Singleton class
                if (profileGetResponseDataModel.getData().getUser().getIsValid()==true)
                {
                    sharePreference.setProfileverified(true);
                    Singleton.getInstance().setProfileUpdated(false);
                }else {
                    sharePreference.setProfileverified(false);
                    Singleton.getInstance().setProfileUpdated(false);
                    UiUtils.showAlert(activity,getString(R.string.app_name),getString(R.string.profile_verification_aleart));
                }


                //User Status
                approvalStatus.setText(profileGetResponseDataModel.getData().getUser().getStatus());

                //Avilable button check/uncheck
               if (profileGetResponseDataModel.getData().getUser().getIsAvailable()==true)
               {
                  // simpleToggleButton.setChecked(true);
                   sharePreference.setProviderAvailable(true);
               }else {
                   //simpleToggleButton.setChecked(false);
                   sharePreference.setProviderAvailable(false);
               }

               //Image download and show
                if (profile_pic_url != null)
                {
                    loadImageFile(profile_pic_url, profileImage, profileFragProgress);

                }else {
                    Picasso.with(activity).load(R.drawable.dummy_image).into(profileImage);
                    profileFragProgress.setVisibility(View.GONE);
                }


            }
        });

        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {

            if (requestCode == INTENT_REQUEST_ORDERTYPE && resultCode == RESULT_OK) {
                String requiredValue = data.getStringExtra(Utility.EDIT_NAME);
                userEmailId.setText(data.getStringExtra(Utility.EDIT_EMAIL));
                userName.setText(data.getStringExtra(Utility.EDIT_NAME));

                //Image download and show
                if (data.getStringExtra(Utility.EDIT_PIC) != null)
                {
                    loadImageFile(data.getStringExtra(Utility.EDIT_PIC), profileImage, profileFragProgress);

                }
            }


        } catch (Exception ex) {
            // Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
            Log.d("Aslambackk", ex.toString());
        }

        super.onActivityResult(requestCode, resultCode, data);
    }



    private void loadImageFile(String url, ImageView imageView, ProgressBar progressBar)
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
        progressBar.setVisibility(View.VISIBLE);
        picasso.with(activity)
                .load(url)
                .into(imageView, new Callback() {

                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onError() {
                        progressBar.setVisibility(View.GONE);
                        Picasso.with(activity).load(R.drawable.dummy_image).into(imageView);

                    }
                });
    }





}