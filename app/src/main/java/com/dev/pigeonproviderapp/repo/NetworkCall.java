package com.dev.pigeonproviderapp.repo;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.Utility.Utility;
import com.dev.pigeonproviderapp.datamodel.AcceptOrderResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.AcceptPaymentResponseModel;
import com.dev.pigeonproviderapp.datamodel.AddDocumentResponseModel;
import com.dev.pigeonproviderapp.datamodel.BankDetailsGetModelResponse;
import com.dev.pigeonproviderapp.datamodel.CompleteOrderPointResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.CompleteTaskErrorPojoClass;
import com.dev.pigeonproviderapp.datamodel.DroppointVerifyErrorPojoClass;
import com.dev.pigeonproviderapp.datamodel.GetUserDocumentResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.ListOrderResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.LocationDatamodel;
import com.dev.pigeonproviderapp.datamodel.NotificationDatamodel;
import com.dev.pigeonproviderapp.datamodel.OTPSendResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.OrderDetailsResponseDatamodel;
import com.dev.pigeonproviderapp.datamodel.OtpVerifyResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.PastOrderResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.PaymentHistoryDataModel;
import com.dev.pigeonproviderapp.datamodel.ProfileGetResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.ProfileUpdateResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.ProviderAvailabilityDatamodel;
import com.dev.pigeonproviderapp.datamodel.StartOrderResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.UpdateProfilePIctureDataModel;
import com.dev.pigeonproviderapp.datamodel.UploadDocumentImageResponseModel;
import com.dev.pigeonproviderapp.datamodel.VerifyOtpResponseDataModel;
import com.dev.pigeonproviderapp.httpRequest.AcceptPaymentAPIModel;
import com.dev.pigeonproviderapp.httpRequest.AddDocumentAPIModel;
import com.dev.pigeonproviderapp.httpRequest.BankDetailsSubmitAPIModel;
import com.dev.pigeonproviderapp.httpRequest.CompleteOrderAPIModel;
import com.dev.pigeonproviderapp.httpRequest.LocationRequestSendModel;
import com.dev.pigeonproviderapp.httpRequest.OTPSendAPIModel;
import com.dev.pigeonproviderapp.httpRequest.OrderItemOTPVerifyModel;
import com.dev.pigeonproviderapp.httpRequest.OrderRatingAPIModel;
import com.dev.pigeonproviderapp.httpRequest.ProfileUpdateAPI;
import com.dev.pigeonproviderapp.httpRequest.ProviderAvailabilityAPIModel;
import com.dev.pigeonproviderapp.httpRequest.SignatureAPIModel;
import com.dev.pigeonproviderapp.httpRequest.VerifyOtpAPIModel;
import com.dev.pigeonproviderapp.network.APIClient;
import com.dev.pigeonproviderapp.network.APIInterface;
import com.dev.pigeonproviderapp.storage.Singleton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Part;

public class NetworkCall {

    protected APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    public MutableLiveData<OTPSendResponseDataModel> callSendOTP(OTPSendAPIModel otpSendAPIModel) {

        MutableLiveData<OTPSendResponseDataModel> otpSendDataModelLiveData = new MutableLiveData<OTPSendResponseDataModel>();

        Call<OTPSendResponseDataModel> registerAPI = apiInterface.OTPSendAPICall(otpSendAPIModel, Utility.USERTYPE);

        registerAPI.enqueue(new Callback<OTPSendResponseDataModel>() {
            @Override
            public void onResponse(Call<OTPSendResponseDataModel> call, Response<OTPSendResponseDataModel> response) {

                if (response.isSuccessful()) {
                    otpSendDataModelLiveData.postValue(response.body());
                    Log.d("Aslam", response.body().toString());
                } else {
                    Log.d("Aslam", response.errorBody().toString());
                    otpSendDataModelLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<OTPSendResponseDataModel> call, Throwable t) {

            }
        });

        return otpSendDataModelLiveData;

    }

    public MutableLiveData<VerifyOtpResponseDataModel> callverifyOTP(VerifyOtpAPIModel verifyOtpAPIModel) {

        MutableLiveData<VerifyOtpResponseDataModel> otpverifyDataModelLiveData = new MutableLiveData<VerifyOtpResponseDataModel>();

        Call<VerifyOtpResponseDataModel> otpverifyAPI = apiInterface.VerifyOtpAPICall(verifyOtpAPIModel, Utility.USERTYPE);

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

    public MutableLiveData<ProfileUpdateResponseDataModel> callprofileInfo(ProfileUpdateAPI profileUpdateAPI) {

        MutableLiveData<ProfileUpdateResponseDataModel> profileInfoUpdateDataModelLiveData = new MutableLiveData<ProfileUpdateResponseDataModel>();

        Call<ProfileUpdateResponseDataModel> profileInfoUpdateAPI = apiInterface.profileInfouploadAPICall(Singleton.getInstance().getTOKEN(), profileUpdateAPI, Utility.USERTYPE);

        profileInfoUpdateAPI.enqueue(new Callback<ProfileUpdateResponseDataModel>() {
            @Override
            public void onResponse(Call<ProfileUpdateResponseDataModel> call, Response<ProfileUpdateResponseDataModel> response) {

                if (response.isSuccessful()) {
                    profileInfoUpdateDataModelLiveData.postValue(response.body());
                    Log.d("Mangal", response.body().toString());
                } else {
                    Log.d("MangalError", response.errorBody().toString());
                    profileInfoUpdateDataModelLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ProfileUpdateResponseDataModel> call, Throwable t) {

            }
        });

        return profileInfoUpdateDataModelLiveData;

    }

    public MutableLiveData<ProfileUpdateResponseDataModel> callupdateprofileInfo(ProfileUpdateAPI profileUpdateAPI) {

        MutableLiveData<ProfileUpdateResponseDataModel> profileInfoUpdateDataModelLiveData = new MutableLiveData<ProfileUpdateResponseDataModel>();

        Call<ProfileUpdateResponseDataModel> profileInfoUpdateApi = apiInterface.updateProfileInfoAPICall(Singleton.getInstance().getTOKEN(), profileUpdateAPI, Utility.USERTYPE);

        profileInfoUpdateApi.enqueue(new Callback<ProfileUpdateResponseDataModel>() {
            @Override
            public void onResponse(Call<ProfileUpdateResponseDataModel> call, Response<ProfileUpdateResponseDataModel> response) {

                if (response.isSuccessful()) {
                    profileInfoUpdateDataModelLiveData.postValue(response.body());
                    Log.d("Mangal", response.body().toString());
                } else {
                    Log.d("Mangal", response.errorBody().toString());
                    profileInfoUpdateDataModelLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ProfileUpdateResponseDataModel> call, Throwable t) {
                Log.d("Debug", t.getMessage());
            }
        });

        return profileInfoUpdateDataModelLiveData;

    }

    public MutableLiveData<UpdateProfilePIctureDataModel> uploadProfilePicture(
            @Part MultipartBody.Part profile_picture) {

        MutableLiveData<UpdateProfilePIctureDataModel> updateProfilePIctureDataModelMutableLiveData = new MutableLiveData<UpdateProfilePIctureDataModel>();

        Call<UpdateProfilePIctureDataModel> updateProfilePicture = apiInterface
                .updateProfilePicture(Singleton.getInstance().getTOKEN(), profile_picture, Utility.USERTYPE);

        updateProfilePicture.enqueue(new Callback<UpdateProfilePIctureDataModel>() {
            @Override
            public void onResponse(Call<UpdateProfilePIctureDataModel> call,
                                   Response<UpdateProfilePIctureDataModel> response) {

                if (response.isSuccessful()) {
                    Log.d("Aslam", "Successfull" + response.message());
                    updateProfilePIctureDataModelMutableLiveData.postValue(response.body());
                } else {
                    Log.d("Aslam", "Fail " + response.message());
                    updateProfilePIctureDataModelMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<UpdateProfilePIctureDataModel> call, Throwable t) {
                Log.d("Aslam", "Fail " + t.getMessage());
            }
        });

        return updateProfilePIctureDataModelMutableLiveData;
    }

//Upload document Image

    public MutableLiveData<UploadDocumentImageResponseModel> uploadDocumentPicture(
            @Part MultipartBody.Part profile_picture) {

        MutableLiveData<UploadDocumentImageResponseModel> uploadDocumentImageDataModelMutableLiveData = new MutableLiveData<UploadDocumentImageResponseModel>();

        Call<UploadDocumentImageResponseModel> uploadDocumentImage = apiInterface
                .uploadDocumentImage(Singleton.getInstance().getTOKEN(), profile_picture, Utility.USERTYPE);

        uploadDocumentImage.enqueue(new Callback<UploadDocumentImageResponseModel>() {
            @Override
            public void onResponse(Call<UploadDocumentImageResponseModel> call,
                                   Response<UploadDocumentImageResponseModel> response) {

                if (response.isSuccessful()) {
                    Log.d("Aslam", "Successfull" + response.message());
                    uploadDocumentImageDataModelMutableLiveData.postValue(response.body());
                } else {
                    Log.d("Aslam", "Fail " + response.message());
                    uploadDocumentImageDataModelMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<UploadDocumentImageResponseModel> call, Throwable t) {
                Log.d("Faliure", "Fail " + t.getMessage());
            }
        });

        return uploadDocumentImageDataModelMutableLiveData;
    }

    //Add document Image

    public MutableLiveData<AddDocumentResponseModel> callAddDocuments(AddDocumentAPIModel addDocumentAPIModel) {

        MutableLiveData<AddDocumentResponseModel> addDocumentDataModelLiveData = new MutableLiveData<AddDocumentResponseModel>();

        Call<AddDocumentResponseModel> addDocumentAPI = apiInterface.addDocumentImage(Singleton.getInstance().getTOKEN(), addDocumentAPIModel, Utility.USERTYPE);

        addDocumentAPI.enqueue(new Callback<AddDocumentResponseModel>() {
            @Override
            public void onResponse(Call<AddDocumentResponseModel> call, Response<AddDocumentResponseModel> response) {

                if (response.isSuccessful()) {
                    addDocumentDataModelLiveData.postValue(response.body());
                    Log.d("Mangal", response.body().toString());
                } else {
                    Log.d("Mangal", response.errorBody().toString());
                    addDocumentDataModelLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<AddDocumentResponseModel> call, Throwable t) {

            }
        });

        return addDocumentDataModelLiveData;

    }

    //Profile Get

    public MutableLiveData<ProfileGetResponseDataModel> getProfileData() {

        MutableLiveData<ProfileGetResponseDataModel> getProfileModelMutableLiveData = new MutableLiveData<ProfileGetResponseDataModel>();

        Call<ProfileGetResponseDataModel> getProfileCall = apiInterface
                .getProfile(Singleton.getInstance().getTOKEN(), Utility.USERTYPE);

        getProfileCall.enqueue(new Callback<ProfileGetResponseDataModel>() {
            @Override
            public void onResponse(Call<ProfileGetResponseDataModel> call,
                                   Response<ProfileGetResponseDataModel> response) {

                if (response.isSuccessful()) {
                    getProfileModelMutableLiveData.postValue(response.body());
                    Log.d("Aslam", response.body().toString());
                } else {
                    Log.d("Aslam", response.errorBody().toString());
                    getProfileModelMutableLiveData.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<ProfileGetResponseDataModel> call, Throwable t) {
                Log.d("Aslam", t.getMessage());
            }
        });

        return getProfileModelMutableLiveData;
    }

    //Order List

    public MutableLiveData<ListOrderResponseDataModel> getOrderListData() {

        MutableLiveData<ListOrderResponseDataModel> listOrderDataModelMutableLiveData = new MutableLiveData<ListOrderResponseDataModel>();

        Call<ListOrderResponseDataModel> getOrderListCall = apiInterface
                .getOrderListCall(Singleton.getInstance().getTOKEN(), Utility.USERTYPE);

        getOrderListCall.enqueue(new Callback<ListOrderResponseDataModel>() {
            @Override
            public void onResponse(Call<ListOrderResponseDataModel> call,
                                   Response<ListOrderResponseDataModel> response) {

                if (response.isSuccessful()) {
                    listOrderDataModelMutableLiveData.postValue(response.body());
                    Log.d("Aslam", response.body().toString());
                } else {
                    Log.d("Aslam Not success", response.errorBody().toString());
                    listOrderDataModelMutableLiveData.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<ListOrderResponseDataModel> call, Throwable t) {
                Log.d("Aslam++", t.getMessage());
            }
        });

        return listOrderDataModelMutableLiveData;
    }

    //PastOrder List

    public MutableLiveData<PastOrderResponseDataModel> getPastOrderListData() {

        MutableLiveData<PastOrderResponseDataModel> listPastOrderDataModelMutableLiveData = new MutableLiveData<PastOrderResponseDataModel>();

        Call<PastOrderResponseDataModel> getPastOrderListCall = apiInterface
                .getPastOrderListCall(Singleton.getInstance().getTOKEN(), Utility.USERTYPE);

        getPastOrderListCall.enqueue(new Callback<PastOrderResponseDataModel>() {
            @Override
            public void onResponse(Call<PastOrderResponseDataModel> call,
                                   Response<PastOrderResponseDataModel> response) {

                if (response.isSuccessful()) {
                    listPastOrderDataModelMutableLiveData.postValue(response.body());
                    Log.d("Aslam", response.body().toString());
                } else {
                    Log.d("Aslam Not success", response.errorBody().toString());
                    listPastOrderDataModelMutableLiveData.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<PastOrderResponseDataModel> call, Throwable t) {
                Log.d("Aslam++", t.getMessage());
            }
        });

        return listPastOrderDataModelMutableLiveData;
    }


    //Order Details List

    public MutableLiveData<OrderDetailsResponseDatamodel> getOrderDetails() {

        MutableLiveData<OrderDetailsResponseDatamodel> OrderDetailsDataModelMutableLiveData = new MutableLiveData<OrderDetailsResponseDatamodel>();

        Call<OrderDetailsResponseDatamodel> getOrderDetailsCall = apiInterface
                .getOrderDetails(Singleton.getInstance().getTOKEN(), Singleton.getInstance().getORDERID(), Utility.USERTYPE);

        getOrderDetailsCall.enqueue(new Callback<OrderDetailsResponseDatamodel>() {
            @Override
            public void onResponse(Call<OrderDetailsResponseDatamodel> call,
                                   Response<OrderDetailsResponseDatamodel> response) {

                if (response.isSuccessful()) {
                    OrderDetailsDataModelMutableLiveData.postValue(response.body());
                    Log.d("Aslam", response.body().toString());
                } else {
                    Log.d("Aslam", response.errorBody().toString());
                    OrderDetailsDataModelMutableLiveData.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<OrderDetailsResponseDatamodel> call, Throwable t) {
                Log.d("ORDERDETAILS", t.getMessage());
            }
        });


        return OrderDetailsDataModelMutableLiveData;
    }

    //Accept order API

    public MutableLiveData<AcceptOrderResponseDataModel> getAcceptOrder() {

        MutableLiveData<AcceptOrderResponseDataModel> acceptOrderDataModelMutableLiveData = new MutableLiveData<AcceptOrderResponseDataModel>();

        Call<AcceptOrderResponseDataModel> orderAcceptCall = apiInterface
                .acceptOrderCall(Singleton.getInstance().getTOKEN(), Singleton.getInstance().getORDERID(), Utility.USERTYPE);

        orderAcceptCall.enqueue(new Callback<AcceptOrderResponseDataModel>() {
            @Override
            public void onResponse(Call<AcceptOrderResponseDataModel> call,
                                   Response<AcceptOrderResponseDataModel> response) {

                if (response.isSuccessful()) {
                    acceptOrderDataModelMutableLiveData.postValue(response.body());
                    Log.d("Aslam", response.body().toString());
                } else {
                    Log.d("Aslam", response.errorBody().toString());
                    acceptOrderDataModelMutableLiveData.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<AcceptOrderResponseDataModel> call, Throwable t) {
                Log.d("Aslam", t.getMessage());
            }
        });


        return acceptOrderDataModelMutableLiveData;
    }

    //Start Order Call

    public MutableLiveData<StartOrderResponseDataModel> getStartOrderData() {

        MutableLiveData<StartOrderResponseDataModel> startOrderDataModelMutableLiveData = new MutableLiveData<StartOrderResponseDataModel>();

        Call<StartOrderResponseDataModel> orderStartCall = apiInterface
                .startOrderCall(Singleton.getInstance().getTOKEN(), Singleton.getInstance().getORDERID(), Utility.USERTYPE);

        orderStartCall.enqueue(new Callback<StartOrderResponseDataModel>() {
            @Override
            public void onResponse(Call<StartOrderResponseDataModel> call,
                                   Response<StartOrderResponseDataModel> response) {

                if (response.isSuccessful()) {
                    startOrderDataModelMutableLiveData.postValue(response.body());
                    Log.d("Aslam", response.body().toString());
                } else {
                    Log.d("Aslam", response.errorBody().toString());
                    startOrderDataModelMutableLiveData.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<StartOrderResponseDataModel> call, Throwable t) {
                Log.d("Aslam", t.getMessage());
            }
        });


        return startOrderDataModelMutableLiveData;
    }

    //VerifyItem OTP Call

    public MutableLiveData<OtpVerifyResponseDataModel> verifyOtpData(OrderItemOTPVerifyModel orderItemOTPVerifyModel) {

        MutableLiveData<OtpVerifyResponseDataModel> verifyOtpDataModelMutableLiveData = new MutableLiveData<OtpVerifyResponseDataModel>();

        Call<OtpVerifyResponseDataModel> verifyOTPcall = apiInterface
                .verifyOTPCall(Singleton.getInstance().getTOKEN(), Singleton.getInstance().getORDERID(), Singleton.getInstance().getORDERITEMID(), orderItemOTPVerifyModel, Utility.USERTYPE);

        verifyOTPcall.enqueue(new Callback<OtpVerifyResponseDataModel>() {
            @Override
            public void onResponse(Call<OtpVerifyResponseDataModel> call,
                                   Response<OtpVerifyResponseDataModel> response) {

                if (response.isSuccessful()) {
                    verifyOtpDataModelMutableLiveData.postValue(response.body());
                    if (response.code() == 200) {
                        Singleton.getInstance().setERRORSTATUS(200);
                    }
                    Log.d("Aslam Success", response.body().toString());
                } else {
                    Log.d("Aslam Not", response.errorBody().toString());
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            Gson gson = new GsonBuilder().create();
                            DroppointVerifyErrorPojoClass mError = new DroppointVerifyErrorPojoClass();
                            try {
                                mError = gson.fromJson(response.errorBody().string(), DroppointVerifyErrorPojoClass.class);
                                Log.d("Mangaldip", String.valueOf(mError.getStatus()));
                                Singleton.getInstance().setERRORSTATUS(mError.getStatus());
                                Singleton.getInstance().setOTPVERIFYMESSAGE(mError.getErrorMessage());
                            } catch (IOException e) {
                                // handle failure to read error
                            }
                        }
                    }
                    verifyOtpDataModelMutableLiveData.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<OtpVerifyResponseDataModel> call, Throwable t) {
                Log.d("Aslam++", t.getMessage());
            }
        });


        return verifyOtpDataModelMutableLiveData;
    }

    //VerifyItem OTP Call

    public MutableLiveData<OtpVerifyResponseDataModel> verifySignatureCall(SignatureAPIModel signatureAPIModel) {

        MutableLiveData<OtpVerifyResponseDataModel> verifySignatureDataModelMutableLiveData = new MutableLiveData<OtpVerifyResponseDataModel>();

        Call<OtpVerifyResponseDataModel> verifySignatureCall = apiInterface
                .verifySignatureCall(Singleton.getInstance().getTOKEN(), Singleton.getInstance().getORDERID(), Singleton.getInstance().getORDERITEMID(), signatureAPIModel, Utility.USERTYPE);

        verifySignatureCall.enqueue(new Callback<OtpVerifyResponseDataModel>() {
            @Override
            public void onResponse(Call<OtpVerifyResponseDataModel> call,
                                   Response<OtpVerifyResponseDataModel> response) {

                if (response.isSuccessful()) {
                    verifySignatureDataModelMutableLiveData.postValue(response.body());
                    Log.d("Aslam Success", response.body().toString());
                } else {
                    Log.d("Aslam Not", response.errorBody().toString());
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            Gson gson = new GsonBuilder().create();
                            DroppointVerifyErrorPojoClass mError = new DroppointVerifyErrorPojoClass();
                            try {
                                mError = gson.fromJson(response.errorBody().string(), DroppointVerifyErrorPojoClass.class);
                                Log.d("Mangaldip", String.valueOf(mError.getStatus()));
                                Singleton.getInstance().setERRORSTATUS(mError.getStatus());
                            } catch (IOException e) {
                                // handle failure to read error
                            }
                        }
                    }
                    verifySignatureDataModelMutableLiveData.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<OtpVerifyResponseDataModel> call, Throwable t) {
                Log.d("Aslam++", t.getMessage());
            }
        });


        return verifySignatureDataModelMutableLiveData;
    }

    //Complete order call

    public MutableLiveData<CompleteOrderPointResponseDataModel> completeOrderData(CompleteOrderAPIModel completeOrderAPIModel) {

        MutableLiveData<CompleteOrderPointResponseDataModel> completeOrderpointDataModelMutableLiveData = new MutableLiveData<CompleteOrderPointResponseDataModel>();

        Call<CompleteOrderPointResponseDataModel> completeOrderCall = apiInterface
                .completeOrderCall(Singleton.getInstance().getTOKEN(), Singleton.getInstance().getORDERID(), Singleton.getInstance().getORDERITEMID(), completeOrderAPIModel, Utility.USERTYPE);

        completeOrderCall.enqueue(new Callback<CompleteOrderPointResponseDataModel>() {
            @Override
            public void onResponse(Call<CompleteOrderPointResponseDataModel> call,
                                   Response<CompleteOrderPointResponseDataModel> response) {

                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        if (response.isSuccessful()) {

                            System.out.println("CrashCheck"+response.body().getStatus());
                            System.out.println("CrashCheck"+response.body().getData().getIsAllDropPointsCompleted());
                            Singleton.getInstance().setERRORSTATUS(response.body().getStatus());
                            Singleton.getInstance().setALLDROPPOINTCOMPLETE(response.body().getData().getIsAllDropPointsCompleted());
                            //Singleton.getInstance().setALLDROPPOINTCOMPLETE(response.body().getData().getIsAllDropPointsCompleted());


                        }
                    }
                    completeOrderpointDataModelMutableLiveData.postValue(response.body());
                    Log.d("Aslam ", response.body().toString());
                } else {
                    if (response.code() == 400) {
                        if (!response.isSuccessful()) {
                            Gson gson = new GsonBuilder().create();
                            CompleteTaskErrorPojoClass mError = new CompleteTaskErrorPojoClass();
                            try {
                                mError = gson.fromJson(response.errorBody().string(), CompleteTaskErrorPojoClass.class);
                                Singleton.getInstance().setERRORSTATUS(mError.getStatus());
                                Singleton.getInstance().setOTPVERIFYMESSAGE(mError.getErrorMessage());
                            } catch (IOException e) {
                                // handle failure to read error
                            }
                        }
                    }
                    completeOrderpointDataModelMutableLiveData.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<CompleteOrderPointResponseDataModel> call, Throwable t) {
                Log.d("Aslam", t.getMessage());
            }
        });


        return completeOrderpointDataModelMutableLiveData;
    }

    //Call Payment accept

    public MutableLiveData<AcceptPaymentResponseModel> callAcceptPayment(AcceptPaymentAPIModel acceptPaymentAPIModel) {

        MutableLiveData<AcceptPaymentResponseModel> acceptPaymentDataModelLiveData = new MutableLiveData<AcceptPaymentResponseModel>();

        Call<AcceptPaymentResponseModel> acceptPaymentByProviderAPI = apiInterface.acceptPaymentCall(Singleton.getInstance().getTOKEN(), acceptPaymentAPIModel, Utility.USERTYPE);

        acceptPaymentByProviderAPI.enqueue(new Callback<AcceptPaymentResponseModel>() {
            @Override
            public void onResponse(Call<AcceptPaymentResponseModel> call, Response<AcceptPaymentResponseModel> response) {

                if (response.isSuccessful()) {
                    acceptPaymentDataModelLiveData.postValue(response.body());
                    Log.d("Mangal", response.body().toString());
                } else {
                    Log.d("Mangal", response.errorBody().toString());
                    acceptPaymentDataModelLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<AcceptPaymentResponseModel> call, Throwable t) {

            }
        });

        return acceptPaymentDataModelLiveData;

    }

    //Order Rating call

    public MutableLiveData<AcceptPaymentResponseModel> orderRatingData(OrderRatingAPIModel orderRatingAPIModel) {

        MutableLiveData<AcceptPaymentResponseModel> orderRatingDataModelMutableLiveData = new MutableLiveData<AcceptPaymentResponseModel>();

        Call<AcceptPaymentResponseModel> orderRatingCall = apiInterface
                .providerRatingCall(Singleton.getInstance().getTOKEN(), Singleton.getInstance().getORDERID(), orderRatingAPIModel, Utility.USERTYPE);

        orderRatingCall.enqueue(new Callback<AcceptPaymentResponseModel>() {
            @Override
            public void onResponse(Call<AcceptPaymentResponseModel> call,
                                   Response<AcceptPaymentResponseModel> response) {

                if (response.isSuccessful()) {
                    orderRatingDataModelMutableLiveData.postValue(response.body());
                    Log.d("Aslam", response.body().toString());
                } else {
                    Log.d("Aslam", response.errorBody().toString());
                    orderRatingDataModelMutableLiveData.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<AcceptPaymentResponseModel> call, Throwable t) {
                Log.d("Aslam", t.getMessage());
            }
        });


        return orderRatingDataModelMutableLiveData;
    }

    //Add Bank Details


    public MutableLiveData<AcceptPaymentResponseModel> addBankDetailsData(BankDetailsSubmitAPIModel bankDetailsSubmitAPIModel) {

        MutableLiveData<AcceptPaymentResponseModel> bankDetailsAddModelMutableLiveData = new MutableLiveData<AcceptPaymentResponseModel>();

        Call<AcceptPaymentResponseModel> addBankDetailsCall = apiInterface
                .addBankDetailsAPICall(Singleton.getInstance().getTOKEN(), bankDetailsSubmitAPIModel, Utility.USERTYPE);

        addBankDetailsCall.enqueue(new Callback<AcceptPaymentResponseModel>() {
            @Override
            public void onResponse(Call<AcceptPaymentResponseModel> call,
                                   Response<AcceptPaymentResponseModel> response) {

                if (response.isSuccessful()) {
                    bankDetailsAddModelMutableLiveData.postValue(response.body());
                    Log.d("Aslam", response.body().toString());
                } else {
                    Log.d("Aslam", response.errorBody().toString());
                    bankDetailsAddModelMutableLiveData.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<AcceptPaymentResponseModel> call, Throwable t) {
                Log.d("Aslam", t.getMessage());
            }
        });


        return bankDetailsAddModelMutableLiveData;
    }

    //Gate Bank Details

    public MutableLiveData<BankDetailsGetModelResponse> getBankDetailsData() {

        MutableLiveData<BankDetailsGetModelResponse> bankDetailsGetModelMutableLiveData = new MutableLiveData<BankDetailsGetModelResponse>();

        Call<BankDetailsGetModelResponse> getBankDetailsCall = apiInterface
                .getBankDetailsAPICall(Singleton.getInstance().getTOKEN(), Utility.USERTYPE);

        getBankDetailsCall.enqueue(new Callback<BankDetailsGetModelResponse>() {
            @Override
            public void onResponse(Call<BankDetailsGetModelResponse> call,
                                   Response<BankDetailsGetModelResponse> response) {

                if (response.isSuccessful()) {
                    bankDetailsGetModelMutableLiveData.postValue(response.body());
                    Log.d("Aslam", response.body().toString());
                } else {
                    Log.d("Aslam", response.errorBody().toString());
                    bankDetailsGetModelMutableLiveData.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<BankDetailsGetModelResponse> call, Throwable t) {
                Log.d("Aslam", t.getMessage());
            }
        });


        return bankDetailsGetModelMutableLiveData;
    }

    //Gate Bank Details

    public MutableLiveData<GetUserDocumentResponseDataModel> userDocumentData() {

        MutableLiveData<GetUserDocumentResponseDataModel> getUserDocumentModelMutableLiveData = new MutableLiveData<GetUserDocumentResponseDataModel>();

        Call<GetUserDocumentResponseDataModel> getUserDocumentCall = apiInterface
                .getUserDocumentAPICall(Singleton.getInstance().getTOKEN(), Utility.USERTYPE);

        getUserDocumentCall.enqueue(new Callback<GetUserDocumentResponseDataModel>() {
            @Override
            public void onResponse(Call<GetUserDocumentResponseDataModel> call,
                                   Response<GetUserDocumentResponseDataModel> response) {

                if (response.isSuccessful()) {
                    getUserDocumentModelMutableLiveData.postValue(response.body());
                    Log.d("Aslam", response.body().toString());
                } else {
                    Log.d("Aslam", response.errorBody().toString());
                    getUserDocumentModelMutableLiveData.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<GetUserDocumentResponseDataModel> call, Throwable t) {
                Log.d("Aslam", t.getMessage());
            }
        });


        return getUserDocumentModelMutableLiveData;
    }

    //Payment History and Earn

    public MutableLiveData<PaymentHistoryDataModel> paymentHistoryData() {

        MutableLiveData<PaymentHistoryDataModel> paymentHistoryModelMutableLiveData = new MutableLiveData<PaymentHistoryDataModel>();

        Call<PaymentHistoryDataModel> paymentHistoryCall = apiInterface
                .paymentHistoryAPICall(Singleton.getInstance().getTOKEN(), Utility.USERTYPE);

        paymentHistoryCall.enqueue(new Callback<PaymentHistoryDataModel>() {
            @Override
            public void onResponse(Call<PaymentHistoryDataModel> call,
                                   Response<PaymentHistoryDataModel> response) {

                if (response.isSuccessful()) {
                    paymentHistoryModelMutableLiveData.postValue(response.body());
                    Log.d("Aslam", response.body().toString());
                } else {
                    Log.d("Aslam", response.errorBody().toString());
                    paymentHistoryModelMutableLiveData.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<PaymentHistoryDataModel> call, Throwable t) {
                Log.d("Aslam", t.getMessage());
            }
        });


        return paymentHistoryModelMutableLiveData;
    }

    //Provider availability

    public MutableLiveData<ProviderAvailabilityDatamodel> provideravailableData(ProviderAvailabilityAPIModel providerAvailabilityAPIModel) {

        MutableLiveData<ProviderAvailabilityDatamodel> providerAvailabilityModelMutableLiveData = new MutableLiveData<ProviderAvailabilityDatamodel>();

        Call<ProviderAvailabilityDatamodel> providerAvailableCall = apiInterface
                .providerAvialableAPICall(Singleton.getInstance().getTOKEN(), providerAvailabilityAPIModel, Utility.USERTYPE);

        providerAvailableCall.enqueue(new Callback<ProviderAvailabilityDatamodel>() {
            @Override
            public void onResponse(Call<ProviderAvailabilityDatamodel> call,
                                   Response<ProviderAvailabilityDatamodel> response) {

                if (response.isSuccessful()) {
                    providerAvailabilityModelMutableLiveData.postValue(response.body());
                    Log.d("Aslam", response.body().toString());
                } else {
                    Log.d("Aslam", response.errorBody().toString());
                    providerAvailabilityModelMutableLiveData.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<ProviderAvailabilityDatamodel> call, Throwable t) {
                Log.d("Aslam", t.getMessage());
            }
        });


        return providerAvailabilityModelMutableLiveData;
    }

    //Notification List
    public MutableLiveData<NotificationDatamodel> getNotificationCall() {

        MutableLiveData<NotificationDatamodel> notificationDataModelMutableLiveData = new MutableLiveData<NotificationDatamodel>();

        Call<NotificationDatamodel> getNotificationCall = apiInterface
                .getallNotificationCall(Singleton.getInstance().getTOKEN(), Utility.USERTYPE);

        getNotificationCall.enqueue(new Callback<NotificationDatamodel>() {
            @Override
            public void onResponse(Call<NotificationDatamodel> call,
                                   Response<NotificationDatamodel> response) {

                if (response.isSuccessful()) {
                    notificationDataModelMutableLiveData.postValue(response.body());
                    Log.d("Aslam", response.body().toString());
                } else {
                    Log.d("Aslam", response.errorBody().toString());
                    notificationDataModelMutableLiveData.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<NotificationDatamodel> call, Throwable t) {
                Log.d("Aslam", t.getMessage());
            }
        });

        return notificationDataModelMutableLiveData;
    }

    //Location fetch APi

    //Provider availability

    public MutableLiveData<LocationDatamodel> locationSendAPI(LocationRequestSendModel locationRequestSendModel) {

        MutableLiveData<LocationDatamodel> locationSendModelMutableLiveData = new MutableLiveData<LocationDatamodel>();

        Call<LocationDatamodel> locationAPICall = apiInterface
                .locationAPICall(Singleton.getInstance().getTOKEN(), locationRequestSendModel, Utility.USERTYPE);

        locationAPICall.enqueue(new Callback<LocationDatamodel>() {
            @Override
            public void onResponse(Call<LocationDatamodel> call,
                                   Response<LocationDatamodel> response) {

                if (response.isSuccessful()) {
                    locationSendModelMutableLiveData.postValue(response.body());
                    Log.d("Aslam", response.body().toString());
                } else {
                    Log.d("Aslam", response.errorBody().toString());
                    locationSendModelMutableLiveData.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<LocationDatamodel> call, Throwable t) {
                Log.d("Aslam", t.getMessage());
            }
        });


        return locationSendModelMutableLiveData;
    }



}
