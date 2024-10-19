package com.example.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Bill extends AppCompatActivity {

    TextView txtTotalCost, txtPackageDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        // Retrieve data from the intent
        Intent intent = getIntent();
        int totalCost = intent.getIntExtra("TOTAL_COST", 0);
        String packageTitle = intent.getStringExtra("PACKAGE_TITLE");
        String packageDescription = intent.getStringExtra("PACKAGE_DESCRIPTION");


        // Fetch the package details


        // Display the caregiver name, package details, and total cost
        txtPackageDetails = findViewById(R.id.txtPackage);
        txtTotalCost = findViewById(R.id.tvTotalCost);

        if (txtPackageDetails != null) {
            txtPackageDetails.setText("Package: " + packageTitle + "\nDescription: " + packageDescription);
        }

        if (txtTotalCost != null) {
            txtTotalCost.setText("Total Cost: $" + totalCost);
        }
    }
}
