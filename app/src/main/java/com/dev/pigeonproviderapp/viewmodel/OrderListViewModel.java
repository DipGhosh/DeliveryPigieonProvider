package com.dev.pigeonproviderapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dev.pigeonproviderapp.datamodel.ListOrderResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.OrderDetailsResponseDatamodel;
import com.dev.pigeonproviderapp.repo.NetworkCall;

public class OrderListViewModel extends ViewModel {

    private MutableLiveData<ListOrderResponseDataModel> rderListDataModelMutableLiveData;
    private MutableLiveData<OrderDetailsResponseDatamodel> orderdetailsDataModelMutableLiveData;

    public LiveData<ListOrderResponseDataModel> getOrderListData() {

        if (rderListDataModelMutableLiveData == null) {
            rderListDataModelMutableLiveData = new NetworkCall().getOrderListData();
        }
        return rderListDataModelMutableLiveData;
    }



    public LiveData<OrderDetailsResponseDatamodel> getOrderDetailsData() {

        if (orderdetailsDataModelMutableLiveData == null) {
            orderdetailsDataModelMutableLiveData = new NetworkCall().getOrderDetails();
        }
        return orderdetailsDataModelMutableLiveData;
    }
}
