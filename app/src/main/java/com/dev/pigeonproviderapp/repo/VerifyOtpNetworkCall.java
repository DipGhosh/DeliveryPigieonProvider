package com.dev.pigeonproviderapp.repo;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.dev.pigeonproviderapp.datamodel.OTPSendResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.VerifyOtpResponseDataModel;
import com.dev.pigeonproviderapp.httpRequest.OTPSendAPIModel;
import com.dev.pigeonproviderapp.httpRequest.VerifyOtpAPIModel;
import com.dev.pigeonproviderapp.network.APIClient;
import com.dev.pigeonproviderapp.network.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOtpNetworkCall {
    protected APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

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
}
