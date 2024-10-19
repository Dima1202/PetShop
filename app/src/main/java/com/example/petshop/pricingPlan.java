package com.example.petshop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

public class pricingPlan extends AppCompatActivity {

    ImageButton imgBtnMonth, imgBtnWeek, imgBtnDay;
    private String caregiverId;
    private String caregiverName;

    FirebaseFirestore FirebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pricing_plan);

        imgBtnMonth = findViewById(R.id.imgbtnMonth);
        imgBtnWeek = findViewById(R.id.imgbtnweek);
        imgBtnDay = findViewById(R.id.imgbtnDay);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

// Get the caregiver ID from the Intent
        String caregiverId = getIntent().getStringExtra("CAREGIVER_ID");

        db.collection("caregivers")
                .document(caregiverId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String caregiverName = documentSnapshot.getString("name");
                        // Now you have the caregiverName, use it as needed
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle error
                });

        Intent intent = getIntent();
        caregiverId = intent.getStringExtra("CAREGIVER_ID");
        caregiverName = intent.getStringExtra("CAREGIVER_NAME");

        TextView txtCaregiverName = findViewById(R.id.textView45);
        if (txtCaregiverName != null) {
            txtCaregiverName.setText(caregiverName);
        }

        imgBtnMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cost = 150; // Replace with the actual cost for the monthly package
                String packageTitle = "Pet Care Service - Monthly Package";
                String packageDescription = "Pet Care Service for a Month:\n" +
                        "- Daily feeding and fresh water\n" +
                        "- Daily walks and exercise\n" +
                        "- Grooming sessions\n" +
                        "- Veterinary check-ups as needed\n" +
                        "- Playtime and socialization\n" +
                        "Cost: $150 per month.";

                showPackageInfo(packageTitle, packageDescription, cost);
            }
        });

        imgBtnWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cost = 50; // Replace with the actual cost for the weekly package
                String packageTitle = "Pet Care Service - Weekly Package";
                String packageDescription = "Pet Care Service for a Week:\n" +
                        "- Daily feeding and fresh water\n" +
                        "- Three walks and exercise sessions\n" +
                        "- Grooming session mid-week\n" +
                        "- Playtime and socialization\n" +
                        "Cost: $50 per week.";

                showPackageInfo(packageTitle, packageDescription, cost);
            }
        });

        imgBtnDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cost = 15; // Replace with the actual cost for the daily package
                String packageTitle = "Pet Care Service - Daily Package";
                String packageDescription = "Pet Care Service for a Day:\n" +
                        "- Two meals with fresh water\n" +
                        "- One long walk or two short walks\n" +
                        "- Brief playtime and interaction\n" +
                        "- Basic grooming if needed\n" +
                        "Cost: $15 per day.";

                showPackageInfo(packageTitle, packageDescription, cost);
            }
        });
    }

    

    private void showPackageInfo(String title, String description, int cost) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(description)
                .setPositiveButton("Book Now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        initiateBooking(title, description, cost);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void initiateBooking(String packageTitle, String packageDescription, int cost) {
        // Start the Bill activity with the necessary data
        Intent intent = new Intent(this, Bill.class);
        intent.putExtra("CAREGIVER_NAME", caregiverName);
        intent.putExtra("PACKAGE_TITLE", packageTitle);
        intent.putExtra("PACKAGE_DESCRIPTION", packageDescription);
        intent.putExtra("TOTAL_COST", cost);
        startActivity(intent);

        // Display a confirmation message.
        Toast.makeText(this, "Booking initiated. Total cost: $" + cost, Toast.LENGTH_SHORT).show();
    }
}






