package com.example.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.weatherforecast.databasehelper.DBAccess;
import com.example.weatherforecast.model.City;
import com.example.weatherforecast.model.Coord;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    TextView tvTest;
    AutoCompleteTextView autoCity;
    ArrayList<City> dataArr = new ArrayList<>();
    ArrayAdapter<City> newsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DBAccess dbAccess = DBAccess.getInstance(getApplicationContext());
        dbAccess.open();
        super.onCreate(savedInstanceState);
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

                //Tạo ra biến Intent để truyền lon và lat sangActiviy
                /*Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                intent.putExtra("lat",coord.getLat());
                intent.putExtra("lon", coord.getLon());
                startActivityForResult(intent, MY_REQUEST_CODE);
                finish();*/

                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                intent.putExtra("lat",coord.getLat());
                intent.putExtra("lon", coord.getLon());
                setResult(MainActivity.RECEIVE_CODE, intent);
                finish();
            }
        });
    }

}