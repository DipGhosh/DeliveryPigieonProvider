package com.dev.pigeonproviderapp.ActivityAll.OTPSection;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dev.pigeonproviderapp.ActivityAll.ProfileEdit;
import com.dev.pigeonproviderapp.ActivityAll.ProviderDashboard;
import com.dev.pigeonproviderapp.ActivityAll.ProviderRegistration.ProviderDetails;
import com.dev.pigeonproviderapp.ActivityAll.ProviderRegistration.Registrationactivity;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.Utility.Utility;
import com.dev.pigeonproviderapp.datamodel.UpdateProfilePIctureDataModel;
import com.dev.pigeonproviderapp.datamodel.UploadDocumentImageResponseModel;
import com.dev.pigeonproviderapp.httpRequest.OrderItemOTPVerifyModel;
import com.dev.pigeonproviderapp.httpRequest.SignatureAPIModel;
import com.dev.pigeonproviderapp.storage.Singleton;
import com.dev.pigeonproviderapp.viewmodel.DocumentsUploadViewModel;
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

    //Image Upload
    private static final int SELECT_PICTURE = 0;
    private final int REQUEST_CAMERA = 1;
    private Uri camuri;
    private Bitmap bitmap;

    private LinearLayout back;
    private Button buttonCaptureImageSubmit;
    private ImageView captureImageImageview;
    private RelativeLayout captureImageClickListener;
    private ConstraintLayout constrainmain;

    DocumentsUploadViewModel documentsUploadViewModel;
    OrderListViewModel orderListViewModel;

    //SignaturePad mSignaturePad;
    private Dialog dialog;
    private String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activeorder_details);

        dialog = UiUtils.showProgress(ItemDigitalSignature.this);

        back=findViewById(R.id.ll_back);
        //mSignaturePad = findViewById(R.id.signaturePad);
        buttonCaptureImageSubmit=findViewById(R.id.btn_signature_submit);
        captureImageImageview=findViewById(R.id.ic_capture_image);
        captureImageClickListener=findViewById(R.id.rl_capture_image_click);
        constrainmain=findViewById(R.id.constrainmain);

        documentsUploadViewModel=ViewModelProviders.of(this).get(DocumentsUploadViewModel.class);
        orderListViewModel = ViewModelProviders.of(this).get(OrderListViewModel.class);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        captureImageClickListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        buttonCaptureImageSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if (submitValidation())
               {
                   verifyOrderOtp();
               }

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE) {

            Uri selectedImageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(ItemDigitalSignature.this.getContentResolver(), selectedImageUri);
                Glide.with(this).load(bitmap)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(captureImageImageview);
            } catch (Exception e) {
                e.printStackTrace();
            }
            uploadFile(selectedImageUri);

        }else if (resultCode == RESULT_OK && requestCode == REQUEST_CAMERA)
        {
            Uri selectedImageUri = camuri;
            String[] projection = {MediaStore.MediaColumns.DATA};
            CursorLoader cursorLoader = new android.content.CursorLoader(this, selectedImageUri, projection, null, null,
                    null);
            Cursor cursor = cursorLoader.loadInBackground();
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();

            String selectedImagePath = cursor.getString(column_index);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(selectedImagePath, options);
            final int REQUIRED_SIZE = 200;
            int scale = 1;
            while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                    && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;
            options.inSampleSize = scale;
            options.inJustDecodeBounds = false;

            bitmap = BitmapFactory.decodeFile(selectedImagePath, options);

            captureImageImageview.setImageBitmap(bitmap);
            uploadFile(selectedImageUri);
        }
    }



    private void uploadFile(Uri fileUri) {
        dialog.show();
        Uri selectedImageUri=fileUri;

        String path = getRealPathFromURI(selectedImageUri);
        Log.d("Picture Path", "" + path);

        //pass it like this
        File file = Utility.saveBitmapToFile(new File(path));
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part multipartBody = MultipartBody.Part
                .createFormData("file", file.getName(), requestFile);

        documentsUploadViewModel.uploadDocumentPicture(multipartBody).observe(this,
                new Observer<UploadDocumentImageResponseModel>() {
                    @Override
                    public void onChanged(UploadDocumentImageResponseModel uploadDocumentImageResponseModel) {
                        dialog.dismiss();
                        filename = uploadDocumentImageResponseModel.getData();

                        Log.d("Aslam", "Filename: " + filename);
                        //verifyOrderOtp();

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

        SignatureAPIModel signatureAPIModel = new SignatureAPIModel();
        signatureAPIModel.setSignature(filename);
        signatureAPIModel.setType(Singleton.getInstance().getDROPPOINTTYPE());


        orderListViewModel.verifySignatureData(signatureAPIModel).observe(this, otpVerifyResponseDataModel -> {
            dialog.dismiss();

            if (otpVerifyResponseDataModel != null) {
                if (otpVerifyResponseDataModel.getStatus() == 200) {
                    final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
                    builder.setTitle(getResources().getString(R.string.app_name));
                    builder.setIcon(R.mipmap.ic_launcher);
                    builder.setMessage(R.string.signature_submit);
                    builder.setPositiveButton(R.string.aleart_ok,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                    final android.app.AlertDialog alert = builder.create();
                    alert.show();

                } else if (otpVerifyResponseDataModel.getStatus() == 400) {
                    UiUtils.showAlert(activity, getString(R.string.app_name), getString(R.string.otp_image_verification));
                }
            } else {
                UiUtils.showAlert(activity, getString(R.string.app_name), getString(R.string.otp_image_verification));
            }



        });
    }

    private void selectImage() {

        final CharSequence[] items = {"From Camera", "Cancel"};

        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(ItemDigitalSignature.this);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("From Camera")) {

                    String fileName = "new-photo-name.jpg";
                    //create parameters for Intent with filename
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, fileName);
                    values.put(MediaStore.Images.Media.DESCRIPTION, "Image capture by camera");
                    //imageUri is the current activity attribute, define and save it for later usage (also in onSaveInstanceState)
                    camuri = ItemDigitalSignature.this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    //create new Intent
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, camuri);
                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("From Cameraroll")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, SELECT_PICTURE);

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public boolean submitValidation()
    {
        if (captureImageImageview.getDrawable() == null) {
            UiUtils.showToast(this, getString(R.string.product_image_upload));
            return false;
        }else {
            return true;
        }
    }



}