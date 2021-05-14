package com.example.weatherforecast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.weatherforecast.common.Common;
import com.example.weatherforecast.databasehelper.DBAccess;
import com.example.weatherforecast.model.Coord;
import com.example.weatherforecast.model.WeatherResponse;
import com.example.weatherforecast.retrofitclient.RetrofitClient;
import com.example.weatherforecast.retrofitclient.WeatherService;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    public static final int SEND_CODE = 1;
    public static final int RECEIVE_CODE = 2;
    LinearLayout addCity, layoutList;
    Toolbar toolbar;
    TextView tvCity, tvTemp;
    ImageView imgWeatherIcon;
    Button addLayout;
    Boolean kiemTra = true;
    ScrollView scrollView;
    View views;
    ArrayList<Coord> arrayList;
    DBAccess dbAccess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getView();
        dbAccess = DBAccess.getInstance(getApplicationContext());
        arrayList = new ArrayList<>();
        setSupportActionBar(toolbar);

        generateDefaultLayout();
        addCity.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivityForResult(intent, SEND_CODE);
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.floating_menu, menu);
        views = v;
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.del:
                dbAccess.open();
                Coord c = arrayList.get((int)views.getTag() - 1);

                boolean a = dbAccess.deleteCityFavorite(String.valueOf(c.getLat()), String.valueOf(c.getLon()));
                System.out.println(views.getTag().toString() + a);
                layoutList.removeView(views);
                dbAccess.close();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void getView() {
        scrollView = findViewById(R.id.scr_view);
        addCity = findViewById(R.id.addCity);
        toolbar = findViewById(R.id.toolBar);
        addLayout = findViewById(R.id.add);
        layoutList = findViewById(R.id.layout_container);
        tvCity = findViewById(R.id.tv_city);
        tvTemp = findViewById(R.id.tv_temperature);
        imgWeatherIcon = findViewById(R.id.img_weatherIcon);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SEND_CODE) {
            if(resultCode == RECEIVE_CODE) {
                String lat = String.valueOf(data.getDoubleExtra("lat", 10.762622));
                String lon = String.valueOf(data.getDoubleExtra("lon", 106.660172));
                getWeatherInformation(lat, lon);
            }
        }
    }

    private void generateDefaultLayout() {
        dbAccess.open();
        ArrayList<Coord> c = dbAccess.getCoordFromSaveTable();
        for(int i = 0; i < c.size(); i++)
            getWeatherInformation(String.valueOf(c.get(i).getLat()),String.valueOf(c.get(i).getLon()));
        dbAccess.close();
    }


    private void getWeatherInformation(String lat, String lon){
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
                    String temperatureString = Double.toString(temp) + '°';
                    String cityName = weatherResponse.getName();
                    String path = "http://openweathermap.org/img/wn/" +
                            weatherResponse.getWeather().get(0).getIcon() +
                            "@2x.png";
                    Coord coo = new Coord(Double.valueOf(lon), Double.valueOf(lat));
                    generateLayout(temperatureString, cityName, path, coo);

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
                Intent aboutActivity = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(aboutActivity);
                return true;
            case R.id.contact:
                Intent contactActivity = new Intent(MainActivity.this, ContactActivity.class);
                startActivity(contactActivity);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void generateLayout(String temperatureString, String cityName, String path, Coord coord) {
        final View view = getLayoutInflater().inflate(R.layout.layout_add_city, null);
        TextView nhietDo = view.findViewById(R.id.tv_temperature);
        TextView thanhPho = view.findViewById(R.id.tv_city);
        ImageView iconThoiTiet = view.findViewById(R.id.img_weatherIcon);
        LinearLayout layout = view.findViewById(R.id.layout);
        arrayList.add(coord);
        if(!kiemTra)
            registerForContextMenu(view);
        nhietDo.setText(temperatureString);
        thanhPho.setText(cityName);
        Picasso.get().load(path).into(iconThoiTiet);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("cityName", cityName);
                intent.putExtra("lon", coord.getLon());
                intent.putExtra("lat", coord.getLat());
                startActivity(intent);
            }
        });
        kiemTra = false;
        view.setTag(arrayList.size());
        layoutList.addView(view);
    }
}