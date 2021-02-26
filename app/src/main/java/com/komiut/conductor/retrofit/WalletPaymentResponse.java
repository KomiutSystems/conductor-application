package com.komiut.conductor.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletPaymentResponse {

    @SerializedName("serial")
    @Expose
    private String serial;
    @SerializedName("balance")
    @Expose
    private String balance;

    public String getSuccess() {
        return serial;
    }

    public void setSuccess(String success) {
        this.serial = success;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
