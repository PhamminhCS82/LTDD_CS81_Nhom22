package com.example.weatherforecast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    LinearLayout addCity;
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

        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }


    //Anh xa
    public void getView() {
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
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if(response.code() == 200) {
                    WeatherResponse weatherResponse = response.body();
                    assert weatherResponse != null;
                    double temp = weatherResponse.getMain().getTemp();
                    tvTemp.setText(Double.toString(temp) + '°');
                    tvCity.setText(weatherResponse.getName());
                    Picasso.get().load(new StringBuilder("http://openweathermap.org/img/wn/")
                            .append(weatherResponse.getWeather().get(0).getIcon())
                    .append("@2x.png").toString()).into(imgWeatherIcon);
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
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
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}