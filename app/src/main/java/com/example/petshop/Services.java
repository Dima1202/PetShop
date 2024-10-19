package com.example.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Services extends AppCompatActivity {

    ImageButton btnRate, btnSer, btnBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        btnRate = findViewById(R.id.imgbtnRate);
        btnSer = findViewById(R.id.imgbtnService);
        btnBill = findViewById(R.id.imgbtnBill);

        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Services.this, Rate.class);
                startActivity(intent);
            }
        });

        btnSer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Services.this, PetCare.class);
                startActivity(intent);
            }
        });

        btnBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Services.this, Bill.class);
                startActivity(intent);
            }
        });

    }
}