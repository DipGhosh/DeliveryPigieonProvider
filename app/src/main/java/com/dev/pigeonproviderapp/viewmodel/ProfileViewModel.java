package com.dev.pigeonproviderapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dev.pigeonproviderapp.datamodel.OTPSendResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.ProfileUpdateResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.UpdateProfilePIctureDataModel;
import com.dev.pigeonproviderapp.httpRequest.OTPSendAPIModel;
import com.dev.pigeonproviderapp.httpRequest.ProfileUpdateAPI;
import com.dev.pigeonproviderapp.repo.NetworkCall;

import okhttp3.MultipartBody;
import retrofit2.http.Part;

public class ProfileViewModel extends ViewModel {
    MutableLiveData<ProfileUpdateResponseDataModel> profileUploadDataModelMutableLiveData;
    MutableLiveData<ProfileUpdateResponseDataModel> profileUpdateDataModelMutableLiveData;
    MutableLiveData<UpdateProfilePIctureDataModel> updateProfilePIctureDataModelMutableLiveData;

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public LiveData<ProfileUpdateResponseDataModel> getProfileUploaddata(ProfileUpdateAPI profileUpdateAPI) {

        if (profileUploadDataModelMutableLiveData == null) {
            profileUploadDataModelMutableLiveData = new NetworkCall().callprofileInfo(profileUpdateAPI);
        }
        return profileUploadDataModelMutableLiveData;
    }

    public LiveData<ProfileUpdateResponseDataModel> getProfileUpdatedata(ProfileUpdateAPI profileUpdateAPI) {

        if (profileUpdateDataModelMutableLiveData == null) {
            profileUpdateDataModelMutableLiveData = new NetworkCall().callupdateprofileInfo(profileUpdateAPI);
        }
        return profileUpdateDataModelMutableLiveData;
    }

    public LiveData<UpdateProfilePIctureDataModel> uploadProfilePicture(
            @Part MultipartBody.Part part) {

        if (updateProfilePIctureDataModelMutableLiveData == null) {
            updateProfilePIctureDataModelMutableLiveData = new NetworkCall().uploadProfilePicture(part);
        }
        return updateProfilePIctureDataModelMutableLiveData;
    }
}
