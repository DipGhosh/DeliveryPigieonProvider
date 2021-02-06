package com.dev.pigeonproviderapp.ActivityAll;

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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.dev.pigeonproviderapp.ActivityAll.OTPSection.ItemDigitalSignature;
import com.dev.pigeonproviderapp.ActivityAll.ProviderRegistration.ProviderDetails;
import com.dev.pigeonproviderapp.Baseclass.BaseActivity;
import com.dev.pigeonproviderapp.BuildConfig;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.CommonUtils;
import com.dev.pigeonproviderapp.Utility.GlideApp;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.Utility.Utility;
import com.dev.pigeonproviderapp.datamodel.OTPSendResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.ProfileUpdateResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.UpdateProfilePIctureDataModel;
import com.dev.pigeonproviderapp.datamodel.UploadDocumentImageResponseModel;
import com.dev.pigeonproviderapp.httpRequest.ProfileUpdateAPI;
import com.dev.pigeonproviderapp.storage.Singleton;
import com.dev.pigeonproviderapp.viewmodel.ProfileViewModel;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Credentials;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;

public class ProfileEdit extends BaseActivity implements View.OnClickListener {

    private Activity activity = ProfileEdit.this;
    //Image Upload
    private static final int SELECT_PICTURE = 0;
    private final int REQUEST_CAMERA = 1;
    private Uri camuri;
    private Bitmap bitmap;

    ProfileViewModel profileViewModel;
    private ImageView back, profileEditImageUpload, selectEditImage;
    private TextView profileEditSubmit;
    private EditText userNameProfileEdit, emailProfileEdit;

    private ProgressBar profileEditImageProgress;
    private String profileImageUrl;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        dialog = UiUtils.showProgress(ProfileEdit.this);

        back = findViewById(R.id.img_back);
        userNameProfileEdit = findViewById(R.id.et_userName_profileEdit);
        emailProfileEdit = findViewById(R.id.et_email_profileEdit);
        profileEditSubmit = findViewById(R.id.tv_save_profile_edit);
        profileEditImageUpload = findViewById(R.id.ic_profileedit_imageupload);
        selectEditImage = findViewById(R.id.ic_edit_pic);
        profileEditImageProgress=findViewById(R.id.profile_edit_image_progress);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            userNameProfileEdit.setText(bundle.getString("NAME"));
            emailProfileEdit.setText(bundle.getString("EMAIL"));
            profileImageUrl=bundle.getString("URL");
        }


        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        //Registered click listener
        back.setOnClickListener(this);
        selectEditImage.setOnClickListener(this);
        profileEditSubmit.setOnClickListener(this);

        //Show Image
        if (profileImageUrl != null)
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
            profileEditImageProgress.setVisibility(View.VISIBLE);
            picasso.with(activity)
                    .load(profileImageUrl)
                    .into(profileEditImageUpload, new Callback() {

                        @Override
                        public void onSuccess() {
                            profileEditImageProgress.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            profileEditImageProgress.setVisibility(View.GONE);
                            Picasso.with(activity).load(R.drawable.dummy_image).into(profileEditImageUpload);
                        }
                    });
        }else {
            profileEditImageProgress.setVisibility(View.GONE);
            Picasso.with(activity).load(R.drawable.dummy_image).into(profileEditImageUpload);
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId() /*to get clicked view id**/) {
            case R.id.img_back:
                Intent intent = getIntent();
                intent.putExtra(Utility.EDIT_NAME, userNameProfileEdit.getText().toString());
                intent.putExtra(Utility.EDIT_EMAIL,emailProfileEdit.getText().toString());
                intent.putExtra(Utility.EDIT_PIC, profileImageUrl);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.tv_save_profile_edit:
                callProfileInfoUpdate();
                break;
            case R.id.ic_edit_pic:
                selectImage();
                break;
            default:
                break;
        }
    }

    public void callProfileInfoUpdate() {
        if (isValid()) {

            dialog.show();

            ProfileUpdateAPI profileUpdateAPI = new ProfileUpdateAPI();
            profileUpdateAPI.setName(userNameProfileEdit.getText().toString());
            profileUpdateAPI.setEmail(emailProfileEdit.getText().toString());


            profileViewModel.getProfileUpdatedata(profileUpdateAPI).observe(this, new Observer<ProfileUpdateResponseDataModel>() {
                @Override
                public void onChanged(ProfileUpdateResponseDataModel profileUpdateResponseDataModel) {
                    dialog.dismiss();

                    UiUtils.showAlert(activity,"Profile Edit",getString(R.string.profile_edit_aleart));



                }
            });
        }

    }

    private boolean isValid() {
        if (TextUtils.isEmpty(userNameProfileEdit.getText().toString())) {
            UiUtils.showToast(this, getString(R.string.aleart_name));
            return false;
        } else if (TextUtils.isEmpty(emailProfileEdit.getText().toString())) {
            UiUtils.showToast(this, getString(R.string.alert_provider_email));
            return false;
        } else if (!CommonUtils.isValidEmail(emailProfileEdit.getText().toString().trim())) {
            UiUtils.showToast(this, getString(R.string.alert_valid_email));
            return false;
        }  else {
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE) {

            Uri selectedImageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(ProfileEdit.this.getContentResolver(), selectedImageUri);
                Glide.with(this).load(bitmap)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(profileEditImageUpload);
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

            profileEditImageUpload.setImageBitmap(bitmap);
            uploadFile(selectedImageUri);
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
        MultipartBody.Part multipartBody = MultipartBody.Part
                .createFormData("profile_picture", file.getName(), requestFile);

        profileViewModel.uploadProfilePicture(multipartBody).observe(this,
                new Observer<UpdateProfilePIctureDataModel>() {
                    @Override
                    public void onChanged(UpdateProfilePIctureDataModel updateProfilePIctureDataModel) {

                        dialog.dismiss();

                      String  filename = updateProfilePIctureDataModel.getData().getUser()
                                .getProfilePicture();
                        profileImageUrl=filename;

                        Log.d("Aslam", "Filename: " + filename);

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

        final CharSequence[] items = {"From Camera", "From Cameraroll", "Cancel"};

        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(ProfileEdit.this);
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
                    camuri = ProfileEdit.this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
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
}