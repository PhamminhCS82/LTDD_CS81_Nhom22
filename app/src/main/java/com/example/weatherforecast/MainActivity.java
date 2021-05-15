package com.example.weatherforecast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;


import android.os.Looper;
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
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.weatherforecast.common.Common;
import com.example.weatherforecast.databasehelper.DBAccess;
import com.example.weatherforecast.model.Coord;
import com.example.weatherforecast.model.WeatherResponse;
import com.example.weatherforecast.retrofitclient.RetrofitClient;
import com.example.weatherforecast.retrofitclient.WeatherService;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import com.squareup.picasso.Picasso;


import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    public static final int SEND_CODE = 1;
    public static final int RECEIVE_CODE = 2;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    LinearLayout addCity, layoutList, layoutDefault;
    Toolbar toolbar;
    TextView tvCity, tvTemp, tvCityDefault, tvTempDefault;
    ImageView imgWeatherIcon, imgWeatherDefault;
    Button addLayout;
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


        getLocation();
        getWeatherDefaultInfo(Common.latitude, Common.longitude);
        generateDefaultLayout();
        addCity.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivityForResult(intent, SEND_CODE);
        });

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.floating_menu, menu);
        views = v;
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.del:
                dbAccess.open();
                Coord c = arrayList.get((int) views.getTag() - 1);

                boolean a = dbAccess.deleteCityFavorite(String.valueOf(c.getLat()), String.valueOf(c.getLon()));
                System.out.println(views.getTag().toString() + a);
                layoutList.removeView(views);
                dbAccess.close();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void getView() {
        tvCityDefault = findViewById(R.id.tv_cityDefault);
        tvTempDefault = findViewById(R.id.tv_temperatureDefault);
        layoutDefault = findViewById(R.id.layoutDefault);
        imgWeatherDefault = findViewById(R.id.img_weatherDefault);
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
        if (requestCode == SEND_CODE) {
            if (resultCode == RECEIVE_CODE) {
                String lat = String.valueOf(data.getDoubleExtra("lat", 10.762622));
                String lon = String.valueOf(data.getDoubleExtra("lon", 106.660172));
                getWeatherInformation(lat, lon);
            }
        }
    }

    private void generateDefaultLayout() {
        dbAccess.open();
        ArrayList<Coord> c = dbAccess.getCoordFromSaveTable();
        for (int i = 0; i < c.size(); i++)
            getWeatherInformation(String.valueOf(c.get(i).getLat()), String.valueOf(c.get(i).getLon()));
        dbAccess.close();
    }

    private void getWeatherDefaultInfo(String lat, String lon) {
        Retrofit retrofit = RetrofitClient.getInstance();
        WeatherService weatherService = retrofit.create(WeatherService.class);
        Call<WeatherResponse> call = weatherService.getWeatherByLatLon(lat, lon, Common.API_KEY_ID, "metric");
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.code() == 200) {
                    WeatherResponse weatherResponse = response.body();
                    assert weatherResponse != null;
                    int temp = (int) Math.round(weatherResponse.getMain().getTemp());
                    String temperatureString = temp + "°C";
                    String cityName = weatherResponse.getName();
                    Coord coord = new Coord(weatherResponse.getCoord().getLon(), weatherResponse.getCoord().getLat());
                    tvCityDefault.setText(cityName);
                    tvTempDefault.setText(temperatureString);
                    Picasso.get().load("http://openweathermap.org/img/wn/" +
                            weatherResponse.getWeather().get(0).getIcon() +
                            "@2x.png").into(imgWeatherDefault);
                    layoutDefault.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                            intent.putExtra("cityName", cityName);
                            intent.putExtra("lon", coord.getLon());
                            intent.putExtra("lat", coord.getLat());
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, Throwable t) {
                tvCity.setText(t.getMessage());
            }
        });

    }

    private void getWeatherInformation(String lat, String lon) {
        Retrofit retrofit = RetrofitClient.getInstance();
        WeatherService weatherService = retrofit.create(WeatherService.class);
        Call<WeatherResponse> call = weatherService.getWeatherByLatLon(lat, lon, Common.API_KEY_ID, "metric");
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.code() == 200) {
                    WeatherResponse weatherResponse = response.body();
                    assert weatherResponse != null;
                    double temp = weatherResponse.getMain().getTemp();
                    String temperatureString = Double.toString(temp) + "°C";
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
            case R.id.current_location:
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_CODE_LOCATION_PERMISSION);
                } else {
                    getLocation();
                }

        }
        return super.onOptionsItemSelected(item);
    }

    private void getLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.getFusedLocationProviderClient(MainActivity.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(@NonNull LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(MainActivity.this)
                                .removeLocationUpdates(this);
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            int latestLocationIndex = locationResult.getLocations().size() - 1;
                            double latitude =
                                    locationResult.getLocations().get(latestLocationIndex).getLatitude();
                            double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                            Common.latitude = String.valueOf(latitude);
                            Common.longitude = String.valueOf(longitude);
                            getWeatherDefaultInfo(Common.latitude, Common.longitude);
                        }
                    }
                }, Looper.getMainLooper());
    }


    public void generateLayout(String temperatureString, String cityName, String path, Coord coord) {
        final View view = getLayoutInflater().inflate(R.layout.layout_add_city, null);
        TextView nhietDo = view.findViewById(R.id.tv_temperature);
        TextView thanhPho = view.findViewById(R.id.tv_city);
        ImageView iconThoiTiet = view.findViewById(R.id.img_weatherIcon);
        LinearLayout layout = view.findViewById(R.id.layout);
        arrayList.add(coord);
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
        view.setTag(arrayList.size());
        layoutList.addView(view);
    }
}