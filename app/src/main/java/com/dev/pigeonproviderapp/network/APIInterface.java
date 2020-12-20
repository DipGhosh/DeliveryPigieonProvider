package com.dev.pigeonproviderapp.network;

import com.dev.pigeonproviderapp.datamodel.RegisterDataModel;
import com.dev.pigeonproviderapp.httpRequest.RegisterBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIInterface {

  @POST("/api/register")
  Call<RegisterDataModel> registerAPI(@Body RegisterBody registerBody);

}
