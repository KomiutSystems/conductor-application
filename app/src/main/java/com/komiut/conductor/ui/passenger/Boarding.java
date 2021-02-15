package com.komiut.conductor.ui.passenger;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Boarding {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("departure")
    @Expose
    private String departure;
    @SerializedName("destination")
    @Expose
    private String destination;
    @SerializedName("latitude_from")
    @Expose
    private Double latitudeFrom;
    @SerializedName("longitude_from")
    @Expose
    private Double longitudeFrom;
    @SerializedName("fare")
    @Expose
    private Integer fare;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("latitude")
    @Expose
    private Object latitude;
    @SerializedName("longitude")
    @Expose
    private Object longitude;
    @SerializedName("depart")
    @Expose
    private Object depart;
    @SerializedName("dest")
    @Expose
    private Object dest;
    @SerializedName("name")
    @Expose
    private Object name;
    @SerializedName("phone")
    @Expose
    private Object phone;

    public String getBoarded() {
        return boarded;
    }

    public void setBoarded(String boarded) {
        this.boarded = boarded;
    }

    @SerializedName("boarded")
    @Expose
    private String boarded;

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    @SerializedName("plate")
    @Expose
    private String plate;

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Double getLatitudeFrom() {
        return latitudeFrom;
    }

    public void setLatitudeFrom(Double latitudeFrom) {
        this.latitudeFrom = latitudeFrom;
    }

    public Double getLongitudeFrom() {
        return longitudeFrom;
    }

    public void setLongitudeFrom(Double longitudeFrom) {
        this.longitudeFrom = longitudeFrom;
    }

    public Integer getFare() {
        return fare;
    }

    public void setFare(Integer fare) {
        this.fare = fare;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Object getLatitude() {
        return latitude;
    }

    public void setLatitude(Object latitude) {
        this.latitude = latitude;
    }

    public Object getLongitude() {
        return longitude;
    }

    public void setLongitude(Object longitude) {
        this.longitude = longitude;
    }

    public Object getDepart() {
        return depart;
    }

    public void setDepart(Object depart) {
        this.depart = depart;
    }

    public Object getDest() {
        return dest;
    }

    public void setDest(Object dest) {
        this.dest = dest;
    }

}