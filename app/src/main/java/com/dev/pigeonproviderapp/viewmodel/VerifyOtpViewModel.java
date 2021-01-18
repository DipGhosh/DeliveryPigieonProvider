package com.dev.pigeonproviderapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dev.pigeonproviderapp.datamodel.VerifyOtpResponseDataModel;
import com.dev.pigeonproviderapp.httpRequest.VerifyOtpAPIModel;
import com.dev.pigeonproviderapp.repo.NetworkCall;



public class VerifyOtpViewModel extends ViewModel {

  private MutableLiveData<VerifyOtpResponseDataModel> otpResponseDataModelMutableLiveData;

  @Override
  protected void onCleared() {
    super.onCleared();
  }
  public LiveData<VerifyOtpResponseDataModel> getVerifyOtpData(VerifyOtpAPIModel verifyOtpAPIModel) {

    if (otpResponseDataModelMutableLiveData == null) {
      otpResponseDataModelMutableLiveData = new NetworkCall().callverifyOTP(verifyOtpAPIModel);
    }
    return otpResponseDataModelMutableLiveData;
  }


}
