package com.dev.pigeonproviderapp.ActivityAll.AccountSettings;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.dev.pigeonproviderapp.ActivityAll.ProviderRegistration.ProviderDetails;
import com.dev.pigeonproviderapp.Baseclass.BaseActivity;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.NetworkUtils;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.Utility.Utility;
import com.dev.pigeonproviderapp.datamodel.BankDetailsGetModelResponse;
import com.dev.pigeonproviderapp.datamodel.GetUserDocumentResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.UpdateProfilePIctureDataModel;
import com.dev.pigeonproviderapp.datamodel.UploadDocumentImageResponseModel;
import com.dev.pigeonproviderapp.httpRequest.AddDocumentAPIModel;
import com.dev.pigeonproviderapp.storage.SharePreference;
import com.dev.pigeonproviderapp.viewmodel.DocumentsUploadViewModel;
import com.dev.pigeonproviderapp.viewmodel.ProfileViewModel;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Callback;
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

public class DocumentListing extends BaseActivity implements View.OnClickListener {

    private Activity activity = DocumentListing.this;

    private ImageView back;
    private ImageView adharcardFontsideImageUpload, adharcardBacksideImageUpload, panCardImageupload, otherDetailsImageupload;
    private ProgressBar aadharFontProgress,aadharBackProgress,panProgress,othersProgress;
    private RelativeLayout rlAdarFontUpload,rlAharFontEdit,rlAdharBackUpload,rlAdharBackEdit,rlPancardUpload,rlPancardEdit,rlOthersUpload,rlOthersEdit;

    ProfileViewModel profileViewModel;
    DocumentsUploadViewModel documentsUploadViewModel;
    private Dialog dialog;

    //Documents update
    private static final int SELECT_PICTURE = 0;
    private final int REQUEST_CAMERA = 1;
    private Uri camuri;
    private Bitmap bitmap;
    MultipartBody.Part multipartBody;
    String documentsName;
    int docId;
    private String filename,position,typeofImageUpload;
    private SharePreference sharePreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_listing);
        sharePreference=new SharePreference(activity);

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
        documentsUploadViewModel=ViewModelProviders.of(this).get(DocumentsUploadViewModel.class);

        back.setOnClickListener(this);
        adharcardFontsideImageUpload.setOnClickListener(this);
        adharcardBacksideImageUpload.setOnClickListener(this);
        panCardImageupload.setOnClickListener(this);
        otherDetailsImageupload.setOnClickListener(this);

        if (NetworkUtils.isNetworkAvailable(activity)) {

            callDocumentListingCall();

        }else {
            UiUtils.showToast(activity, getString(R.string.network_error));
        }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId() /*to get clicked view id**/) {
            case R.id.img_back:
                finish();
                break;
            case R.id.ic_adharcard_fontsideImageUpload:
                if (sharePreference.GetVerified()==false)
                {
                    selectImage();
                    position="1";
                }else {

                    UiUtils.showAlert(activity, getString(R.string.app_name), getString(R.string.profile_already_verified));
                }

                break;
            case R.id.ic_adharcard_backsideImageUpload:
                if (sharePreference.GetVerified()==false)
                {
                    selectImage();
                    position="2";
                }else {
                    UiUtils.showAlert(activity, getString(R.string.app_name), getString(R.string.profile_already_verified));
                }
                break;
            case R.id.ic_pandcardImageUpload:

                if (sharePreference.GetVerified()==false)
                {
                    selectImage();
                    position="3";
                }else {
                    UiUtils.showAlert(activity, getString(R.string.app_name), getString(R.string.profile_already_verified));
                }
                break;
            case R.id.ic_others_documentImageUpload:

                if (sharePreference.GetVerified()==false)
                {
                    selectImage();
                    position="4";
                }else {
                    UiUtils.showAlert(activity, getString(R.string.app_name), getString(R.string.profile_already_verified));
                }
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
                               aadharFontProgress.setVisibility(View.VISIBLE);
                               loadImageFile(data.getFileNameFront(),adharcardFontsideImageUpload,aadharFontProgress,rlAdarFontUpload,rlAharFontEdit);
                           } else if (data.getName().equals("Address Proof Back")) {
                               aadharBackProgress.setVisibility(View.VISIBLE);
                               loadImageFile(data.getFileNameFront(),adharcardBacksideImageUpload,aadharBackProgress,rlAdharBackUpload,rlAdharBackEdit);
                           }
                           else if (data.getName().equals("PAN")) {
                               panProgress.setVisibility(View.VISIBLE);
                               loadImageFile(data.getFileNameFront(),panCardImageupload,panProgress,rlPancardUpload,rlPancardEdit);
                           }
                           else if (data.getName().equals("Others")) {
                               othersProgress.setVisibility(View.VISIBLE);
                               loadImageFile(data.getFileNameFront(),otherDetailsImageupload,othersProgress,rlOthersUpload,rlOthersEdit);
                           }
                    }
                }




            }
        });


    }

    private void loadImageFile(String url,ImageView imageView,ProgressBar progressBar,RelativeLayout plusicon,RelativeLayout editicon)
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
                        plusicon.setVisibility(View.GONE);
                        editicon.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {
                        progressBar.setVisibility(View.GONE);
                        plusicon.setVisibility(View.VISIBLE);
                        editicon.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE) {
            dialog.show();
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(DocumentListing.this.getContentResolver(), selectedImageUri);
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
                }



            } catch (Exception e) {
                e.printStackTrace();
            }

            if (NetworkUtils.isNetworkAvailable(activity)) {

                uploadFile(selectedImageUri);

            }else {
                UiUtils.showToast(activity, getString(R.string.network_error));
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
            }

            if (NetworkUtils.isNetworkAvailable(activity)) {

                uploadFile(selectedImageUri);

            }else {
                UiUtils.showToast(activity, getString(R.string.network_error));
            }
        }
    }

    private void uploadFile(Uri fileUri) {

        dialog.show();

        Uri selectedImageUri=fileUri;

        String path = getRealPathFromURI(selectedImageUri);
        Log.d("Picture Path", "" + path);

        //pass it like this
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            multipartBody = MultipartBody.Part
                    .createFormData("file", file.getName(), requestFile);



            documentsUploadViewModel.uploadDocumentPicture(multipartBody).observe(this,
                    new Observer<UploadDocumentImageResponseModel>() {
                        @Override
                        public void onChanged(UploadDocumentImageResponseModel uploadDocumentImageResponseModel) {

                            dialog.dismiss();

                            filename = uploadDocumentImageResponseModel.getData();

                            if (position.equals("1"))
                            {
                                documentsName= Utility.ADDRESSPROOF_FONT;
                                docId=Utility.ADDRESS_FONT_ID;

                                if (NetworkUtils.isNetworkAvailable(activity)) {
                                    AddDocumentImage();
                                }else {
                                    UiUtils.showToast(activity, getString(R.string.network_error));
                                }

                            }else if (position.equals("2"))
                            {
                                documentsName=Utility.ADDRESSPROOF_BACK;
                                docId=Utility.ADDRESS_BACK_ID;
                                if (NetworkUtils.isNetworkAvailable(activity)) {
                                    AddDocumentImage();
                                }else {
                                    UiUtils.showToast(activity, getString(R.string.network_error));
                                }
                            }else if(position.equals("3"))
                            {
                                documentsName=Utility.ADDRESSPROOF_PAN;
                                docId=Utility.ADDRESS_PAN_ID;
                                if (NetworkUtils.isNetworkAvailable(activity)) {
                                    AddDocumentImage();
                                }else {
                                    UiUtils.showToast(activity, getString(R.string.network_error));
                                }
                            }else if(position.equals("4"))
                            {
                                documentsName=Utility.ADDRESSPROOF_OTHERS;
                                docId=Utility.ADDRESS_OTHERS_ID;
                                if (NetworkUtils.isNetworkAvailable(activity)) {
                                    AddDocumentImage();
                                }else {
                                    UiUtils.showToast(activity, getString(R.string.network_error));
                                }
                            }

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

    private void selectImage() {
        final CharSequence[] items = {"From Camera", "Cancel"};

        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(DocumentListing.this);
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
                    camuri = DocumentListing.this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
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

            if (documentsUploadViewModel != null) {
               //image uploaded successfully
            }else {
                UiUtils.showAlert(activity, getString(R.string.app_name), getString(R.string.wrong_data_aleart));
            }

        });
    }


}