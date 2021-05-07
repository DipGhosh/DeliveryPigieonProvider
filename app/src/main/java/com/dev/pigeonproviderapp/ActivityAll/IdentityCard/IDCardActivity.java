package com.dev.pigeonproviderapp.ActivityAll.IdentityCard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dev.pigeonproviderapp.ActivityAll.OTPSection.ItemDigitalSignature;
import com.dev.pigeonproviderapp.BuildConfig;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.UiShare;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.Utility.Utility;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class IDCardActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    String finalDestination;
    String fileName;
    private Activity activity = IDCardActivity.this;
    private ImageView back, providerImage, downloadImage;
    private ProgressBar progressBar;
    private TextView name, cardNumber, city, ptoviderPhonenumber, pigeonContactNumber, email;
    private String profileImageUrl,url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_d_card);

        providerImage = findViewById(R.id.ic_provider_image);
        back = findViewById(R.id.img_back);
        progressBar = findViewById(R.id.profile_edit_image_progress);

        name = findViewById(R.id.tv_idcard_name);
        cardNumber = findViewById(R.id.tv_idcard_id);
        city = findViewById(R.id.tv_idcardcity);
        ptoviderPhonenumber = findViewById(R.id.tv_provider_phonenumber);
        pigeonContactNumber = findViewById(R.id.tv_pigeon_contactnumber);
        email = findViewById(R.id.tv_email);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            name.setText(bundle.getString(Utility.IDCARD_NAME));
            cardNumber.setText(bundle.getString(Utility.IDCARD_ID));
            city.setText(bundle.getString(Utility.IDCARD_CITY));
            ptoviderPhonenumber.setText(bundle.getString(Utility.IDCARD_PROVIDER_PHONE));
            pigeonContactNumber.setText(bundle.getString(Utility.IDCARD_COMPANY_PHONE));
            email.setText(bundle.getString(Utility.IDCARD_COMPANY_EMAIL));
            profileImageUrl=bundle.getString(Utility.IDCARD_IMAGE);
            url=bundle.getString(Utility.IDCARD_PDF_LINK);

        }

        //Show Image
        if (profileImageUrl != null) {
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
                    .load(profileImageUrl)
                    .into(providerImage, new Callback() {

                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            progressBar.setVisibility(View.GONE);
                            Picasso.with(activity).load(R.drawable.dummy_image).into(providerImage);
                        }
                    });
        } else {
            progressBar.setVisibility(View.GONE);
            Picasso.with(activity).load(R.drawable.dummy_image).into(providerImage);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        findViewById(R.id.ic_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Downloading...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                String destination = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
                fileName = "id_card.pdf";
                destination += fileName;
                final Uri uri = Uri.parse("file://" + destination);

                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.setDescription("Downloading....");
                request.setTitle("TITLE ");
                request.setDestinationUri(uri);

                final DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                final long downloadId = manager.enqueue(request);

                finalDestination = destination;
                BroadcastReceiver onComplete = new BroadcastReceiver() {
                    public void onReceive(Context ctxt, Intent intent) {
                        Log.d("Update status", "Download completed");
                        unregisterReceiver(this);
                        progressDialog.dismiss();
                        UiUtils.showAlert(activity, getString(R.string.app_name), getString(R.string.aleart_pdf_download));

                    }
                };

                registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            }
        });

        findViewById(R.id.ic_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Downloading...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                String destination = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
                fileName = "id_card.pdf";
                destination += fileName;
                final Uri uri = Uri.parse("file://" + destination);



                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.setDescription("Downloading....");
                request.setTitle("TITLE ");
                request.setDestinationUri(uri);

                final DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                final long downloadId = manager.enqueue(request);

                finalDestination = destination;
                BroadcastReceiver onComplete = new BroadcastReceiver() {
                    public void onReceive(Context ctxt, Intent intent) {
                        Log.d("Update status", "Download completed");
                        unregisterReceiver(this);
                        progressDialog.dismiss();

                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Uri screenshotUri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".provider", file);
                        sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                        sharingIntent.putExtra(Intent.EXTRA_TEXT, "");
                        sharingIntent.setType("application/pdf");
                        activity.startActivity(Intent.createChooser(sharingIntent, "Status Bank"));
                    }
                };

                registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

            }
        });
    }


}