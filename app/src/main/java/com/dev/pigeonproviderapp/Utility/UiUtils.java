package com.dev.pigeonproviderapp.Utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dev.pigeonproviderapp.R;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class UiUtils {

    public static void showToast(Context context, String message) {
        if (context != null)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showToastLong(Activity context, String message) {
        if (context != null && !context.isFinishing())
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showToast(Context context, int resourceId) {
        Toast.makeText(context, resourceId, Toast.LENGTH_SHORT).show();
    }


    public static Drawable getDrawable(Context context, String name) {
        Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(name, "drawable",
                context.getPackageName());
        return resources.getDrawable(resourceId);
    }

    /**
     * Hide the soft keyboard from screen for edit text only
     *
     * @param context context of current visible activity
     * @param view    clicked view
     */
    public static void hideSoftKeyBoard(final Context context, final View view) {
        try {
            final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     * Show soft keyboard on auto focus of edittext
     *
     * @param context context of current visible activity
     * @param view    focused view
     */
    public static void showKeyboard(final Context context, final View view) {
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public static int calculateNoOfColumns(Context context, float columnWidthDp) { // For example columnWidthdp=180
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (screenWidthDp / columnWidthDp + 0.5); // +0.5 for correct rounding to int.
        return noOfColumns;
    }

    private void showAlert(Context context, String title, String message) {
        //Uncomment the below code to Set the message and title from the strings.xml file
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //Setting message manually and performing action on button click
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(context.getString(R.string.label_ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });


        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle(title);
        alert.show();


    }

    public static void showAlert(Activity mActivity, String mTitle, String mMessage) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mActivity);
        alertDialogBuilder.setTitle(mTitle);
        if (mMessage == null || TextUtils.isEmpty(mMessage))
            mMessage = "Poor internet!";
        alertDialogBuilder.setMessage(mMessage).setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    public static void goNext(Activity mActivity) {
        mActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }



    public static boolean isPartiallyOrFullyVisible(final View v, View scrollview) {
        Rect scrollBounds = new Rect();
        scrollview.getHitRect(scrollBounds);
        return v.getLocalVisibleRect(scrollBounds);
    }


    public static void shareImage(final String url, final String extraText, final Context context) {
        Picasso.with(context).load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.putExtra(Intent.EXTRA_TEXT, extraText);
                intent.putExtra(Intent.EXTRA_TEXT, url);
                String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "", null);
                if (path != null) {
                    Uri screenshotUri = Uri.parse(path);
                    intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                    intent.setType("text/plain");
                    intent.setType("image/*");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Intent chooserIntent = Intent.createChooser(intent, "StudentApp");
                    chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(chooserIntent);
                }else{
                    Toast.makeText(context, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });
    }

    public static Uri getLocalBitmapUri(Bitmap bmp, Context context) {
        Uri bmpUri = null;
        try {
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "StudentApp" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }


    // progress bar handling
    public static Dialog showProgress(Activity activity) {
        Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(0));
        dialog.setContentView(R.layout.dialog_progress);
        ProgressBar progressBar = dialog.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(activity.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        dialog.setCancelable(false);
        return dialog;
    }





}
