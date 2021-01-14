package com.dev.pigeonproviderapp.repo;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.dev.pigeonproviderapp.datamodel.OTPSendDataModel;
import com.dev.pigeonproviderapp.httpRequest.OTPSendAPI;
import com.dev.pigeonproviderapp.network.APIClient;
import com.dev.pigeonproviderapp.network.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkCall {

  protected APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

  public MutableLiveData<OTPSendDataModel> callSendOTP(OTPSendAPI otpSendAPI) {

    MutableLiveData<OTPSendDataModel> otpSendDataModelLiveData = new MutableLiveData<OTPSendDataModel>();

    Call<OTPSendDataModel> registerAPI = apiInterface.OTPSendAPICall(otpSendAPI);

    registerAPI.enqueue(new Callback<OTPSendDataModel>() {
      @Override
      public void onResponse(Call<OTPSendDataModel> call, Response<OTPSendDataModel> response) {

        if (response.isSuccessful()) {
          otpSendDataModelLiveData.postValue(response.body());
          Log.d("Aslam", response.body().toString());
        } else {
          Log.d("Aslam", response.errorBody().toString());
          otpSendDataModelLiveData.postValue(response.body());
        }
      }

      @Override
      public void onFailure(Call<OTPSendDataModel> call, Throwable t) {

      }
    });

    return otpSendDataModelLiveData;

  }


}
