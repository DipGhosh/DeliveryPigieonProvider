package com.dev.pigeonproviderapp.repo;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;

import com.dev.pigeonproviderapp.datamodel.AcceptOrderResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.AcceptPaymentResponseModel;
import com.dev.pigeonproviderapp.datamodel.AddDocumentResponseModel;
import com.dev.pigeonproviderapp.datamodel.CompleteOrderPointResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.ListOrderResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.OTPSendResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.OrderDetailsResponseDatamodel;
import com.dev.pigeonproviderapp.datamodel.OtpVerifyResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.ProfileGetResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.ProfileUpdateResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.StartOrderResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.UpdateProfilePIctureDataModel;
import com.dev.pigeonproviderapp.datamodel.UploadDocumentImageResponseModel;
import com.dev.pigeonproviderapp.datamodel.VerifyOtpResponseDataModel;
import com.dev.pigeonproviderapp.httpRequest.AcceptPaymentAPIModel;
import com.dev.pigeonproviderapp.httpRequest.AddDocumentAPIModel;
import com.dev.pigeonproviderapp.httpRequest.CompleteOrderAPIModel;
import com.dev.pigeonproviderapp.httpRequest.OTPSendAPIModel;
import com.dev.pigeonproviderapp.httpRequest.OrderItemOTPVerifyModel;
import com.dev.pigeonproviderapp.httpRequest.ProfileUpdateAPI;
import com.dev.pigeonproviderapp.httpRequest.VerifyOtpAPIModel;
import com.dev.pigeonproviderapp.network.APIClient;
import com.dev.pigeonproviderapp.network.APIInterface;
import com.dev.pigeonproviderapp.storage.Singleton;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Part;

public class NetworkCall {

  protected APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

  public MutableLiveData<OTPSendResponseDataModel> callSendOTP(OTPSendAPIModel otpSendAPIModel) {

    MutableLiveData<OTPSendResponseDataModel> otpSendDataModelLiveData = new MutableLiveData<OTPSendResponseDataModel>();

    Call<OTPSendResponseDataModel> registerAPI = apiInterface.OTPSendAPICall(otpSendAPIModel);

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

    Call<VerifyOtpResponseDataModel> otpverifyAPI = apiInterface.VerifyOtpAPICall(verifyOtpAPIModel);

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

    Call<ProfileUpdateResponseDataModel> profileInfoUpdateAPI = apiInterface.profileInfouploadAPICall(Singleton.getInstance().getTOKEN(),profileUpdateAPI);

    profileInfoUpdateAPI.enqueue(new Callback<ProfileUpdateResponseDataModel>() {
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

      }
    });

    return profileInfoUpdateDataModelLiveData;

  }

  public MutableLiveData<ProfileUpdateResponseDataModel> callupdateprofileInfo(ProfileUpdateAPI profileUpdateAPI) {

    MutableLiveData<ProfileUpdateResponseDataModel> profileInfoUpdateDataModelLiveData = new MutableLiveData<ProfileUpdateResponseDataModel>();

    Call<ProfileUpdateResponseDataModel> profileInfoUpdateApi = apiInterface.updateProfileInfoAPICall(Singleton.getInstance().getTOKEN(),profileUpdateAPI);

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

      }
    });

    return profileInfoUpdateDataModelLiveData;

  }

  public MutableLiveData<UpdateProfilePIctureDataModel> uploadProfilePicture(
          @Part MultipartBody.Part profile_picture) {

    MutableLiveData<UpdateProfilePIctureDataModel> updateProfilePIctureDataModelMutableLiveData = new MutableLiveData<UpdateProfilePIctureDataModel>();

    Call<UpdateProfilePIctureDataModel> updateProfilePicture = apiInterface
            .updateProfilePicture(Singleton.getInstance().getTOKEN(), profile_picture);

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

      }
    });

    return updateProfilePIctureDataModelMutableLiveData;
  }

//Upload document Image

  public MutableLiveData<UploadDocumentImageResponseModel> uploadDocumentPicture(
          @Part MultipartBody.Part profile_picture) {

    MutableLiveData<UploadDocumentImageResponseModel> uploadDocumentImageDataModelMutableLiveData = new MutableLiveData<UploadDocumentImageResponseModel>();

    Call<UploadDocumentImageResponseModel> uploadDocumentImage = apiInterface
            .uploadDocumentImage(Singleton.getInstance().getTOKEN(), profile_picture);

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

      }
    });

    return uploadDocumentImageDataModelMutableLiveData;
  }

  //Add document Image

  public MutableLiveData<AddDocumentResponseModel> callAddDocuments(AddDocumentAPIModel addDocumentAPIModel) {

    MutableLiveData<AddDocumentResponseModel> addDocumentDataModelLiveData = new MutableLiveData<AddDocumentResponseModel>();

    Call<AddDocumentResponseModel> addDocumentAPI = apiInterface.addDocumentImage(Singleton.getInstance().getTOKEN(),addDocumentAPIModel);

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
            .getProfile(Singleton.getInstance().getTOKEN());

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

      }
    });

    return getProfileModelMutableLiveData;
  }

  //Order List

  public MutableLiveData<ListOrderResponseDataModel> getOrderListData() {

    MutableLiveData<ListOrderResponseDataModel> listOrderDataModelMutableLiveData = new MutableLiveData<ListOrderResponseDataModel>();

    Call<ListOrderResponseDataModel> getOrderListCall = apiInterface
            .getOrderListCall(Singleton.getInstance().getTOKEN());

    getOrderListCall.enqueue(new Callback<ListOrderResponseDataModel>() {
      @Override
      public void onResponse(Call<ListOrderResponseDataModel> call,
                             Response<ListOrderResponseDataModel> response) {

        if (response.isSuccessful()) {
          listOrderDataModelMutableLiveData.postValue(response.body());
          Log.d("Aslam", response.body().toString());
        } else {
          Log.d("Aslam", response.errorBody().toString());
          listOrderDataModelMutableLiveData.postValue(response.body());
        }

      }

      @Override
      public void onFailure(Call<ListOrderResponseDataModel> call, Throwable t) {

      }
    });

    return listOrderDataModelMutableLiveData;
  }


  //Order Details List

  public MutableLiveData<OrderDetailsResponseDatamodel> getOrderDetails() {

    MutableLiveData<OrderDetailsResponseDatamodel> OrderDetailsDataModelMutableLiveData = new MutableLiveData<OrderDetailsResponseDatamodel>();

    Call<OrderDetailsResponseDatamodel> getOrderDetailsCall = apiInterface
            .getOrderDetails(Singleton.getInstance().getTOKEN(), Singleton.getInstance().getORDERID());

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
        Log.d("Aslam", t.getMessage());
      }
    });


    return OrderDetailsDataModelMutableLiveData;
  }

  //Accept order API

  public MutableLiveData<AcceptOrderResponseDataModel> getAcceptOrder() {

    MutableLiveData<AcceptOrderResponseDataModel> acceptOrderDataModelMutableLiveData = new MutableLiveData<AcceptOrderResponseDataModel>();

    Call<AcceptOrderResponseDataModel> orderAcceptCall = apiInterface
            .acceptOrderCall(Singleton.getInstance().getTOKEN(), Singleton.getInstance().getORDERID());

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
            .startOrderCall(Singleton.getInstance().getTOKEN(), Singleton.getInstance().getORDERID());

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
            .verifyOTPCall(Singleton.getInstance().getTOKEN(), Singleton.getInstance().getORDERID(),Singleton.getInstance().getORDERITEMID(),orderItemOTPVerifyModel);

    verifyOTPcall.enqueue(new Callback<OtpVerifyResponseDataModel>() {
      @Override
      public void onResponse(Call<OtpVerifyResponseDataModel> call,
                             Response<OtpVerifyResponseDataModel> response) {

        if (response.isSuccessful()) {
          verifyOtpDataModelMutableLiveData.postValue(response.body());
          Log.d("Aslam", response.body().toString());
        } else {
          Log.d("Aslam", response.errorBody().toString());
          verifyOtpDataModelMutableLiveData.postValue(response.body());
        }

      }

      @Override
      public void onFailure(Call<OtpVerifyResponseDataModel> call, Throwable t) {
        Log.d("Aslam", t.getMessage());
      }
    });


    return verifyOtpDataModelMutableLiveData;
  }

  //Complete order call

  public MutableLiveData<CompleteOrderPointResponseDataModel> completeOrderData(CompleteOrderAPIModel completeOrderAPIModel) {

    MutableLiveData<CompleteOrderPointResponseDataModel> completeOrderpointDataModelMutableLiveData = new MutableLiveData<CompleteOrderPointResponseDataModel>();

    Call<CompleteOrderPointResponseDataModel> completeOrderCall = apiInterface
            .completeOrderCall(Singleton.getInstance().getTOKEN(), Singleton.getInstance().getORDERID(),Singleton.getInstance().getORDERITEMID(),completeOrderAPIModel);

    completeOrderCall.enqueue(new Callback<CompleteOrderPointResponseDataModel>() {
      @Override
      public void onResponse(Call<CompleteOrderPointResponseDataModel> call,
                             Response<CompleteOrderPointResponseDataModel> response) {

        if (response.isSuccessful()) {
          completeOrderpointDataModelMutableLiveData.postValue(response.body());
          Log.d("Aslam", response.body().toString());
        } else {
          Log.d("Aslam", response.errorBody().toString());
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

    Call<AcceptPaymentResponseModel> acceptPaymentByProviderAPI = apiInterface.acceptPaymentCall(Singleton.getInstance().getTOKEN(),acceptPaymentAPIModel);

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



}
