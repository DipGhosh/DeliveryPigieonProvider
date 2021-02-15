package com.dev.pigeonproviderapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dev.pigeonproviderapp.datamodel.LocationDatamodel;
import com.dev.pigeonproviderapp.datamodel.VerifyOtpResponseDataModel;
import com.dev.pigeonproviderapp.httpRequest.LocationRequestSendModel;
import com.dev.pigeonproviderapp.httpRequest.VerifyOtpAPIModel;
import com.dev.pigeonproviderapp.repo.NetworkCall;

public class LocationSendViewModel  extends ViewModel {

    private MutableLiveData<LocationDatamodel> locationSendDataModelMutableLiveData;

    @Override
    protected void onCleared() {
        super.onCleared();
    }
    public LiveData<LocationDatamodel> locationSendAPI(LocationRequestSendModel locationRequestSendModel) {


        locationSendDataModelMutableLiveData = new NetworkCall().locationSendAPI(locationRequestSendModel);
        return locationSendDataModelMutableLiveData;
    }
}
