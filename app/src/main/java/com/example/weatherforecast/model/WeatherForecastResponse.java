package com.example.weatherforecast.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherForecastResponse {
    @SerializedName("lon")
    private double lon;
    @SerializedName("lat")
    private double lat;
    @SerializedName("timezone")
    private String timezone;
    @SerializedName("timezone_offset")
    private int timezone_offset;
    @SerializedName("current")
    private Current current;
    @SerializedName("hourly")
    private List<Hourly> hourly;
    @SerializedName("daily")
    private List<Daily> daily;

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

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getTimezone_offset() {
        return timezone_offset;
    }

    public void setTimezone_offset(int timezone_offset) {
        this.timezone_offset = timezone_offset;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public List<Hourly> getHourly() {
        return hourly;
    }

    public void setHourly(List<Hourly> hourly) {
        this.hourly = hourly;
    }

    public List<Daily> getDaily() {
        return daily;
    }

    public void setDaily(List<Daily> daily) {
        this.daily = daily;
    }
}
