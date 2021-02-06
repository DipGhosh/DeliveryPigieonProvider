package com.dev.pigeonproviderapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dev.pigeonproviderapp.datamodel.ListOrderResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.NotificationDatamodel;
import com.dev.pigeonproviderapp.repo.NetworkCall;

public class NotificationViewModel extends ViewModel {

    private MutableLiveData<NotificationDatamodel> notificationDataModelMutableLiveData;

    public LiveData<NotificationDatamodel> getAllNotificationCall() {

        notificationDataModelMutableLiveData = new NetworkCall().getNotificationCall();
        return notificationDataModelMutableLiveData;
    }
}
