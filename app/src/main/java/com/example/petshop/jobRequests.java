package com.example.petshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class jobRequests extends AppCompatActivity {

    private ListView listView;
    private JobRequestsAdapter jobRequestsAdapter;
    private List<Job> jobRequestList;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_requests);

        listView = findViewById(R.id.listViewJobRequests);
        jobRequestList = new ArrayList<>();
        jobRequestsAdapter = new JobRequestsAdapter(jobRequestList, this);

        listView.setAdapter(jobRequestsAdapter);

        firestore = FirebaseFirestore.getInstance();

        // Retrieve job requests data from Firestore
        retrieveJobRequests();
    }

    private void retrieveJobRequests() {
        firestore.collection("JobRequests")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Job jobRequest = documentSnapshot.toObject(Job.class);
                        jobRequestList.add(jobRequest);
                    }
                    jobRequestsAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                    Toast.makeText(this, "Failed to retrieve job requests", Toast.LENGTH_SHORT).show();
                });
    }
}


