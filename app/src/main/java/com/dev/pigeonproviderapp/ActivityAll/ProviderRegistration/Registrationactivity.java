package com.dev.pigeonproviderapp.ActivityAll.ProviderRegistration;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import com.dev.pigeonproviderapp.MainActivity;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.activity.BaseActivity;
import com.dev.pigeonproviderapp.network.APIClient;
import com.dev.pigeonproviderapp.network.APIInterface;
import com.dev.pigeonproviderapp.viewmodel.RegisterationActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class Registrationactivity extends BaseActivity {

  Button btnRegistration;
  EditText et_phoneNumber;
  EditText et_otp;
  RegisterationActivityViewModel registerViewModel;
  public static final int MULTIPLE_PERMISSIONS = 10;
  String[] permissions = new String[]{
          android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
          android.Manifest.permission.CAMERA,
          android.Manifest.permission.ACCESS_COARSE_LOCATION,
          Manifest.permission.ACCESS_FINE_LOCATION};


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_registrationactivity);
    checkPermissions();

    btnRegistration = (Button) findViewById(R.id.btn_registration);
    et_phoneNumber = findViewById(R.id.et_phoneNumber);
    et_otp = findViewById(R.id.et_otp);

    registerViewModel = ViewModelProviders.of(this).get(RegisterationActivityViewModel.class);

    btnRegistration.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        /*apiInterface = APIClient.getClient().create(APIInterface.class);
        registerViewModel.getRegisterData(apiInterface);*/
        Intent intent=new Intent(Registrationactivity.this, ProviderDetails.class);
        startActivity(intent);
      }
    });

  }

  private boolean checkPermissions() {
    int result;
    List<String> listPermissionsNeeded = new ArrayList<>();
    for (String p : permissions) {
      result = ContextCompat.checkSelfPermission(Registrationactivity.this, p);
      if (result != PackageManager.PERMISSION_GRANTED) {
        listPermissionsNeeded.add(p);
      }
    }
    if (!listPermissionsNeeded.isEmpty()) {
      ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
      return false;
    }
    return true;
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
    switch (requestCode) {
      case MULTIPLE_PERMISSIONS: {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          // permissions granted.


        }
        return;
      }
    }
  }
}