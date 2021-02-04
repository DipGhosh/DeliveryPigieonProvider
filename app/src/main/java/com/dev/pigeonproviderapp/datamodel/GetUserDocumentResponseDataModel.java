package com.dev.pigeonproviderapp.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetUserDocumentResponseDataModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("errorMessage")
    @Expose
    private Object errorMessage;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("errors")
    @Expose
    private List<Object> errors = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public List<Object> getErrors() {
        return errors;
    }

    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }
    public class Data {

        @SerializedName("documents")
        @Expose
        private List<Document> documents = null;

        public List<Document> getDocuments() {
            return documents;
        }

        public void setDocuments(List<Document> documents) {
            this.documents = documents;
        }

    }

    public class Document {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("document_type_id")
        @Expose
        private Integer documentTypeId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("file_name_front")
        @Expose
        private String fileNameFront;
        @SerializedName("file_name_back")
        @Expose
        private Object fileNameBack;
        @SerializedName("status")
        @Expose
        private Object status;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("deleted_at")
        @Expose
        private Object deletedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getDocumentTypeId() {
            return documentTypeId;
        }

        public void setDocumentTypeId(Integer documentTypeId) {
            this.documentTypeId = documentTypeId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFileNameFront() {
            return fileNameFront;
        }

        public void setFileNameFront(String fileNameFront) {
            this.fileNameFront = fileNameFront;
        }

        public Object getFileNameBack() {
            return fileNameBack;
        }

        public void setFileNameBack(Object fileNameBack) {
            this.fileNameBack = fileNameBack;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Object getDeletedAt() {
            return deletedAt;
        }

        public void setDeletedAt(Object deletedAt) {
            this.deletedAt = deletedAt;
        }

    }
}
