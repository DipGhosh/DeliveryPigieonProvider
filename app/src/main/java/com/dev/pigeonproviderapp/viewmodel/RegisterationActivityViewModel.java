package com.dev.pigeonproviderapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.dev.pigeonproviderapp.datamodel.OTPSendDataModel;
import com.dev.pigeonproviderapp.httpRequest.OTPSendAPI;
import com.dev.pigeonproviderapp.repo.NetworkCall;

public class RegisterationActivityViewModel extends ViewModel {

  private MutableLiveData<OTPSendDataModel> otpSendDataModelMutableLiveData;

  @Override
  protected void onCleared() {
    super.onCleared();
  }

  public LiveData<OTPSendDataModel> getRegisterData(OTPSendAPI otpSendAPI) {

    if (otpSendDataModelMutableLiveData == null) {
      otpSendDataModelMutableLiveData = new NetworkCall().callSendOTP(otpSendAPI);
    }
    return otpSendDataModelMutableLiveData;
  }

}
