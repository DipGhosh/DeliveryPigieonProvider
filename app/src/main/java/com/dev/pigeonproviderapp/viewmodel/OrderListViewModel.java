package com.dev.pigeonproviderapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dev.pigeonproviderapp.datamodel.AcceptOrderResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.AcceptPaymentResponseModel;
import com.dev.pigeonproviderapp.datamodel.CompleteOrderPointResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.ListOrderResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.OrderDetailsResponseDatamodel;
import com.dev.pigeonproviderapp.datamodel.OtpVerifyResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.StartOrderResponseDataModel;
import com.dev.pigeonproviderapp.httpRequest.AcceptPaymentAPIModel;
import com.dev.pigeonproviderapp.httpRequest.CompleteOrderAPIModel;
import com.dev.pigeonproviderapp.httpRequest.OrderItemOTPVerifyModel;
import com.dev.pigeonproviderapp.httpRequest.OrderRatingAPIModel;
import com.dev.pigeonproviderapp.httpRequest.SignatureAPIModel;
import com.dev.pigeonproviderapp.repo.NetworkCall;

public class OrderListViewModel extends ViewModel {

    private MutableLiveData<ListOrderResponseDataModel> rderListDataModelMutableLiveData;
    private MutableLiveData<OrderDetailsResponseDatamodel> orderdetailsDataModelMutableLiveData;
    private MutableLiveData<AcceptOrderResponseDataModel> acceptOrderDataModelMutableLiveData;
    private MutableLiveData<StartOrderResponseDataModel> startrOrderDataModelMutableLiveData;
    private MutableLiveData<OtpVerifyResponseDataModel> verifyOTPDataModelMutableLiveData;
    private MutableLiveData<CompleteOrderPointResponseDataModel> completeOrderDataModelMutableLiveData;
    private MutableLiveData<AcceptPaymentResponseModel> acceptPaymentDataModelMutableLiveData;
    private MutableLiveData<AcceptPaymentResponseModel> orderRatingDataModelMutableLiveData;

    public LiveData<ListOrderResponseDataModel> getOrderListData() {

        rderListDataModelMutableLiveData = new NetworkCall().getOrderListData();
        return rderListDataModelMutableLiveData;
    }



    public LiveData<OrderDetailsResponseDatamodel> getOrderDetailsData() {

        /*if (orderdetailsDataModelMutableLiveData == null) {
            orderdetailsDataModelMutableLiveData = new NetworkCall().getOrderDetails();
        }*/
        orderdetailsDataModelMutableLiveData = new NetworkCall().getOrderDetails();
        return orderdetailsDataModelMutableLiveData;
    }

    public LiveData<AcceptOrderResponseDataModel> acceptOrderData() {

        /*if (acceptOrderDataModelMutableLiveData == null) {
            acceptOrderDataModelMutableLiveData = new NetworkCall().getAcceptOrder();
        }*/
       acceptOrderDataModelMutableLiveData = new NetworkCall().getAcceptOrder();
        return acceptOrderDataModelMutableLiveData;
    }

    public LiveData<StartOrderResponseDataModel> startOrderData() {

        startrOrderDataModelMutableLiveData = new NetworkCall().getStartOrderData();
        return startrOrderDataModelMutableLiveData;
    }

    public LiveData<OtpVerifyResponseDataModel> verifyOTPData(OrderItemOTPVerifyModel orderItemOTPVerifyModel) {

        verifyOTPDataModelMutableLiveData = new NetworkCall().verifyOtpData(orderItemOTPVerifyModel);

        return verifyOTPDataModelMutableLiveData;
    }
    public LiveData<OtpVerifyResponseDataModel> verifySignatureData(SignatureAPIModel signatureAPIModel) {

        verifyOTPDataModelMutableLiveData = new NetworkCall().verifySignatureCall(signatureAPIModel);

        return verifyOTPDataModelMutableLiveData;
    }

    public LiveData<CompleteOrderPointResponseDataModel> completeOrderData(CompleteOrderAPIModel completeOrderAPIModel) {

        completeOrderDataModelMutableLiveData = new NetworkCall().completeOrderData(completeOrderAPIModel);

        return completeOrderDataModelMutableLiveData;
    }

    public LiveData<AcceptPaymentResponseModel> paymentAcceptData(AcceptPaymentAPIModel acceptPaymentAPIModel) {

        acceptPaymentDataModelMutableLiveData = new NetworkCall().callAcceptPayment(acceptPaymentAPIModel);

        return acceptPaymentDataModelMutableLiveData;
    }

    public LiveData<AcceptPaymentResponseModel> orderRatingData(OrderRatingAPIModel orderRatingAPIModel) {

        orderRatingDataModelMutableLiveData = new NetworkCall().orderRatingData(orderRatingAPIModel);

        return orderRatingDataModelMutableLiveData;
    }
}
