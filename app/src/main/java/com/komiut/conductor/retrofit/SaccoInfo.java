package com.komiut.conductor.retrofit;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SaccoInfo implements Serializable {

    @SerializedName("sacco")
    @Expose
    private String sacco;
    @SerializedName("customer_care_no")
    @Expose
    private String customerCareNo;
    @SerializedName("sacco_motto")
    @Expose
    private String saccoMotto;
    @SerializedName("logo")
    @Expose
    private String logo;

    public String getSacco() {
        return sacco;
    }

    public void setSacco(String sacco) {
        this.sacco = sacco;
    }

    public String getCustomerCareNo() {
        return customerCareNo;
    }

    public void setCustomerCareNo(String customerCareNo) {
        this.customerCareNo = customerCareNo;
    }

    public String getSaccoMotto() {
        return saccoMotto;
    }

    public void setSaccoMotto(String saccoMotto) {
        this.saccoMotto = saccoMotto;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

}
