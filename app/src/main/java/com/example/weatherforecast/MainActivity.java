package com.example.weatherforecast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.weatherforecast.common.Common;
import com.example.weatherforecast.model.WeatherResponse;
import com.example.weatherforecast.retrofitclient.RetrofitClient;
import com.example.weatherforecast.retrofitclient.WeatherService;
import com.squareup.picasso.Picasso;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    LinearLayout addCity,test;
    Toolbar toolbar;
    TextView tvTemp, tvCity;
    ImageView imgWeatherIcon;
    String lat = "33.44";
    String lon = "-94.04";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getView();
        getWeatherInformation();

        setSupportActionBar(toolbar);

        addCity.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        });
        test.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            startActivity(intent);
        });
    }


    //Anh xa
    public void getView() {
        test = findViewById(R.id.test);
        addCity = findViewById(R.id.addCity);
        toolbar = findViewById(R.id.toolBar);
        tvCity = findViewById(R.id.tv_city);
        tvTemp = findViewById(R.id.tv_template);
        imgWeatherIcon = findViewById(R.id.img_weatherIcon);
    }

    private void getWeatherInformation(){
        Retrofit retrofit = RetrofitClient.getInstance();
        WeatherService weatherService = retrofit.create(WeatherService.class);
        Call<WeatherResponse> call = weatherService.getWeatherByLatLon(lat,lon,Common.API_KEY_ID,"metric");
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse>  call,@NonNull Response<WeatherResponse> response) {
                if(response.code() == 200) {
                    WeatherResponse weatherResponse = response.body();
                    assert weatherResponse != null;
                    double temp = weatherResponse.getMain().getTemp();
                    String tempString = Double.toString(temp) + '°';
                    tvTemp.setText(tempString);
                    tvCity.setText(weatherResponse.getName());
                    Picasso.get().load("http://openweathermap.org/img/wn/" +
                            weatherResponse.getWeather().get(0).getIcon() +
                            "@2x.png").into(imgWeatherIcon);
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, Throwable t) {
                tvCity.setText(t.getMessage());
            }
        });
    }

    //Option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Toast.makeText(MainActivity.this, "Thông tin ứng dụng", Toast.LENGTH_LONG).show();
                return true;
            case R.id.contact:
                Toast.makeText(MainActivity.this, "Liên hệ với chúng tôi", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}