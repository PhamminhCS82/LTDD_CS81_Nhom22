package com.example.weatherforecast.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Daily {
    @SerializedName("dt")
    public int dt;
    @SerializedName("sunrise")
    public int sunrise;
    @SerializedName("sunset")
    public int sunset;
    @SerializedName("moonrise")
    public int moonrise;
    @SerializedName("moonset")
    public int moonset;
    @SerializedName("moon_phase")
    public double moon_phase;
    @SerializedName("temp")
    public Temp temp;
    @SerializedName("feels_like")
    public FeelsLike feels_like;
    @SerializedName("pressure")
    public int pressure;
    @SerializedName("humidity")
    public int humidity;
    @SerializedName("dew_point")
    public double dew_point;
    @SerializedName("wind_speed")
    public double wind_speed;
    @SerializedName("wind_deg")
    public int wind_deg;
    @SerializedName("wind_gust")
    public double wind_gust;
    @SerializedName("weather")
    public List<Weather> weather;
    @SerializedName("clouds")
    public int clouds;
    @SerializedName("pop")
    public double pop;
    @SerializedName("rain")
    public double rain;
    @SerializedName("uvi")
    public double uvi;
}
