package com.dev.pigeonproviderapp.network;

import com.dev.pigeonproviderapp.datamodel.OTPSendResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.VerifyOtpResponseDataModel;
import com.dev.pigeonproviderapp.httpRequest.OTPSendAPIModel;
import com.dev.pigeonproviderapp.httpRequest.VerifyOtpAPIModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIInterface {

  @Headers({"Accept: application/json"})
  @POST("/api/otp/send")
  Call<OTPSendResponseDataModel> OTPSendAPICall(@Body OTPSendAPIModel registerBody);
  @Headers({"Accept: application/json"})
  @POST("/api/otp/verify")
  Call<VerifyOtpResponseDataModel> VerifyOtpAPICall(@Body VerifyOtpAPIModel verifyOtpBody);
}
