package com.dev.pigeonproviderapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dev.pigeonproviderapp.datamodel.AcceptPaymentResponseModel;
import com.dev.pigeonproviderapp.datamodel.BankDetailsGetModelResponse;
import com.dev.pigeonproviderapp.datamodel.GetUserDocumentResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.OTPSendResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.OrderDetailsResponseDatamodel;
import com.dev.pigeonproviderapp.datamodel.PaymentHistoryDataModel;
import com.dev.pigeonproviderapp.datamodel.ProfileGetResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.ProfileUpdateResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.UpdateProfilePIctureDataModel;
import com.dev.pigeonproviderapp.httpRequest.BankDetailsSubmitAPIModel;
import com.dev.pigeonproviderapp.httpRequest.OTPSendAPIModel;
import com.dev.pigeonproviderapp.httpRequest.ProfileUpdateAPI;
import com.dev.pigeonproviderapp.repo.NetworkCall;

import okhttp3.MultipartBody;
import retrofit2.http.Part;

public class ProfileViewModel extends ViewModel {
    MutableLiveData<ProfileUpdateResponseDataModel> profileUploadDataModelMutableLiveData;
    MutableLiveData<ProfileUpdateResponseDataModel> profileUpdateDataModelMutableLiveData;
    MutableLiveData<UpdateProfilePIctureDataModel> updateProfilePIctureDataModelMutableLiveData;
    MutableLiveData<ProfileGetResponseDataModel> profileGetDataModelMutableLiveData;
    MutableLiveData<AcceptPaymentResponseModel> addBankDetailsDataModelMutableLiveData;
    MutableLiveData<BankDetailsGetModelResponse> getBankDetailsDataModelMutableLiveData;
    MutableLiveData<GetUserDocumentResponseDataModel> getUserDocumentDataModelMutableLiveData;
    MutableLiveData<PaymentHistoryDataModel> paymentHistoryDataModelMutableLiveData;


    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public LiveData<ProfileUpdateResponseDataModel> getProfileUploaddata(ProfileUpdateAPI profileUpdateAPI) {

        profileUploadDataModelMutableLiveData = new NetworkCall().callprofileInfo(profileUpdateAPI);
        return profileUploadDataModelMutableLiveData;
    }

    public LiveData<ProfileUpdateResponseDataModel> getProfileUpdatedata(ProfileUpdateAPI profileUpdateAPI) {


        profileUpdateDataModelMutableLiveData = new NetworkCall().callupdateprofileInfo(profileUpdateAPI);
        return profileUpdateDataModelMutableLiveData;
    }

    public LiveData<UpdateProfilePIctureDataModel> uploadProfilePicture(
            @Part MultipartBody.Part part) {

        /*if (updateProfilePIctureDataModelMutableLiveData == null) {
            updateProfilePIctureDataModelMutableLiveData = new NetworkCall().uploadProfilePicture(part);
        }*/
        updateProfilePIctureDataModelMutableLiveData = new NetworkCall().uploadProfilePicture(part);
        return updateProfilePIctureDataModelMutableLiveData;
    }

    public LiveData<ProfileGetResponseDataModel> gerProfile() {

        profileGetDataModelMutableLiveData = new NetworkCall().getProfileData();
        return profileGetDataModelMutableLiveData;
    }
    public LiveData<AcceptPaymentResponseModel> addBankDetails(BankDetailsSubmitAPIModel bankDetailsSubmitAPIModel) {

        addBankDetailsDataModelMutableLiveData = new NetworkCall().addBankDetailsData(bankDetailsSubmitAPIModel);
        return addBankDetailsDataModelMutableLiveData;
    }

    public LiveData<BankDetailsGetModelResponse> getBankDetails() {

        getBankDetailsDataModelMutableLiveData = new NetworkCall().getBankDetailsData();
        return getBankDetailsDataModelMutableLiveData;
    }

    public LiveData<GetUserDocumentResponseDataModel> getUserDocument() {

        getUserDocumentDataModelMutableLiveData = new NetworkCall().userDocumentData();
        return getUserDocumentDataModelMutableLiveData;
    }

    public LiveData<PaymentHistoryDataModel> getPaymentHistory() {

        paymentHistoryDataModelMutableLiveData = new NetworkCall().paymentHistoryData();
        return paymentHistoryDataModelMutableLiveData;
    }


}
