package com.dev.pigeonproviderapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.dev.pigeonproviderapp.datamodel.OTPSendResponseDataModel;
import com.dev.pigeonproviderapp.httpRequest.OTPSendAPIModel;
import com.dev.pigeonproviderapp.repo.OtpSendNetworkCall;

public class OtpSendViewModel extends ViewModel {

  private MutableLiveData<OTPSendResponseDataModel> otpSendDataModelMutableLiveData;

  @Override
  protected void onCleared() {
    super.onCleared();
  }

  public LiveData<OTPSendResponseDataModel> getRegisterData(OTPSendAPIModel otpSendAPIModel) {

    if (otpSendDataModelMutableLiveData == null) {
      otpSendDataModelMutableLiveData = new OtpSendNetworkCall().callSendOTP(otpSendAPIModel);
    }
    return otpSendDataModelMutableLiveData;
  }

}
