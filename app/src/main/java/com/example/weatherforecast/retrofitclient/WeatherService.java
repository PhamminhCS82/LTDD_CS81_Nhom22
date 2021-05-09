package com.example.weatherforecast.retrofitclient;

import com.example.weatherforecast.model.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("data/2.5/weather?")
    Call<WeatherResponse> getWeatherByLatLon(@Query("lat") String lat,
                                             @Query("lon") String lon,
                                             @Query("appid") String appId,
                                             @Query("units") String unit);

}
