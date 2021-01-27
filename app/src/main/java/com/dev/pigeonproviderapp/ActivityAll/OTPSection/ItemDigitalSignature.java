package com.dev.pigeonproviderapp.ActivityAll.OTPSection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.datamodel.UpdateProfilePIctureDataModel;
import com.dev.pigeonproviderapp.httpRequest.OrderItemOTPVerifyModel;
import com.dev.pigeonproviderapp.viewmodel.OrderListViewModel;
import com.dev.pigeonproviderapp.viewmodel.ProfileViewModel;
import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ItemDigitalSignature extends AppCompatActivity  {

    private Activity activity = ItemDigitalSignature.this;

    private LinearLayout back;
    private Button buttonSignatureSubmit;

    ProfileViewModel profileViewModel;
    OrderListViewModel orderListViewModel;

    SignaturePad mSignaturePad;
    private Dialog dialog;
    private String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activeorder_details);

        dialog = UiUtils.showProgress(ItemDigitalSignature.this);

        back=findViewById(R.id.ll_back);
        mSignaturePad = findViewById(R.id.signaturePad);
        buttonSignatureSubmit=findViewById(R.id.btn_signature_submit);

        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        orderListViewModel = ViewModelProviders.of(this).get(OrderListViewModel.class);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {
                //btn_submit1.setVisibility(View.VISIBLE);

            }

            @Override
            public void onClear() {

            }
        });

        buttonSignatureSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  Bitmap signatureBitmap = mSignaturePad.getSignatureBitmap();
                    if (addJpgSignatureToGallery(signatureBitmap)) {
                        dialog.show();
                        //Toast.makeText(ItemDigitalSignature.this, "successfully uploaded", Toast.LENGTH_SHORT).show();

                    } else {
                        //Toast.makeText(ItemDigitalSignature.this, "Unable to store the signature", Toast.LENGTH_SHORT).show();
                    }



            }
        });

    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created");
        }
        return file;
    }

    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();
    }

    public boolean addJpgSignatureToGallery(Bitmap signature) {
        boolean result = false;
        try {
            File photo = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.jpg", System.currentTimeMillis()));
            saveBitmapToJPG(signature, photo);
            scanMediaFile(photo);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        ItemDigitalSignature.this.sendBroadcast(mediaScanIntent);
        uploadFile(contentUri);
    }

    private void uploadFile(Uri fileUri) {
        Uri selectedImageUri=fileUri;

        String path = getRealPathFromURI(selectedImageUri);
        Log.d("Picture Path", "" + path);

        //pass it like this
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part multipartBody = MultipartBody.Part
                .createFormData("profile_picture", file.getName(), requestFile);

        profileViewModel.uploadProfilePicture(multipartBody).observe(this,
                new Observer<UpdateProfilePIctureDataModel>() {
                    @Override
                    public void onChanged(UpdateProfilePIctureDataModel updateProfilePIctureDataModel) {
                        dialog.dismiss();
                        filename = updateProfilePIctureDataModel.getData().getUser()
                                .getProfilePicture();
                        Log.d("Aslam", "Filename: " + filename);
                        verifyOrderOtp();

                    }
                });
    }

    public String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null,
                null, null, null);
        if (cursor == null) {
            // path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            try {
                int idx = cursor
                        .getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(idx);
            } catch (Exception e) {

                result = "";
            }
            cursor.close();
        }
        return result;
    }

    public void verifyOrderOtp() {

        dialog.show();

        OrderItemOTPVerifyModel orderItemOTPVerifyModel = new OrderItemOTPVerifyModel();
        orderItemOTPVerifyModel.setOtp("");
        orderItemOTPVerifyModel.setSignature(filename);


        orderListViewModel.verifyOTPData(orderItemOTPVerifyModel).observe(this, otpVerifyResponseDataModel -> {
            dialog.dismiss();

            if (otpVerifyResponseDataModel.getStatus()==200)
            {
                UiUtils.showAlert(activity,"Signature",getString(R.string.aleart_signature_upload) );
            }
        });
    }



}