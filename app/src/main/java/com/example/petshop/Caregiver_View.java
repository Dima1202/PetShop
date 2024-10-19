package com.example.petshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Caregiver_View extends AppCompatActivity {

    private RecyclerView recyclerViewCaregivers;
    private CaregiverAdapter caregiverAdapter;
    private List<Caregiver> caregiverList;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_view);

        recyclerViewCaregivers = findViewById(R.id.recyCare);
        recyclerViewCaregivers.setLayoutManager(new LinearLayoutManager(this));

        caregiverList = new ArrayList<>();
        caregiverAdapter = new CaregiverAdapter(caregiverList);
        recyclerViewCaregivers.setAdapter(caregiverAdapter);

        firestore = FirebaseFirestore.getInstance();

        // Retrieve caregiver data from Firestore
        retrieveCaregivers();
    }

    private void retrieveCaregivers() {
        firestore.collection("Caregivers")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Caregiver caregiver = documentSnapshot.toObject(Caregiver.class);

                        // Set the document ID for each caregiver
                        caregiver.setDocumentId(documentSnapshot.getId());

                        caregiverList.add(caregiver);

                        // Fetch and display average rating for each caregiver
                        fetchAndDisplayAverageRating(caregiver);
                    }
                    caregiverAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                    Toast.makeText(this, "Failed to retrieve caregivers", Toast.LENGTH_SHORT).show();
                });
    }

    private void fetchAndDisplayAverageRating(Caregiver caregiver) {
        String caregiverId = caregiver.getDocumentId();
        firestore.collection("ratings")
                .whereEqualTo("caregiverId", caregiverId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    int totalRatings = queryDocumentSnapshots.size();
                    float totalRatingValue = 0;

                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        customerRating rating = document.toObject(customerRating.class);
                        totalRatingValue += rating.getRating(); // Use rating variable, not customerRating
                    }

                    // Calculate average rating
                    float averageRating = totalRatings > 0 ? totalRatingValue / totalRatings : 0;

                    // Set the average rating to the caregiver object
                    caregiver.setAverageRating(averageRating);

                    // Notify the adapter that the data has changed
                    caregiverAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                    Toast.makeText(this, "Failed to fetch ratings", Toast.LENGTH_SHORT).show();
                });
    }


}

