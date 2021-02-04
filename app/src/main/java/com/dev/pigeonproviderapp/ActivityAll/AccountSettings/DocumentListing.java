package com.dev.pigeonproviderapp.ActivityAll.AccountSettings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.dev.pigeonproviderapp.Baseclass.BaseActivity;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.datamodel.BankDetailsGetModelResponse;
import com.dev.pigeonproviderapp.datamodel.GetUserDocumentResponseDataModel;
import com.dev.pigeonproviderapp.viewmodel.ProfileViewModel;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DocumentListing extends BaseActivity implements View.OnClickListener {

    private Activity activity = DocumentListing.this;

    private ImageView back;
    private ImageView adharcardFontsideImageUpload, adharcardBacksideImageUpload, panCardImageupload, otherDetailsImageupload;
    private ProgressBar aadharFontProgress,aadharBackProgress,panProgress,othersProgress;
    private RelativeLayout rlAdarFontUpload,rlAharFontEdit,rlAdharBackUpload,rlAdharBackEdit,rlPancardUpload,rlPancardEdit,rlOthersUpload,rlOthersEdit;

    ProfileViewModel profileViewModel;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_listing);

        dialog = UiUtils.showProgress(DocumentListing.this);


        back = findViewById(R.id.img_back);
        adharcardFontsideImageUpload = findViewById(R.id.ic_adharcard_fontsideImageUpload);
        adharcardBacksideImageUpload = findViewById(R.id.ic_adharcard_backsideImageUpload);
        panCardImageupload = findViewById(R.id.ic_pandcardImageUpload);
        otherDetailsImageupload = findViewById(R.id.ic_others_documentImageUpload);

        aadharFontProgress = findViewById(R.id.aadhar_progress_fontside);
        aadharBackProgress = findViewById(R.id.aadhar_progress_backside);
        panProgress = findViewById(R.id.aadhar_progress_pancard);
        othersProgress = findViewById(R.id.aadhar_progress_others);

        rlAdarFontUpload = findViewById(R.id.rl_adarfont_upload);
        rlAharFontEdit = findViewById(R.id.rl_adarfont_edit);
        rlAdharBackUpload = findViewById(R.id.rl_adarback_upload);
        rlAdharBackEdit = findViewById(R.id.rl_adarback_edit);
        rlAdharBackUpload = findViewById(R.id.rl_adarback_upload);
        rlAdharBackEdit = findViewById(R.id.rl_adarback_edit);
        rlPancardUpload = findViewById(R.id.rl_pancard_upload);
        rlPancardEdit = findViewById(R.id.rl_pancard_edit);
        rlOthersUpload = findViewById(R.id.rl_others_upload);
        rlOthersEdit = findViewById(R.id.rl_others_edit);

        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        back.setOnClickListener(this);

        callDocumentListingCall();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId() /*to get clicked view id**/) {
            case R.id.img_back:
                finish();
                break;

            default:
                break;
        }
    }

    public void callDocumentListingCall() {

        dialog.show();

        profileViewModel.getUserDocument().observe(this, new Observer<GetUserDocumentResponseDataModel>() {
            @Override
            public void onChanged(GetUserDocumentResponseDataModel userDocumentResponseDataModel) {

                dialog.dismiss();

                if (userDocumentResponseDataModel.getStatus()==200 && userDocumentResponseDataModel.getData().getDocuments().size()>0)
                {
                    for (GetUserDocumentResponseDataModel.Document data : userDocumentResponseDataModel.getData().getDocuments())
                    {
                           if (data.getName().equals("Address Proof Front"))
                           {
                               loadImageFile(data.getFileNameFront(),adharcardFontsideImageUpload,aadharFontProgress);
                           } else if (data.getName().equals("Address Proof Back")) {
                               loadImageFile(data.getFileNameFront(),adharcardBacksideImageUpload,aadharBackProgress);
                           }
                           else if (data.getName().equals("PAN")) {
                               loadImageFile(data.getFileNameFront(),panCardImageupload,panProgress);
                           }
                           else if (data.getName().equals("Others")) {
                               loadImageFile(data.getFileNameFront(),otherDetailsImageupload,othersProgress);
                           }
                    }
                }




            }
        });


    }

    private void loadImageFile(String url,ImageView imageView,ProgressBar progressBar)
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