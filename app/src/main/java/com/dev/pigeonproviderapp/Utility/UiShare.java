package com.dev.pigeonproviderapp.Utility;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;


import com.dev.pigeonproviderapp.BuildConfig;
import com.dev.pigeonproviderapp.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;

public class UiShare {
    static ProgressDialog progressDialog;






    public static void Downloadvideo(final String url, final String filename, final Context context)
    {

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),filename);
        if(file.exists()){

            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri screenshotUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
            sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
            sharingIntent.putExtra(Intent.EXTRA_TEXT, "");
            sharingIntent.setType("application/pdf");
            context.startActivity(Intent.createChooser(sharingIntent, "Status Bank"));
        }else {
            /*Download a file from url*/
            final ProgressDialog progressDialog;
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(true);
            progressDialog.show();
            final DownloadManager downloadManager;
            downloadManager=(DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri= Uri.parse(url);
            DownloadManager.Request request=new DownloadManager.Request(uri);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,filename);
            Long reference=downloadManager.enqueue(request);

            IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
            BroadcastReceiver receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

                    Cursor cursor = downloadManager.query(new DownloadManager.Query().setFilterById(downloadId));

                    if (cursor.moveToFirst()) {
                        int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                        if(status == DownloadManager.STATUS_SUCCESSFUL){
                            progressDialog.dismiss();
                            //Toast.makeText(context,"Download Complete",Toast.LENGTH_LONG).show();
                            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),filename);
                            if(file.exists()){
                                //share file
                                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Uri screenshotUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
                                sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                                sharingIntent.putExtra(Intent.EXTRA_TEXT, "");
                                sharingIntent.setType("application/pdf");
                                context.startActivity(Intent.createChooser(sharingIntent, "Status Bank"));
                            }


                        }

                    }


                }
            };
            context.registerReceiver(receiver, filter);

        }

    }




}
