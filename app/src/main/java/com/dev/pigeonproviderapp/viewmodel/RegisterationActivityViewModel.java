package com.dev.pigeonproviderapp.viewmodel;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.dev.pigeonproviderapp.datamodel.RegisterDataModel;
import com.dev.pigeonproviderapp.httpRequest.RegisterBody;
import com.dev.pigeonproviderapp.network.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterationActivityViewModel extends ViewModel {

  private MutableLiveData<RegisterDataModel> registerDataModelLiveData;

  @Override
  protected void onCleared() {
    super.onCleared();
  }

  public LiveData<RegisterDataModel> getRegisterData(APIInterface apiInterface) {

    if (registerDataModelLiveData == null) {

      callRegisteration(apiInterface);
    }

    return registerDataModelLiveData;
  }


  public void callRegisteration(APIInterface apiInterface) {

    RegisterBody registerBody = new RegisterBody();
    registerBody.setEmail("mdaslam360@gmail.com");
    registerBody.setName("Md Aslam");
    registerBody.setPhone("7349465100");

    Call<RegisterDataModel> registerAPI = apiInterface.registerAPI(registerBody);

    registerAPI.enqueue(new Callback<RegisterDataModel>() {
      @Override
      public void onResponse(Call<RegisterDataModel> call, Response<RegisterDataModel> response) {

        if (response.isSuccessful()) {
          Log.d("Aslam", response.body().toString());
        } else {
          Log.d("Aslam", response.errorBody().toString());
        }
      }

      @Override
      public void onFailure(Call<RegisterDataModel> call, Throwable t) {

      }
    });

  }

}
