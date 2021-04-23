package com.dev.pigeonproviderapp.ActivityAll.ProviderRegistration;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.dev.pigeonproviderapp.ActivityAll.AccountSettings.AddBankDetails;
import com.dev.pigeonproviderapp.ActivityAll.ProfileEdit;
import com.dev.pigeonproviderapp.ActivityAll.ProviderDashboard;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Baseclass.BaseActivity;
import com.dev.pigeonproviderapp.Utility.CommonUtils;
import com.dev.pigeonproviderapp.Utility.NetworkUtils;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.Utility.Utility;
import com.dev.pigeonproviderapp.datamodel.OrderDetailsResponseDatamodel;
import com.dev.pigeonproviderapp.datamodel.UpdateProfilePIctureDataModel;
import com.dev.pigeonproviderapp.datamodel.UploadDocumentImageResponseModel;
import com.dev.pigeonproviderapp.httpRequest.AddDocumentAPIModel;
import com.dev.pigeonproviderapp.httpRequest.ProfileUpdateAPI;
import com.dev.pigeonproviderapp.httpRequest.VerifyOtpAPIModel;
import com.dev.pigeonproviderapp.storage.SharePreference;
import com.dev.pigeonproviderapp.storage.Singleton;
import com.dev.pigeonproviderapp.viewmodel.DocumentsUploadViewModel;
import com.dev.pigeonproviderapp.viewmodel.OrderListViewModel;
import com.dev.pigeonproviderapp.viewmodel.OtpSendViewModel;
import com.dev.pigeonproviderapp.viewmodel.ProfileViewModel;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProviderDetails extends BaseActivity implements View.OnClickListener {

    private Activity activity = ProviderDetails.this;

    ProfileViewModel profileViewModel;
    DocumentsUploadViewModel documentsUploadViewModel;

    private static final int SELECT_PICTURE = 0;
    private final int REQUEST_CAMERA = 1;
    private Uri camuri;
    private Bitmap bitmap;
    MultipartBody.Part multipartBody;

    private Button btnSubmit;
    private ImageView providerImageUpload, adharcardFontsideImageUpload, adharcardBacksideImageUpload, panCardImageupload, otherDetailsImageupload;
    private EditText providerName, providerEmail;
    private String filename,position,typeofImageUpload;
    private RelativeLayout rlAdarFontUpload,rlAharFontEdit,rlAdharBackUpload,rlAdharBackEdit,rlPancardUpload,rlPancardEdit,rlOthersUpload,rlOthersEdit,profileImageUploadClick;
    private LinearLayout addBankDetailsClick;
    private LinearLayout mainLayout;

    Dialog dialog;
    String documentsName;
    int docId;
    private SharePreference sharePreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_details);

        btnSubmit = findViewById(R.id.btn_providersubmit);
        providerImageUpload = findViewById(R.id.ic_providerImage_upload);
        adharcardFontsideImageUpload = findViewById(R.id.ic_adharcard_fontsideImageUpload);
        adharcardBacksideImageUpload = findViewById(R.id.ic_adharcard_backsideImageUpload);
        panCardImageupload = findViewById(R.id.ic_pandcardImageUpload);
        otherDetailsImageupload = findViewById(R.id.ic_others_documentImageUpload);
        providerName = findViewById(R.id.et_providerFullName);
        providerEmail = findViewById(R.id.et_providerEmail);
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
        profileImageUploadClick=findViewById(R.id.rl_profile_image_upload);
        addBankDetailsClick=findViewById(R.id.ll_add_bank_details);
        mainLayout=findViewById(R.id.layout_main);

        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        documentsUploadViewModel=ViewModelProviders.of(this).get(DocumentsUploadViewModel.class);


        dialog = UiUtils.showProgress(ProviderDetails.this);
        sharePreference=new SharePreference(ProviderDetails.this);

        //Registered click listener
        btnSubmit.setOnClickListener(this);
        adharcardFontsideImageUpload.setOnClickListener(this);
        adharcardBacksideImageUpload.setOnClickListener(this);
        panCardImageupload.setOnClickListener(this);
        otherDetailsImageupload.setOnClickListener(this);
        providerImageUpload.setOnClickListener(this);
        profileImageUploadClick.setOnClickListener(this);
        addBankDetailsClick.setOnClickListener(this);
        mainLayout.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId() /*to get clicked view id**/) {
            case R.id.btn_providersubmit:

                if (NetworkUtils.isNetworkAvailable(activity)) {
                    callProfileInfoUpdate();
                }else {
                    UiUtils.showToast(this, getString(R.string.network_error));
                }

                break;
            case R.id.ic_adharcard_fontsideImageUpload:

                selectImage();
                position="1";
                typeofImageUpload="Documents";
                break;
            case R.id.ic_adharcard_backsideImageUpload:

                selectImage();
                position="2";
                typeofImageUpload="Documents";
                break;
            case R.id.ic_pandcardImageUpload:

                selectImage();
                position="3";
                typeofImageUpload="Documents";
                break;
            case R.id.ic_others_documentImageUpload:

                selectImage();
                position="4";
                typeofImageUpload="Documents";
                break;
            case R.id.rl_profile_image_upload:

                selectProfileImage();
                position="5";
                typeofImageUpload="Profile";
                break;
            case R.id.ll_add_bank_details:

                Intent intent=new Intent(ProviderDetails.this, AddBankDetails.class);
                startActivity(intent);

                break;
            case R.id.layout_main:

                UiUtils.hideSoftKeyBoard(activity,mainLayout);

                break;
            default:
                break;
        }
    }

    public void callProfileInfoUpdate() {
        if (isValid()) {
            dialog.show();
            ProfileUpdateAPI profileUpdateAPI = new ProfileUpdateAPI();
            profileUpdateAPI.setName(providerName.getText().toString());
            profileUpdateAPI.setEmail(providerEmail.getText().toString());


            profileViewModel.getProfileUploaddata(profileUpdateAPI).observe(this, profileUpdateResponseDataModel -> {

                dialog.dismiss();

                if (profileUpdateResponseDataModel != null) {

                    if (profileUpdateResponseDataModel.getStatus()==200)
                    {
                        sharePreference.SetIsloogedIn(true);

                        //sharePreference.setToken(Singleton.getInstance().getTOKEN());
                        Intent intent = new Intent(ProviderDetails.this, ProviderDashboard.class);
                        startActivity(intent);
                    }else if (profileUpdateResponseDataModel.getStatus()==400)
                    {
                        UiUtils.showAlert(activity, getString(R.string.app_name), getString(R.string.profile_invalid));
                    }

                }else {
                    UiUtils.showAlert(activity, getString(R.string.app_name), getString(R.string.profile_invalid));
                }




            });
        }

    }



    private boolean isValid() {
        if (TextUtils.isEmpty(providerName.getText().toString())) {
            UiUtils.showToast(this, getString(R.string.alert_provider_name));
            return false;
        } /*else if (TextUtils.isEmpty(providerEmail.getText().toString())) {
            UiUtils.showToast(this, getString(R.string.alert_provider_email));
            return false;
        }*//*else if (!CommonUtils.isValidEmail(providerEmail.getText().toString().trim())) {
            UiUtils.showToast(this, getString(R.string.alert_valid_email));
            return false;
        }*/else if (adharcardFontsideImageUpload.getDrawable() == null) {
            UiUtils.showToast(this, getString(R.string.aleart_upload_addressproof));
            return false;
        }else if (adharcardBacksideImageUpload.getDrawable() == null) {
            UiUtils.showToast(this, getString(R.string.aleart_upload_adharback));
            return false;
        } else if (panCardImageupload.getDrawable() == null) {
            UiUtils.showToast(this, getString(R.string.aleart_upload_pancard));
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE) {
            dialog.show();
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(ProviderDetails.this.getContentResolver(), selectedImageUri);
                if (position.equals("1"))
                {
                    adharcardFontsideImageUpload.setImageBitmap(bitmap);
                    rlAdarFontUpload.setVisibility(View.GONE);
                    rlAharFontEdit.setVisibility(View.VISIBLE);
                }else if (position.equals("2"))
                {
                    adharcardBacksideImageUpload.setImageBitmap(bitmap);
                    rlAdharBackUpload.setVisibility(View.GONE);
                    rlAdharBackEdit.setVisibility(View.VISIBLE);
                }else if (position.equals("3"))
                {
                    panCardImageupload.setImageBitmap(bitmap);
                    rlPancardUpload.setVisibility(View.GONE);
                    rlPancardEdit.setVisibility(View.VISIBLE);
                }else if (position.equals("4"))
                {
                    otherDetailsImageupload.setImageBitmap(bitmap);
                    rlOthersUpload.setVisibility(View.GONE);
                    rlOthersEdit.setVisibility(View.VISIBLE);
                }else if (position.equals("5"))
                {
                    providerImageUpload.setImageBitmap(bitmap);
                }



            } catch (Exception e) {
                e.printStackTrace();
            }

            if (NetworkUtils.isNetworkAvailable(activity)) {
                uploadFile(selectedImageUri);
            }else {
                UiUtils.showToast(this, getString(R.string.network_error));
            }




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

            if (position.equals("1"))
            {
                adharcardFontsideImageUpload.setImageBitmap(bitmap);
                rlAdarFontUpload.setVisibility(View.GONE);
                rlAharFontEdit.setVisibility(View.VISIBLE);
            }else if (position.equals("2"))
            {
                adharcardBacksideImageUpload.setImageBitmap(bitmap);
                rlAdharBackUpload.setVisibility(View.GONE);
                rlAdharBackEdit.setVisibility(View.VISIBLE);
            }else if (position.equals("3"))
            {
                panCardImageupload.setImageBitmap(bitmap);
                rlPancardUpload.setVisibility(View.GONE);
                rlPancardEdit.setVisibility(View.VISIBLE);
            }else if (position.equals("4"))
            {
                otherDetailsImageupload.setImageBitmap(bitmap);
                rlOthersUpload.setVisibility(View.GONE);
                rlOthersEdit.setVisibility(View.VISIBLE);
            }else if (position.equals("5"))
            {
                providerImageUpload.setImageBitmap(bitmap);
            }

            if (NetworkUtils.isNetworkAvailable(activity)) {
                uploadFile(selectedImageUri);
            }else {
                UiUtils.showToast(this, getString(R.string.network_error));
            }
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
        if (typeofImageUpload.equals("Documents"))
        {
            multipartBody = MultipartBody.Part
                    .createFormData("file", file.getName(), requestFile);
        }else {
            multipartBody = MultipartBody.Part
                    .createFormData("profile_picture", file.getName(), requestFile);
        }


        if (typeofImageUpload.equals("Documents"))
        {
            documentsUploadViewModel.uploadDocumentPicture(multipartBody).observe(this,
                    new Observer<UploadDocumentImageResponseModel>() {
                        @Override
                        public void onChanged(UploadDocumentImageResponseModel uploadDocumentImageResponseModel) {

                            dialog.dismiss();

                            filename = uploadDocumentImageResponseModel.getData();

                            if (position.equals("1"))
                            {
                                documentsName=Utility.ADDRESSPROOF_FONT;
                                docId=Utility.ADDRESS_FONT_ID;
                                AddDocumentImage();
                            }else if (position.equals("2"))
                            {
                                documentsName=Utility.ADDRESSPROOF_BACK;
                                docId=Utility.ADDRESS_BACK_ID;
                                AddDocumentImage();
                            }else if(position.equals("3"))
                            {
                                documentsName=Utility.ADDRESSPROOF_PAN;
                                docId=Utility.ADDRESS_PAN_ID;
                                AddDocumentImage();
                            }else if(position.equals("4"))
                            {
                                documentsName=Utility.ADDRESSPROOF_OTHERS;
                                docId=Utility.ADDRESS_OTHERS_ID;
                                AddDocumentImage();
                            }

                        }
                    });
        }else if (typeofImageUpload.equals("Profile"))
        {
            profileViewModel.uploadProfilePicture(multipartBody).observe(this,
                    new Observer<UpdateProfilePIctureDataModel>() {
                        @Override
                        public void onChanged(UpdateProfilePIctureDataModel updateProfilePIctureDataModel) {
                            dialog.dismiss();
                            String filename = updateProfilePIctureDataModel.getData().getUser()
                                    .getProfilePicture();
                            Log.d("Aslam", "Filename: " + filename);


                        }
                    });
        }

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

    private void selectImage() {

        final CharSequence[] items = {"From Camera", "Cancel"};

        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(
                ProviderDetails.this);
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
                    camuri = ProviderDetails.this.getContentResolver()
                            .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
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

    private void selectProfileImage() {

        final CharSequence[] items = {"From Camera","From Cameraroll", "Cancel"};

        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(
                ProviderDetails.this);
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
                    camuri = ProviderDetails.this.getContentResolver()
                            .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
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


    public void AddDocumentImage() {

        AddDocumentAPIModel addDocumentAPIModel = new AddDocumentAPIModel();
        addDocumentAPIModel.setDocumentName(documentsName);
        addDocumentAPIModel.setDocumentTypeId(docId);
        addDocumentAPIModel.setFileName(filename);


        documentsUploadViewModel.sendaddDocumentImage(addDocumentAPIModel).observe(this, documentsUploadViewModel -> {

        });
    }





}