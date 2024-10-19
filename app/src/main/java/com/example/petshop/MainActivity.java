package com.example.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnLogin, btnReg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         btnLogin = findViewById(R.id.btn_login);
         btnReg = findViewById(R.id.btn_reg);

         btnLogin.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent visitLogin = new Intent(getApplicationContext(), login_activity.class);
                 startActivity(visitLogin);
             }
         });

         btnReg.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent visitRegister = new Intent(getApplicationContext(), register_activity.class);
                 startActivity(visitRegister);
             }
         });
    }
}