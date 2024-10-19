package com.example.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class caregiverInfo extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private EditText edtName, edtAge, edtContactNo, edtExperience, edtGender, edtSkills;
    private Button btnSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_info);

        firestore = FirebaseFirestore.getInstance();

        // Initialize UI components
        edtName = findViewById(R.id.edtName);
        edtAge = findViewById(R.id.edtAge);
        edtContactNo = findViewById(R.id.edtContactNo);
        edtExperience = findViewById(R.id.edtExperience);
        edtGender = findViewById(R.id.edtGender);
        edtSkills = findViewById(R.id.edtSkills);
        btnSave = findViewById(R.id.btnSave);

        // Fetch current caregiver details and populate the UI
        fetchAndPopulateCaregiverDetails();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save edited caregiver details
                saveEditedCaregiverDetails();
            }
        });
    }

    private void fetchAndPopulateCaregiverDetails() {
        // Fetch current caregiver details from Firestore and populate the UI
        // Assume you have the caregiver's document ID
        String caregiverId = "YOUR_CAREGIVER_DOCUMENT_ID";

        // Reference to the "Caregivers" collection in Firestore
        CollectionReference caregiversCollection = firestore.collection("Caregivers");

        // Fetch caregiver details
        caregiversCollection.document(caregiverId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        // Retrieve caregiver details
                        Map<String, Object> caregiverData = task.getResult().getData();

                        // Populate UI with current caregiver details
                        if (caregiverData != null) {
                            edtName.setText((String) caregiverData.get("Name"));
                            edtAge.setText((String) caregiverData.get("Age"));
                            edtContactNo.setText((String) caregiverData.get("Contact_No"));
                            edtExperience.setText((String) caregiverData.get("Experience"));
                            edtGender.setText((String) caregiverData.get("Gender"));
                            edtSkills.setText((String) caregiverData.get("Skills"));
                        }
                    } else {
                        // Handle errors
                    }
                });
    }

    private void saveEditedCaregiverDetails() {
        // Save edited caregiver details to Firestore
        // Assume you have the caregiver's document ID
        String caregiverId = "YOUR_CAREGIVER_DOCUMENT_ID";

        // Reference to the "Caregivers" collection in Firestore
        CollectionReference caregiversCollection = firestore.collection("Caregivers");

        // Create a map with the edited caregiver details
        Map<String, Object> editedCaregiverData = new HashMap<>();
        editedCaregiverData.put("Name", edtName.getText().toString());
        editedCaregiverData.put("Age", edtAge.getText().toString());
        editedCaregiverData.put("Contact_No", edtContactNo.getText().toString());
        editedCaregiverData.put("Experience", edtExperience.getText().toString());
        editedCaregiverData.put("Gender", edtGender.getText().toString());
        editedCaregiverData.put("Skills", edtSkills.getText().toString());

        // Update caregiver details in Firestore
        caregiversCollection.document(caregiverId)
                .update(editedCaregiverData)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Handle successful update
                    } else {
                        // Handle update failure
                    }
                });
    }
}
