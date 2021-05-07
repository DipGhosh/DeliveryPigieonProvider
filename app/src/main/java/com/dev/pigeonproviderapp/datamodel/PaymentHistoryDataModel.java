package com.dev.pigeonproviderapp.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentHistoryDataModel {

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

        @SerializedName("paymentHistory")
        @Expose
        private List<PaymentHistory> paymentHistory = null;
        @SerializedName("earningHistory")
        @Expose
        private List<EarningHistory> earningHistory = null;
        @SerializedName("newbonusHistory")
        @Expose
        private List<NewbonusHistory> newbonusHistory = null;
        @SerializedName("totalPayment")
        @Expose
        private Double totalPayment;
        @SerializedName("totalEarning")
        @Expose
        private Integer totalEarning;
        @SerializedName("bonusHistory")
        @Expose
        private List<BonusHistory> bonusHistory = null;

        public List<PaymentHistory> getPaymentHistory() {
            return paymentHistory;
        }

        public void setPaymentHistory(List<PaymentHistory> paymentHistory) {
            this.paymentHistory = paymentHistory;
        }

        public List<EarningHistory> getEarningHistory() {
            return earningHistory;
        }

        public void setEarningHistory(List<EarningHistory> earningHistory) {
            this.earningHistory = earningHistory;
        }

        public Double getTotalPayment() {
            return totalPayment;
        }

        public void setTotalPayment(Double totalPayment) {
            this.totalPayment = totalPayment;
        }

        public Integer getTotalEarning() {
            return totalEarning;
        }

        public void setTotalEarning(Integer totalEarning) {
            this.totalEarning = totalEarning;
        }
        public List<BonusHistory> getBonusHistory() {
            return bonusHistory;
        }

        public void setBonusHistory(List<BonusHistory> bonusHistory) {
            this.bonusHistory = bonusHistory;
        }
        public List<NewbonusHistory> getNewbonusHistory() {
            return newbonusHistory;
        }

        public void setNewbonusHistory(List<NewbonusHistory> newbonusHistory) {
            this.newbonusHistory = newbonusHistory;
        }

    }

    public class EarningHistory {

        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("paymentType")
        @Expose
        private String paymentType;
        @SerializedName("date")
        @Expose
        private String date;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(String paymentType) {
            this.paymentType = paymentType;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

    }

    public class PaymentHistory {

        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("paymentType")
        @Expose
        private String paymentType;
        @SerializedName("date")
        @Expose
        private String date;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(String paymentType) {
            this.paymentType = paymentType;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

    }

    public class BonusHistory {

        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("paymentType")
        @Expose
        private String paymentType;
        @SerializedName("date")
        @Expose
        private String date;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(String paymentType) {
            this.paymentType = paymentType;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

    }

    public class NewbonusHistory {

        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("note")
        @Expose
        private String note;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

    }
}
