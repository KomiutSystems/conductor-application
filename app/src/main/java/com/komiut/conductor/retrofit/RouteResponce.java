package com.komiut.conductor.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RouteResponce {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("departure")
    @Expose
    private String departure;
    @SerializedName("destination")
    @Expose
    private String destination;
    @SerializedName("distance_to_queue")
    @Expose
    private Object distanceToQueue;
    @SerializedName("distance")
    @Expose
    private Double distance;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Object getDistanceToQueue() {
        return distanceToQueue;
    }

    public void setDistanceToQueue(Object distanceToQueue) {
        this.distanceToQueue = distanceToQueue;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
