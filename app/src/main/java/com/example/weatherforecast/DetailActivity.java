package com.example.weatherforecast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


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
    String lat = "33.44";
    String lon = "-94.04";
    PercentageChartView percentageChartView;
    RecyclerView recyclerViewForecast;
    TextView tvTemp, tvCity, tvDateTime, tvHumidity, tvClouds, tvWindSpd, tvUV, tvFeelsLike, tvWindDir, tvWindSpd2;
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

        getWeatherInformation();
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
                    String cityName = weatherResponse.getTimezone();
                    String temp = Math.round(weatherResponse.getCurrent().temp) + "°C";
                    String hud = String.valueOf(weatherResponse.getCurrent().humidity) + '%';
                    String clouds = String.valueOf(weatherResponse.getCurrent().clouds) + '%';
                    String windSpd = weatherResponse.getCurrent().wind_speed + "m/s";
                    String feel = "Feels like: " + Math.round(weatherResponse.getCurrent().feels_like) + "°C";
                    String uv = "UV Index: " + Math.round(weatherResponse.getCurrent().uvi);
                    String windDir = "Direction: " + Common.convertDegreeToCardinalDirection(weatherResponse.getCurrent().wind_deg);
                    String windSpd2 = "Speed: " + Math.round(weatherResponse.getCurrent().wind_speed * 3.6) + " km/h";
                    tvDateTime.setText(Common.convertUnixToDateTime(weatherResponse.getCurrent().dt));
                    tvCity.setText(cityName);
                    tvTemp.setText(temp);
                    tvHumidity.setText(hud);
                    tvWindSpd.setText(windSpd);
                    tvWindSpd2.setText(windSpd2);
                    tvWindDir.setText(windDir);
                    tvClouds.setText(clouds);
                    tvFeelsLike.setText(feel);
                    tvUV.setText(uv);
                    percentageChartView.setProgress(weatherResponse.getCurrent().humidity, true);
                    Picasso.get().load("http://openweathermap.org/img/wn/" +
                            weatherResponse.getCurrent().weather.get(0).getIcon() +
                            "@2x.png").into(imgView);

                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherForecastResponse> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
}