package com.example.weatherforecast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.weatherforecast.adapter.DailyWeatherAdapter;
import com.example.weatherforecast.adapter.WeatherForecastAdapter;
import com.example.weatherforecast.common.Common;
import com.example.weatherforecast.model.WeatherForecastResponse;
import com.example.weatherforecast.retrofitclient.RetrofitClient;
import com.example.weatherforecast.retrofitclient.WeatherService;
import com.ramijemli.percentagechartview.PercentageChartView;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailActivity extends AppCompatActivity {
    String nameCT = "Ho Chi Minh City",
            lat = "10.762622",
            lon = "106.660172";
    PercentageChartView percentageChartView;
    RecyclerView recyclerViewForecast;
    TextView tvTemp, tvCity, tvDateTime, tvHumidity,
            tvClouds, tvWindSpd, tvUV, tvFeelsLike,
            tvWindDir, tvWindSpd2, tvWthInf, tvTempDay, tvTempNight;
    ImageView imgView;
    ListView listView;
    WeatherForecastAdapter weatherForecastAdapter;
    DailyWeatherAdapter dailyWeatherAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getView();
        recyclerViewForecast.setHasFixedSize(true);
        recyclerViewForecast.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false));
        recyclerViewForecast.setAdapter(weatherForecastAdapter);
        getCityNameAndLatLon();
        getWeatherInformation();
    }

    private void getCityNameAndLatLon() {
        Intent intent = getIntent();
        nameCT = intent.getStringExtra("cityName");
        lat = String.valueOf(intent.getDoubleExtra("lat", 10.762622));
        lon = String.valueOf(intent.getDoubleExtra("lon", 106.660172));
    }

    private void getView(){
        listView = findViewById(R.id.lv_daily);
        recyclerViewForecast = findViewById(R.id.recycler_forecast);
        imgView = findViewById(R.id.weather_icon);
        tvCity = findViewById(R.id.txt_city);
        tvTemp = findViewById(R.id.tv_temp);
        tvDateTime = findViewById(R.id.tv_current_date_time);
        tvHumidity = findViewById(R.id.tv_current_humidity);
        tvClouds = findViewById(R.id.tv_current_clouds);
        tvWindSpd = findViewById(R.id.tv_current_wind_spd);
        percentageChartView = findViewById(R.id.view_id);
        tvUV = findViewById(R.id.tv_uv);
        tvFeelsLike = findViewById(R.id.tv_feels_like);
        tvWindDir = findViewById(R.id.tv_wind_dir);
        tvWindSpd2 = findViewById(R.id.tv_wind_spd);
        tvWthInf = findViewById(R.id.tv_weather_current_info);
        tvTempDay = findViewById(R.id.tv_day_temp);
        tvTempNight = findViewById(R.id.tv_night_temp);
        listView.setEnabled(false);
    }

    private void getWeatherInformation() {
        Retrofit retrofit = RetrofitClient.getInstance();
        WeatherService weatherService = retrofit.create(WeatherService.class);
        Call<WeatherForecastResponse> call = weatherService.getWeatherForecastByLatLon(lat,lon, Common.API_KEY_ID, "minutely,alerts","metric");
        call.enqueue(new Callback<WeatherForecastResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherForecastResponse>  call, @NonNull Response<WeatherForecastResponse> response) {
                if(response.code() == 200) {
                    WeatherForecastResponse weatherResponse = response.body();
                    assert weatherResponse != null;
                    weatherForecastAdapter = new WeatherForecastAdapter(DetailActivity.this, weatherResponse);
                    recyclerViewForecast.setAdapter(weatherForecastAdapter);
                    dailyWeatherAdapter = new DailyWeatherAdapter(DetailActivity.this, weatherResponse);
                    listView.setAdapter(dailyWeatherAdapter);
                    String cityName = nameCT;
                    String temp = Math.round(weatherResponse.getCurrent().getTemp()) + "°C";
                    String hud = String.valueOf(weatherResponse.getCurrent().getHumidity()) + '%';
                    String clouds = String.valueOf(weatherResponse.getCurrent().getClouds()) + '%';
                    String windSpd = weatherResponse.getCurrent().getWind_speed() + "m/s";
                    String feel = "Feels like: " + Math.round(weatherResponse.getCurrent().getFeels_like()) + "°C";
                    String uv = "UV Index: " + Math.round(weatherResponse.getCurrent().getUvi());
                    String windDir = Common.convertDegreeToCardinalDirection(weatherResponse.getCurrent().getWind_deg());
                    String windSpd2 = Math.round(weatherResponse.getCurrent().getWind_speed() * 3.6) + " km/h";
                    String tempDay = Math.round(weatherResponse.getDaily().get(0).getTemp().getDay()) + "°C";
                    String tempNight = Math.round(weatherResponse.getDaily().get(0).getTemp().getNight()) + "°C";
                    String wthInfo = weatherResponse.getCurrent().getWeather().get(0).getDescription();
                    tvDateTime.setText(Common.convertUnixToDateTime(weatherResponse.getCurrent().getDt()));
                    tvCity.setText(cityName);
                    tvTemp.setText(temp);
                    tvHumidity.setText(hud);
                    tvWindSpd.setText(windSpd);
                    Common.appendColoredText(tvWindSpd2, windSpd2, Color.BLACK);
                    Common.appendColoredText(tvWindDir, windDir, Color.BLACK);
                    Common.appendColoredText(tvTempDay, tempDay, Color.BLACK);
                    Common.appendColoredText(tvTempNight, tempNight, Color.BLACK);
                    tvWthInf.setText(wthInfo);
                    tvClouds.setText(clouds);
                    tvFeelsLike.setText(feel);
                    tvUV.setText(uv);
                    percentageChartView.setProgress(weatherResponse.getCurrent().getHumidity(), true);
                    Picasso.get().load("http://openweathermap.org/img/wn/" +
                            weatherResponse.getCurrent().getWeather().get(0).getIcon() +
                            "@2x.png").into(imgView);

                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherForecastResponse> call, @NonNull Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
}