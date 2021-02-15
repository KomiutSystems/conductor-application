package com.komiut.conductor.model;

import java.io.Serializable;

public class PassengerMapCoordinates implements Serializable {
    double latitutde,longitude;

    public double getLatitutde() {
        return latitutde;
    }

    public void setLatitutde(double latitutde) {
        this.latitutde = latitutde;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
