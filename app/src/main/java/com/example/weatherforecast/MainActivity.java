package com.example.weatherforecast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    LinearLayout addCity, layoutList;
    Toolbar toolbar;
    Button addLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addCity = findViewById(R.id.addCity);
        toolbar = findViewById(R.id.toolBar);
        
        addLayout = findViewById(R.id.add);
        layoutList = findViewById(R.id.layout_container);

        setSupportActionBar(toolbar);

        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        addLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v = getLayoutInflater().inflate(R.layout.layout_add_city, null);

                TextView nhietDo = (TextView) v.findViewById(R.id.nhietDo);
                TextView thanhPho = (TextView) v.findViewById(R.id.thanhPho);
                ImageView iconThoiTiet = (ImageView) v.findViewById(R.id.iconThoiTiet);

                nhietDo.setText("160°");
                thanhPho.setText("Hà Nội");
                iconThoiTiet.setImageResource(R.drawable.rain);

                layoutList.addView(v);
            }
        });

    }

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
}