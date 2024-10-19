package com.example.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Rating;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Rate extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private Spinner caregiverSpinner;
    private RatingBar ratingBar;
    private EditText customerNameEditText;
    private EditText commentEditText;
    private Button submitButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        firestore = FirebaseFirestore.getInstance();

        caregiverSpinner = findViewById(R.id.caregiverSpinner);
        ratingBar = findViewById(R.id.ratingBar);
        customerNameEditText = findViewById(R.id.edtcsName);
        commentEditText = findViewById(R.id.Comment);
        submitButton = findViewById(R.id.btnSubmit);

        // Set up the caregiver spinner
        setUpCaregiverSpinner();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float rating = ratingBar.getRating();
                String customerName = customerNameEditText.getText().toString().trim();
                String caregiverId = getSelectedCaregiverId();
                String comment = commentEditText.getText().toString().trim();

                // Validate if any of the fields are empty
                if (customerName.isEmpty() || caregiverId.isEmpty() || comment.isEmpty()) {
                    Toast.makeText(Rate.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save the rating to Firestore in a new "ratings" collection
                saveRatingToFirestore(customerName, caregiverId, rating, comment);
            }
        });
    }

    private void setUpCaregiverSpinner() {
        // Reference to the "caregivers" collection in Firestore
        CollectionReference caregiversCollection = firestore.collection("Caregivers");

        // Fetch caregiver names and IDs from Firestore
        caregiversCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<String> caregiverNames = new ArrayList<>();
                ArrayList<String> caregiverIds = new ArrayList<>();

                for (QueryDocumentSnapshot document : task.getResult()) {
                    String caregiverName = document.getString("Name");
                    String caregiverId = document.getId();

                    caregiverNames.add(caregiverName);
                    caregiverIds.add(caregiverId);
                }

                // Create an ArrayAdapter using the caregiver names
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, caregiverNames);

                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // Apply the adapter to the spinner
                caregiverSpinner.setAdapter(adapter);
            } else {
                // Handle errors
                Toast.makeText(Rate.this, "Failed to fetch caregivers", Toast.LENGTH_SHORT).show();
                Log.e("RateActivity", "Error fetching caregivers", task.getException()); // Log the error
            }
        });
    }

    private String getSelectedCaregiverId() {
        // Retrieve the selected caregiver's ID from the spinner
        int selectedPosition = caregiverSpinner.getSelectedItemPosition();
        return (selectedPosition != Spinner.INVALID_POSITION) ? caregiverSpinner.getItemAtPosition(selectedPosition).toString() : "";
    }

    private void saveRatingToFirestore(String customerName, String caregiverId, float rating, String comment) {
        // Reference to the "ratings" collection in Firestore
        CollectionReference ratingsCollection = firestore.collection("ratings");

        // Create a map with the rating data
        Map<String, Object> ratingData = new HashMap<>();
        ratingData.put("customerName", customerName);
        ratingData.put("caregiverId", caregiverId); // Use caregiver's document ID as a reference
        ratingData.put("rating", rating);
        ratingData.put("comment", comment);

        // Add the rating to the "ratings" collection
        ratingsCollection
                .add(ratingData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(Rate.this, "Rating submitted successfully", Toast.LENGTH_SHORT).show();
                    clearFields();
                    // After submitting the rating, you can fetch and display the ratings if needed
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Rate.this, "Failed to submit rating", Toast.LENGTH_SHORT).show();
                });
    }

    private void clearFields() {
        customerNameEditText.getText().clear();
        commentEditText.getText().clear();
        ratingBar.setRating(0.0f);
    }
}







