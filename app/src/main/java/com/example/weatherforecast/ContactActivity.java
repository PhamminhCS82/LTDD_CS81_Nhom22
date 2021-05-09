package com.example.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ContactActivity extends AppCompatActivity {
    Button btnMinh, btnKiet, btnNhat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        anhXa();

        btnMinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFacebook("minh.phamquang.9883");
            }
        });

        btnKiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFacebook("quangkiet.tat.1");
            }
        });
        
        btnNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFacebook("oldman216");
            }
        });
    }

    private void anhXa() {
        btnKiet = (Button) findViewById(R.id.btn_kiet);
        btnMinh = (Button) findViewById(R.id.btn_minh);
        btnNhat = (Button) findViewById(R.id.btn_nhat);
    }

    private void goToFacebook(String ten) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + ten));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com"));
            startActivity(intent);
        }
    }
}