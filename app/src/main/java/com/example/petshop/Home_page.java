package com.example.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

public class Home_page extends AppCompatActivity {

    ImageButton imgBtnBook, imgBtnInfo, imgBtnPetInfo, imgBtnServices, imgBtnPricing, imgBtnRegister, imgBtnView, imgBtnLogin;
    FirebaseAuth fireAuth;
    FirebaseFirestore fireStore;
    TextView textView,logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        imgBtnBook = findViewById(R.id.imgbtnBook);
        imgBtnInfo = findViewById(R.id.imgbtnInfo);
        imgBtnPetInfo = findViewById(R.id.imgbtnPInfo);
        imgBtnServices = findViewById(R.id.imgbtnSer);
        imgBtnPricing = findViewById(R.id.imgbtnPrice);
        imgBtnRegister = findViewById(R.id.imgbtnReg);
        imgBtnView = findViewById(R.id.imgbtnVPets);
        imgBtnLogin = findViewById(R.id.imgbtnLoginInter);

        logout = findViewById(R.id.txvLogout);

        textView = findViewById(R.id.txtView);

        fireAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            currentUser.reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    // Now try to get the display name
                    String username = currentUser.getDisplayName();
                    if (username != null) {
                        textView.setText("Welcome, " + username + "!");
                    } else {
                        textView.setText("Welcome!");
                    }
                }
            });
        } else {
            // User is not authenticated
            textView.setText("Welcome!");
        }



        imgBtnBook.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Replace SecondActivity.class with the actual class for the Book activity
                Intent intent = new Intent(Home_page.this, Caregiver_View.class);
                startActivity(intent);
            }
        });

        imgBtnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Replace ThirdActivity.class with the actual class for the Info activity
                Intent intent = new Intent(Home_page.this, Customer_Info.class);
                startActivity(intent);
            }
        });

        imgBtnPetInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Replace ThirdActivity.class with the actual class for the Info activity
                Intent intent = new Intent(Home_page.this, petInfo.class);
                startActivity(intent);
            }
        });

        imgBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Replace ThirdActivity.class with the actual class for the Info activity
                Intent intent = new Intent(Home_page.this, View_Pets.class);
                startActivity(intent);
            }
        });

        imgBtnServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Replace ThirdActivity.class with the actual class for the Info activity
                Intent intent = new Intent(Home_page.this, Services.class);
                startActivity(intent);
            }
        });

        imgBtnPricing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Replace ThirdActivity.class with the actual class for the Info activity
                Intent intent = new Intent(Home_page.this, pricingPlan.class);
                startActivity(intent);
            }
        });

        imgBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Replace ThirdActivity.class with the actual class for the Info activity
                Intent intent = new Intent(Home_page.this, Caregiver_Register.class);
                startActivity(intent);
            }
        });

        imgBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Replace ThirdActivity.class with the actual class for the Info activity
                Intent intent = new Intent(Home_page.this, CaregiverLogin.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Sign out the user
                fireAuth.signOut();

                // Start the main activity
                Intent intent = new Intent(Home_page.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });


    }
}