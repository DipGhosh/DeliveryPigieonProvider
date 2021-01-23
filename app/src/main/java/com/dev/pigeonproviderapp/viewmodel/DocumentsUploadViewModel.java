package com.dev.pigeonproviderapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dev.pigeonproviderapp.datamodel.AddDocumentResponseModel;
import com.dev.pigeonproviderapp.datamodel.UpdateProfilePIctureDataModel;
import com.dev.pigeonproviderapp.datamodel.UploadDocumentImageResponseModel;
import com.dev.pigeonproviderapp.datamodel.VerifyOtpResponseDataModel;
import com.dev.pigeonproviderapp.httpRequest.AddDocumentAPIModel;
import com.dev.pigeonproviderapp.httpRequest.VerifyOtpAPIModel;
import com.dev.pigeonproviderapp.repo.NetworkCall;

import okhttp3.MultipartBody;
import retrofit2.http.Part;

public class DocumentsUploadViewModel extends ViewModel {

    MutableLiveData<UploadDocumentImageResponseModel> documentsPIctureUploadDataModelMutableLiveData;
    MutableLiveData<AddDocumentResponseModel> addDocumentDataModelMutableLiveData;

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public LiveData<UploadDocumentImageResponseModel> uploadDocumentPicture(
            @Part MultipartBody.Part part) {

        /*if (documentsPIctureUploadDataModelMutableLiveData == null) {
            documentsPIctureUploadDataModelMutableLiveData = new NetworkCall().uploadDocumentPicture(part);
        }*/
        documentsPIctureUploadDataModelMutableLiveData = new NetworkCall().uploadDocumentPicture(part);
        return documentsPIctureUploadDataModelMutableLiveData;
    }

    public LiveData<AddDocumentResponseModel> sendaddDocumentImage(AddDocumentAPIModel addDocumentAPIModel) {

       /* if (addDocumentDataModelMutableLiveData == null) {
            addDocumentDataModelMutableLiveData = new NetworkCall().callAddDocuments(addDocumentAPIModel);
        }*/
        addDocumentDataModelMutableLiveData = new NetworkCall().callAddDocuments(addDocumentAPIModel);
        return addDocumentDataModelMutableLiveData;
    }
}
