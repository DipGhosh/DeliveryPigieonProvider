package com.dev.pigeonproviderapp.ActivityAll.ProviderRegistration;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.dev.pigeonproviderapp.ActivityAll.ProfileEdit;
import com.dev.pigeonproviderapp.ActivityAll.ProviderDashboard;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Baseclass.BaseActivity;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.Utility.Utility;
import com.dev.pigeonproviderapp.datamodel.UpdateProfilePIctureDataModel;
import com.dev.pigeonproviderapp.datamodel.UploadDocumentImageResponseModel;
import com.dev.pigeonproviderapp.httpRequest.AddDocumentAPIModel;
import com.dev.pigeonproviderapp.httpRequest.ProfileUpdateAPI;
import com.dev.pigeonproviderapp.httpRequest.VerifyOtpAPIModel;
import com.dev.pigeonproviderapp.viewmodel.DocumentsUploadViewModel;
import com.dev.pigeonproviderapp.viewmodel.OtpSendViewModel;
import com.dev.pigeonproviderapp.viewmodel.ProfileViewModel;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

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

    ProfileViewModel profileViewModel;
    DocumentsUploadViewModel documentsUploadViewModel;
    private static final int SELECT_PICTURE = 0;
    MultipartBody.Part multipartBody;

    private Button btnSubmit;
    private ImageView providerImageUpload, adharcardFontsideImageUpload, adharcardBacksideImageUpload, panCardImageupload, otherDetailsImageupload;
    private EditText providerName, providerEmail;
    private String filename,position,typeofImageUpload;
    private RelativeLayout rlAdarFontUpload,rlAharFontEdit,rlAdharBackUpload,rlAdharBackEdit,rlPancardUpload,rlPancardEdit,rlOthersUpload,rlOthersEdit;



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

        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        documentsUploadViewModel=ViewModelProviders.of(this).get(DocumentsUploadViewModel.class);

        //Registered click listener
        btnSubmit.setOnClickListener(this);
        adharcardFontsideImageUpload.setOnClickListener(this);
        adharcardBacksideImageUpload.setOnClickListener(this);
        panCardImageupload.setOnClickListener(this);
        otherDetailsImageupload.setOnClickListener(this);
        providerImageUpload.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId() /*to get clicked view id**/) {
            case R.id.btn_providersubmit:
                callProfileInfoUpdate();
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
            case R.id.ic_providerImage_upload:
                selectImage();
                position="5";
                typeofImageUpload="Profile";
                break;
            default:
                break;
        }
    }

    public void callProfileInfoUpdate() {
        if (isValid()) {
            ProfileUpdateAPI profileUpdateAPI = new ProfileUpdateAPI();
            profileUpdateAPI.setName(providerName.getText().toString());
            profileUpdateAPI.setEmail(providerEmail.getText().toString());


            profileViewModel.getProfileUploaddata(profileUpdateAPI).observe(this, profileViewModel -> {
                Intent intent = new Intent(ProviderDetails.this, ProviderDashboard.class);
                startActivity(intent);

            });
        }

    }



    private boolean isValid() {
        if (TextUtils.isEmpty(providerName.getText().toString())) {
            UiUtils.showToast(this, getString(R.string.alert_provider_name));
            return false;
        } /*else if (!providerPhoneNumber.getText().toString().matches(MobilePattern)) {
      UiUtils.showToast(this, getString(R.string.alert_create_phone));
      return false;
    }*/ else {
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE) {

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
            String path = getRealPathFromURI(selectedImageUri);
            Log.d("Picture Path", "" + path);

            //pass it like this
            File file = new File(path);
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

                                filename = uploadDocumentImageResponseModel.getData();

                                if (position.equals("1"))
                                {
                                    Log.d("Addharfont", "Filename: " + filename);
                                    AddDocumentImage();
                                }else if (position.equals("2"))
                                {
                                    Log.d("AddharBack", "Filename: " + filename);
                                    AddDocumentImage();
                                }else if(position.equals("3"))
                                {
                                    Log.d("Pancard", "Filename: " + filename);
                                    AddDocumentImage();
                                }else if(position.equals("4"))
                                {
                                    Log.d("Others", "Filename: " + filename);
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

                                String filename = updateProfilePIctureDataModel.getData().getUser()
                                        .getProfilePicture();
                                Log.d("Aslam", "Filename: " + filename);


                            }
                        });
            }




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
        final CharSequence[] options = {"From Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("From Gallery")) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, SELECT_PICTURE);

            } else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void AddDocumentImage() {

        AddDocumentAPIModel addDocumentAPIModel = new AddDocumentAPIModel();
        addDocumentAPIModel.setDocumentName("My adadr");
        addDocumentAPIModel.setDocumentTypeId("1");
        addDocumentAPIModel.setFileName(filename);


        documentsUploadViewModel.sendaddDocumentImage(addDocumentAPIModel).observe(this, documentsUploadViewModel -> {

        });
    }



}