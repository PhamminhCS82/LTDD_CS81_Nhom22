package com.example.weatherforecast.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Current {
    @SerializedName("dt")
    public int dt;
    @SerializedName("sunrise")
    public int sunrise;
    @SerializedName("sunset")
    public int sunset;
    @SerializedName("temp")
    public double temp;
    @SerializedName("feels_like")
    public double feels_like;
    @SerializedName("pressure")
    public int pressure;
    @SerializedName("humidity")
    public int humidity;
    @SerializedName("dew_point")
    public double dew_point;
    @SerializedName("uvi")
    public double uvi;
    @SerializedName("clouds")
    public int clouds;
    @SerializedName("visibility")
    public int visibility;
    @SerializedName("wind_speed")
    public double wind_speed;
    @SerializedName("wind_deg")
    public int wind_deg;
    @SerializedName("wind_gust")
    public double wind_gust;
    @SerializedName("weather")
    public List<Weather> weather;
}
