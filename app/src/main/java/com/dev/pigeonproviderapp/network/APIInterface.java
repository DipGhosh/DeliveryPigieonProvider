package com.dev.pigeonproviderapp.network;

import com.dev.pigeonproviderapp.datamodel.OTPSendDataModel;
import com.dev.pigeonproviderapp.httpRequest.OTPSendAPI;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIInterface {

  @Headers({"Accept: application/json"})
  @POST("/api/otp/send")
  Call<OTPSendDataModel> OTPSendAPICall(@Body OTPSendAPI registerBody);

}
