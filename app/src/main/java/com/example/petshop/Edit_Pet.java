package com.example.petshop;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Edit_Pet extends AppCompatActivity {
    private FirebaseFirestore fStore;
    private FirebaseAuth fAuth;
    private EditText editName, editAge, editBreed, editGender, editColor, editFeatures;
    private Button btnSaveChanges;
    private String petDocumentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pet);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        editName = findViewById(R.id.edtPet_Name);
        editAge = findViewById(R.id.edtPet_Age);
        editBreed = findViewById(R.id.edtPet_Breed);
        editGender = findViewById(R.id.edtPet_Gender);
        editColor = findViewById(R.id.edtPet_Color);
        editFeatures = findViewById(R.id.edtPet_Features);
        btnSaveChanges = findViewById(R.id.btn_Update);

        // Get pet details from the intent
        Intent intent = getIntent();
        petDocumentId = intent.getStringExtra("petId");
        String petName = intent.getStringExtra("petName");
        String petAge = intent.getStringExtra("petAge");
        String petBreed = intent.getStringExtra("petBreed");
        String petGender = intent.getStringExtra("petGender");
        String petColor = intent.getStringExtra("petColor");
        String petFeatures = intent.getStringExtra("petFeatures");

        // Set existing pet details to the EditText fields
        editName.setText(petName);
        editAge.setText(petAge);
        editBreed.setText(petBreed);
        editGender.setText(petGender);
        editColor.setText(petColor);
        editFeatures.setText(petFeatures);

        btnSaveChanges.setOnClickListener(view -> saveChanges());
    }

    private void saveChanges() {
        String newName = editName.getText().toString().trim();
        String newAge = editAge.getText().toString().trim();
        String newBreed = editBreed.getText().toString().trim();
        String newGender = editGender.getText().toString().trim();
        String newColor = editColor.getText().toString().trim();
        String newFeatures = editFeatures.getText().toString().trim();

        // Update the pet details in Firestore
        updatePetDetails(newName, newAge, newBreed, newGender, newColor, newFeatures);
    }

    private void updatePetDetails(String newName, String newAge, String newBreed,
                                  String newGender, String newColor, String newFeatures) {
        String userID = fAuth.getCurrentUser().getUid();

        DocumentReference petRef = fStore.collection("users").document(userID)
                .collection("pets").document(petDocumentId);

        petRef.update("name", newName,
                        "age", newAge,
                        "breed", newBreed,
                        "gender", newGender,
                        "color", newColor,
                        "features", newFeatures)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(Edit_Pet.this, "Changes saved successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Finish the activity after successful update
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Edit_Pet.this, "Error saving changes", Toast.LENGTH_SHORT).show();
                });
    }
}

