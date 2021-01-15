package com.dev.pigeonproviderapp.repo;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.dev.pigeonproviderapp.datamodel.OTPSendResponseDataModel;
import com.dev.pigeonproviderapp.httpRequest.OTPSendAPIModel;
import com.dev.pigeonproviderapp.network.APIClient;
import com.dev.pigeonproviderapp.network.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpSendNetworkCall {

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


}
