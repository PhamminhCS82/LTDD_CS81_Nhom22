package com.example.weatherforecast.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Hourly {
    @SerializedName("dt")
    private int dt;
    @SerializedName("temp")
    private double temp;
    @SerializedName("feels_like")
    private double feels_like;
    @SerializedName("pressure")
    private int pressure;
    @SerializedName("humidity")
    private int humidity;
    @SerializedName("dew_point")
    private double dew_point;
    @SerializedName("uvi")
    private double uvi;
    @SerializedName("clouds")
    private int clouds;
    @SerializedName("visibility")
    private int visibility;
    @SerializedName("wind_speed")
    private double wind_speed;
    @SerializedName("wind-deg")
    private int wind_deg;
    @SerializedName("wind_gust")
    private double wind_gust;
    @SerializedName("weather")
    private List<Weather> weather;
    @SerializedName("pop")
    private double pop;
    @SerializedName("rain")
    private Rain rain;

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(double feels_like) {
        this.feels_like = feels_like;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getDew_point() {
        return dew_point;
    }

    public void setDew_point(double dew_point) {
        this.dew_point = dew_point;
    }

    public double getUvi() {
        return uvi;
    }

    public void setUvi(double uvi) {
        this.uvi = uvi;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public double getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(double wind_speed) {
        this.wind_speed = wind_speed;
    }

    public int getWind_deg() {
        return wind_deg;
    }

    public void setWind_deg(int wind_deg) {
        this.wind_deg = wind_deg;
    }

    public double getWind_gust() {
        return wind_gust;
    }

    public void setWind_gust(double wind_gust) {
        this.wind_gust = wind_gust;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public double getPop() {
        return pop;
    }

    public void setPop(double pop) {
        this.pop = pop;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }
}
