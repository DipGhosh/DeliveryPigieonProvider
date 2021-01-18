package com.dev.pigeonproviderapp.network;

import com.dev.pigeonproviderapp.datamodel.OTPSendResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.ProfileUpdateResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.UpdateProfilePIctureDataModel;
import com.dev.pigeonproviderapp.datamodel.VerifyOtpResponseDataModel;
import com.dev.pigeonproviderapp.httpRequest.OTPSendAPIModel;
import com.dev.pigeonproviderapp.httpRequest.ProfileUpdateAPI;
import com.dev.pigeonproviderapp.httpRequest.VerifyOtpAPIModel;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

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

}
