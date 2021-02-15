package com.komiut.conductor.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Passenger implements Serializable {

    @SerializedName("pick_up")
    @Expose
    private String pickUp;
    @SerializedName("dest")
    @Expose
    private String dest;
    @SerializedName("status")
    @Expose
    private String status;

    public Passenger(String pickUp, String dest, String status) {
        this.pickUp = pickUp;
        this.dest = dest;
        this.status = status;
    }

    public String getPickUp() {
        return pickUp;
    }

    public void setPickUp(String pickUp) {
        this.pickUp = pickUp;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
