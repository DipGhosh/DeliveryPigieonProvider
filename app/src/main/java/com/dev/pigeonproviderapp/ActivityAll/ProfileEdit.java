package com.dev.pigeonproviderapp.ActivityAll;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.dev.pigeonproviderapp.ActivityAll.ProviderRegistration.ProviderDetails;
import com.dev.pigeonproviderapp.Baseclass.BaseActivity;
import com.dev.pigeonproviderapp.BuildConfig;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.datamodel.UpdateProfilePIctureDataModel;
import com.dev.pigeonproviderapp.httpRequest.ProfileUpdateAPI;
import com.dev.pigeonproviderapp.viewmodel.ProfileViewModel;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileEdit extends BaseActivity implements View.OnClickListener {

    private static final int SELECT_PICTURE = 0;
    ProfileViewModel profileViewModel;
    private ImageView back, profileEdit, profileEditImageUpload, selectEditImage;
    private EditText userNameProfileEdit, emailProfileEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        back = findViewById(R.id.img_back);
        userNameProfileEdit = findViewById(R.id.et_userName_profileEdit);
        emailProfileEdit = findViewById(R.id.et_email_profileEdit);
        profileEdit = findViewById(R.id.ic_profile_edit);
        profileEditImageUpload = findViewById(R.id.ic_profileedit_imageupload);
        selectEditImage = findViewById(R.id.ic_edit_pic);


        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        //Registered click listener
        back.setOnClickListener(this);
        profileEdit.setOnClickListener(this);
        selectEditImage.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId() /*to get clicked view id**/) {
            case R.id.img_back:
                finish();
                break;
            case R.id.ic_profile_edit:
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
            ProfileUpdateAPI profileUpdateAPI = new ProfileUpdateAPI();
            profileUpdateAPI.setName(userNameProfileEdit.getText().toString());
            profileUpdateAPI.setEmail(emailProfileEdit.getText().toString());


            profileViewModel.getProfileUpdatedata(profileUpdateAPI).observe(this, profileViewModel -> {


            });
        }

    }

    private boolean isValid() {
        if (TextUtils.isEmpty(userNameProfileEdit.getText().toString())) {
            UiUtils.showToast(this, getString(R.string.alert_create_phone));
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

                            String filename = updateProfilePIctureDataModel.getData().getUser()
                                    .getProfilePicture();
                            Log.d("Aslam", "Filename: " + filename);

                            Picasso.with(ProfileEdit.this).load(BuildConfig.SERVER_URL + "/" + filename)
                                    .resize(200, 200)
                                    .into(profileEditImageUpload);

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
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, SELECT_PICTURE);
    }
}