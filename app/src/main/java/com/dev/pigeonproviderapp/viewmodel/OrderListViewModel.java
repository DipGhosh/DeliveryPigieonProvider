package com.dev.pigeonproviderapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dev.pigeonproviderapp.datamodel.ListOrderResponseDataModel;
import com.dev.pigeonproviderapp.repo.NetworkCall;

public class OrderListViewModel extends ViewModel {

    private MutableLiveData<ListOrderResponseDataModel> rderListDataModelMutableLiveData;

    public LiveData<ListOrderResponseDataModel> getOrderListData() {

        if (rderListDataModelMutableLiveData == null) {
            rderListDataModelMutableLiveData = new NetworkCall().getOrderListData();
        }
        return rderListDataModelMutableLiveData;
    }
}
