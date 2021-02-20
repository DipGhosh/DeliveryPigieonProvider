package com.dev.pigeonproviderapp.ActivityAll.AccountSettings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dev.pigeonproviderapp.ActivityAll.ProfileEdit;
import com.dev.pigeonproviderapp.Baseclass.BaseActivity;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.Utility.CommonUtils;
import com.dev.pigeonproviderapp.Utility.UiUtils;
import com.dev.pigeonproviderapp.datamodel.AcceptPaymentResponseModel;
import com.dev.pigeonproviderapp.datamodel.BankDetailsGetModelResponse;
import com.dev.pigeonproviderapp.datamodel.OrderDetailsResponseDatamodel;
import com.dev.pigeonproviderapp.datamodel.ProfileGetResponseDataModel;
import com.dev.pigeonproviderapp.datamodel.ProfileUpdateResponseDataModel;
import com.dev.pigeonproviderapp.httpRequest.BankDetailsSubmitAPIModel;
import com.dev.pigeonproviderapp.httpRequest.ProfileUpdateAPI;
import com.dev.pigeonproviderapp.viewmodel.ProfileViewModel;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AddBankDetails extends BaseActivity implements View.OnClickListener {

    private Activity activity = AddBankDetails.this;

    private ImageView back;
    private EditText bankAccountNumber,bankIfscCode,bankAccountType,bankAccountHolderName;
    private TextView saveBankDetails;
    private ConstraintLayout mainLayout;
    private LinearLayout mainLayoutClick;

    ProfileViewModel profileViewModel;
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_details);

        dialog = UiUtils.showProgress(AddBankDetails.this);

        back = findViewById(R.id.img_back);
        bankAccountNumber=findViewById(R.id.et_accont_number);
        bankIfscCode=findViewById(R.id.et_ifsc_code);
        bankAccountType=findViewById(R.id.et_bank_account_type);
        bankAccountHolderName=findViewById(R.id.et_bank_accountholder_name);
        saveBankDetails = findViewById(R.id.tv_save_bank_details);
        mainLayout=findViewById(R.id.cinstarin_main);
        mainLayoutClick=findViewById(R.id.ll_main);




        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        back.setOnClickListener(this);
        saveBankDetails.setOnClickListener(this);

        callGetBankDetails();

        mainLayoutClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UiUtils.hideSoftKeyBoard(activity,mainLayout);
            }
        });


    }

    @Override
    public void onClick(View v) {

        switch (v.getId() /*to get clicked view id**/) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_save_bank_details:

                CallBankdetailsCreate();
                break;
            default:
                break;
        }
    }

    public void CallBankdetailsCreate() {
        if (isValid()) {

            dialog.show();

            BankDetailsSubmitAPIModel bankDetailsSubmitAPIModel = new BankDetailsSubmitAPIModel();
            bankDetailsSubmitAPIModel.setName(bankAccountHolderName.getText().toString());
            bankDetailsSubmitAPIModel.setAccount_no(bankAccountNumber.getText().toString());
            bankDetailsSubmitAPIModel.setAccount_type(bankAccountType.getText().toString());
            bankDetailsSubmitAPIModel.setIfsc(bankIfscCode.getText().toString());
            bankDetailsSubmitAPIModel.setBank_address("Kolkata");
            bankDetailsSubmitAPIModel.setBank_name("SBI");


            profileViewModel.addBankDetails(bankDetailsSubmitAPIModel).observe(this, new Observer<AcceptPaymentResponseModel>() {
                @Override
                public void onChanged(AcceptPaymentResponseModel bankdetailsresponseDatamodel) {
                    dialog.dismiss();

                    if (bankdetailsresponseDatamodel != null) {
                        if( bankdetailsresponseDatamodel.getStatus()==200)
                        {
                            UiUtils.showAlert(activity,"Add Bank Details Edit",getString(R.string.aleart_add_bankdetails));
                        }
                    }else {
                        UiUtils.showAlert(activity, getString(R.string.app_name), getString(R.string.profile_invalid));
                    }




                }
            });
        }

    }

    public void callGetBankDetails() {

        dialog.show();

        profileViewModel.getBankDetails().observe(this, new Observer<BankDetailsGetModelResponse>() {
            @Override
            public void onChanged(BankDetailsGetModelResponse bankDetailsGetModelResponse) {

                dialog.dismiss();

                if (bankDetailsGetModelResponse.getStatus()==200 && bankDetailsGetModelResponse.getData().size()>0)
                {
                    for (BankDetailsGetModelResponse.Datum data : bankDetailsGetModelResponse.getData())
                    {
                        bankAccountHolderName.setText(data.getNameOnAccount());
                        bankIfscCode.setText(data.getIfsc());
                        bankAccountType.setText(data.getAccountType());
                        bankAccountNumber.setText(""+data.getAccountNo());
                        bankIfscCode.setText(data.getIfsc());
                    }
                }




            }
        });

    }

    private boolean isValid() {
        if (TextUtils.isEmpty(bankAccountNumber.getText().toString())) {
            UiUtils.showToast(this, getString(R.string.aleart_account_number));
            return false;
        } else if (TextUtils.isEmpty(bankIfscCode.getText().toString())) {
            UiUtils.showToast(this, getString(R.string.aleart_ifsc_code));
            return false;
        } else if (TextUtils.isEmpty(bankAccountType.getText().toString())) {
            UiUtils.showToast(this, getString(R.string.aleart_account_type));
            return false;
        }  else if (TextUtils.isEmpty(bankAccountHolderName.getText().toString())) {
            UiUtils.showToast(this, getString(R.string.aleart_accountholder_name));
            return false;
        }  else {
            return true;
        }
    }


}