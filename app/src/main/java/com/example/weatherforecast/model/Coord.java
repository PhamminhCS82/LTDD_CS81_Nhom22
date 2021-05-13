package com.example.weatherforecast.model;

import com.google.gson.annotations.SerializedName;


public class Coord {
    @SerializedName("lon")
    private double lon;
    @SerializedName("lat")
    private double lat;
    public Coord(){

    }
    public Coord(double lon, double lat){
        this.lon = lon;
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

}
