package com.dev.pigeonproviderapp.network;

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
import com.dev.pigeonproviderapp.httpRequest.OrderRatingAPIModel;
import com.dev.pigeonproviderapp.httpRequest.ProfileUpdateAPI;
import com.dev.pigeonproviderapp.httpRequest.VerifyOtpAPIModel;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.QueryName;

public interface APIInterface {

  @Headers({"Accept: application/json"})
  @POST("/api/otp/send")
  Call<OTPSendResponseDataModel> OTPSendAPICall(@Body OTPSendAPIModel registerBody);

  @Headers({"Accept: application/json"})
  @POST("/api/otp/verify")
  Call<VerifyOtpResponseDataModel> VerifyOtpAPICall(@Body VerifyOtpAPIModel verifyOtpBody);

  @Headers({"Accept: application/json"})
  @POST("/api/profile")
  Call<ProfileUpdateResponseDataModel> profileInfouploadAPICall(@Header("Authorization") String authorization, @Body ProfileUpdateAPI profileUpdateAPI);

  @Headers({"Accept: application/json"})
  @PUT("/api/profile/1")
  Call<ProfileUpdateResponseDataModel> updateProfileInfoAPICall(@Header("Authorization") String authorization,
                                       @Body ProfileUpdateAPI profileUpdateAPI);

  @Multipart
  @POST("/api/profile/picture")
  Call<UpdateProfilePIctureDataModel> updateProfilePicture(@Header("Authorization") String authorization,
                                                           @Part MultipartBody.Part profile_picture);

  @Multipart
  @POST("/api/files")
  Call<UploadDocumentImageResponseModel> uploadDocumentImage(@Header("Authorization") String authorization,
                                                              @Part MultipartBody.Part profile_picture);

  @Headers({"Accept: application/json"})
  @POST("/api/documents")
  Call<AddDocumentResponseModel> addDocumentImage(@Header("Authorization") String authorization,
                                                  @Body AddDocumentAPIModel addDocumentAPIModel);

  @GET("/api/me")
  Call<ProfileGetResponseDataModel> getProfile(@Header("Authorization") String authorization);

  @GET("/api/orders")
  Call<ListOrderResponseDataModel> getOrderListCall(@Header("Authorization") String authorization);

  @GET("/api/orders/{id}}")
  Call<OrderDetailsResponseDatamodel> getOrderDetails(@Header("Authorization") String authorization, @Path("id") int id);

  @Headers({"Accept: application/json"})
  @POST("/api/accept_order/{id}}")
  Call<AcceptOrderResponseDataModel> acceptOrderCall(@Header("Authorization") String authorization, @Path("id") int id);

  @Headers({"Accept: application/json"})
  @POST("/api/start_order/{id}}")
  Call<StartOrderResponseDataModel> startOrderCall(@Header("Authorization") String authorization, @Path("id") int id);

  @Headers({"Accept: application/json"})
  @POST("/api/start_drop_point/{id}/{itemid}")
  Call<OtpVerifyResponseDataModel> verifyOTPCall(@Header("Authorization") String authorization, @Path("id") int id, @Path("itemid") int itemid, @Body OrderItemOTPVerifyModel orderItemOTPVerifyModel);

  @Headers({"Accept: application/json"})
  @POST("/api/complete_point/{id}/{itemid}")
  Call<CompleteOrderPointResponseDataModel> completeOrderCall(@Header("Authorization") String authorization, @Path("id") int id, @Path("itemid") int itemid, @Body CompleteOrderAPIModel completeOrderAPIModel);

  @Headers({"Accept: application/json"})
  @POST("/api/payments/accept")
  Call<AcceptPaymentResponseModel> acceptPaymentCall(@Header("Authorization") String authorization,
                                                     @Body AcceptPaymentAPIModel acceptPaymentAPIModel);

  @Headers({"Accept: application/json"})
  @POST("/api/rate_order/{id}")
  Call<AcceptPaymentResponseModel> providerRatingCall(@Header("Authorization") String authorization, @Path("id") int id, @Body OrderRatingAPIModel orderRatingAPIModel);

}


