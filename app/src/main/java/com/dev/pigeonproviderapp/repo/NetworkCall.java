package com.dev.pigeonproviderapp.repo;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.dev.pigeonproviderapp.datamodel.OTPSendResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.ProfileUpdateResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.UpdateProfilePIctureDataModel;
import com.dev.pigeonproviderapp.datamodel.VerifyOtpResponseDataModel;
import com.dev.pigeonproviderapp.httpRequest.OTPSendAPIModel;
import com.dev.pigeonproviderapp.httpRequest.ProfileUpdateAPI;
import com.dev.pigeonproviderapp.httpRequest.VerifyOtpAPIModel;
import com.dev.pigeonproviderapp.network.APIClient;
import com.dev.pigeonproviderapp.network.APIInterface;
import com.dev.pigeonproviderapp.storage.Singleton;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Part;

public class NetworkCall {

  protected APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

  public MutableLiveData<OTPSendResponseDataModel> callSendOTP(OTPSendAPIModel otpSendAPIModel) {

    MutableLiveData<OTPSendResponseDataModel> otpSendDataModelLiveData = new MutableLiveData<OTPSendResponseDataModel>();

    Call<OTPSendResponseDataModel> registerAPI = apiInterface.OTPSendAPICall(otpSendAPIModel);

    registerAPI.enqueue(new Callback<OTPSendResponseDataModel>() {
      @Override
      public void onResponse(Call<OTPSendResponseDataModel> call, Response<OTPSendResponseDataModel> response) {

        if (response.isSuccessful()) {
          otpSendDataModelLiveData.postValue(response.body());
          Log.d("Aslam", response.body().toString());
        } else {
          Log.d("Aslam", response.errorBody().toString());
          otpSendDataModelLiveData.postValue(response.body());
        }
      }

      @Override
      public void onFailure(Call<OTPSendResponseDataModel> call, Throwable t) {

      }
    });

    return otpSendDataModelLiveData;

  }

  public MutableLiveData<VerifyOtpResponseDataModel> callverifyOTP(VerifyOtpAPIModel verifyOtpAPIModel) {

    MutableLiveData<VerifyOtpResponseDataModel> otpverifyDataModelLiveData = new MutableLiveData<VerifyOtpResponseDataModel>();

    Call<VerifyOtpResponseDataModel> otpverifyAPI = apiInterface.VerifyOtpAPICall(verifyOtpAPIModel);

    otpverifyAPI.enqueue(new Callback<VerifyOtpResponseDataModel>() {
      @Override
      public void onResponse(Call<VerifyOtpResponseDataModel> call, Response<VerifyOtpResponseDataModel> response) {

        if (response.isSuccessful()) {
          otpverifyDataModelLiveData.postValue(response.body());
          Log.d("Mangal", response.body().toString());
        } else {
          Log.d("Mangal", response.errorBody().toString());
          otpverifyDataModelLiveData.postValue(response.body());
        }
      }

      @Override
      public void onFailure(Call<VerifyOtpResponseDataModel> call, Throwable t) {

      }
    });

    return otpverifyDataModelLiveData;

  }

  public MutableLiveData<ProfileUpdateResponseDataModel> callprofileInfo(ProfileUpdateAPI profileUpdateAPI) {

    MutableLiveData<ProfileUpdateResponseDataModel> profileInfoUpdateDataModelLiveData = new MutableLiveData<ProfileUpdateResponseDataModel>();

    Call<ProfileUpdateResponseDataModel> profileInfoUpdateAPI = apiInterface.profileInfouploadAPICall(Singleton.getInstance().getTOKEN(),profileUpdateAPI);

    profileInfoUpdateAPI.enqueue(new Callback<ProfileUpdateResponseDataModel>() {
      @Override
      public void onResponse(Call<ProfileUpdateResponseDataModel> call, Response<ProfileUpdateResponseDataModel> response) {

        if (response.isSuccessful()) {
          profileInfoUpdateDataModelLiveData.postValue(response.body());
          Log.d("Mangal", response.body().toString());
        } else {
          Log.d("Mangal", response.errorBody().toString());
          profileInfoUpdateDataModelLiveData.postValue(response.body());
        }
      }

      @Override
      public void onFailure(Call<ProfileUpdateResponseDataModel> call, Throwable t) {

      }
    });

    return profileInfoUpdateDataModelLiveData;

  }

  public MutableLiveData<ProfileUpdateResponseDataModel> callupdateprofileInfo(ProfileUpdateAPI profileUpdateAPI) {

    MutableLiveData<ProfileUpdateResponseDataModel> profileInfoUpdateDataModelLiveData = new MutableLiveData<ProfileUpdateResponseDataModel>();

    Call<ProfileUpdateResponseDataModel> profileInfoUpdateAPI = apiInterface.updateProfileInfoAPICall(Singleton.getInstance().getTOKEN(),profileUpdateAPI);

    profileInfoUpdateAPI.enqueue(new Callback<ProfileUpdateResponseDataModel>() {
      @Override
      public void onResponse(Call<ProfileUpdateResponseDataModel> call, Response<ProfileUpdateResponseDataModel> response) {

        if (response.isSuccessful()) {
          profileInfoUpdateDataModelLiveData.postValue(response.body());
          Log.d("Mangal", response.body().toString());
        } else {
          Log.d("Mangal", response.errorBody().toString());
          profileInfoUpdateDataModelLiveData.postValue(response.body());
        }
      }

      @Override
      public void onFailure(Call<ProfileUpdateResponseDataModel> call, Throwable t) {

      }
    });

    return profileInfoUpdateDataModelLiveData;

  }

  public MutableLiveData<UpdateProfilePIctureDataModel> uploadProfilePicture(
          @Part MultipartBody.Part profile_picture) {

    MutableLiveData<UpdateProfilePIctureDataModel> updateProfilePIctureDataModelMutableLiveData = new MutableLiveData<UpdateProfilePIctureDataModel>();

    Call<UpdateProfilePIctureDataModel> updateProfilePicture = apiInterface
            .updateProfilePicture(Singleton.getInstance().getTOKEN(), profile_picture);

    updateProfilePicture.enqueue(new Callback<UpdateProfilePIctureDataModel>() {
      @Override
      public void onResponse(Call<UpdateProfilePIctureDataModel> call,
                             Response<UpdateProfilePIctureDataModel> response) {

        if (response.isSuccessful()) {
          Log.d("Aslam", "Successfull" + response.message());
          updateProfilePIctureDataModelMutableLiveData.postValue(response.body());
        } else {
          Log.d("Aslam", "Fail " + response.message());
          updateProfilePIctureDataModelMutableLiveData.postValue(response.body());
        }
      }

      @Override
      public void onFailure(Call<UpdateProfilePIctureDataModel> call, Throwable t) {

      }
    });

    return updateProfilePIctureDataModelMutableLiveData;
  }


}
