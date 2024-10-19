package com.example.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Caregiver_Menu extends AppCompatActivity {
    ImageButton imgBtnJob, imgBtnInfo;
    TextView textView, txvLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_menu);

        textView = findViewById(R.id.txvlog);

        imgBtnJob = findViewById(R.id.imgbtnJob);
        imgBtnInfo = findViewById(R.id.imgbtn_Info);
        txvLogout = findViewById(R.id.txvlog);

        imgBtnJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the JobRequestsActivity
                Intent intent = new Intent(Caregiver_Menu.this, jobRequests.class);
                startActivity(intent);
            }
        });

        imgBtnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the CaregiverInfoActivity
                Intent intent = new Intent(Caregiver_Menu.this, caregiverInfo.class);
                startActivity(intent);
            }
        });

        txvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void logout() {
        Intent intent = new Intent(Caregiver_Menu.this, CaregiverLogin.class);
        startActivity(intent);
        finish();
    }
}

