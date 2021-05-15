package com.example.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherforecast.databasehelper.DBAccess;
import com.example.weatherforecast.model.City;
import com.example.weatherforecast.model.Coord;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    AutoCompleteTextView autoCity;
    ArrayList<City> dataArr = new ArrayList<>();
    ArrayAdapter<City> newsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isOnline()){
            setContentView(R.layout.activity_disconnect);
        } else {
            DBAccess dbAccess = DBAccess.getInstance(getApplicationContext());
            dbAccess.open();
            setContentView(R.layout.activity_search);
            autoCity = findViewById(R.id.atv_city);
            autoCity.setDropDownBackgroundResource(android.R.color.white);
            autoCity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String c = autoCity.getText().toString();
                    dataArr = dbAccess.getCityList(c);
                    newsAdapter = new ArrayAdapter<City>(SearchActivity.this, android.R.layout.simple_dropdown_item_1line, dataArr);
                    autoCity.setAdapter(newsAdapter);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            autoCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Lấy tọa độ lon, lat
                    String c = autoCity.getText().toString();
                    String[] s = c.split(",");
                    Coord coord = dbAccess.getCoordByCityName(s[0]);
                    boolean save = dbAccess.saveLayoutData(String.valueOf(coord.getLat()), String.valueOf(coord.getLon()));
                    if (save) {
                        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                        intent.putExtra("lat", coord.getLat());
                        intent.putExtra("lon", coord.getLon());
                        dbAccess.close();
                        setResult(MainActivity.RECEIVE_CODE, intent);
                        finish();
                    } else
                        Toast.makeText(SearchActivity.this, "Đã chọn trùng thành phố rồi!!!", Toast.LENGTH_LONG);
                }
            });
        }
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

}