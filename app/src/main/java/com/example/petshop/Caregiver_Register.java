package com.example.petshop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.HashMap;
import java.util.Map;

public class Caregiver_Register extends AppCompatActivity {

    EditText edtName, edtAge, edtNo, edtExp, edtGender;
    TextView multilinetextview;
    Button btnReg;
    ImageButton imgCam;
    FirebaseFirestore fireStore;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    Uri imageUri;
    private FirebaseAuth mAuth;
    private Map<String, Object> caregiverData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_register);

        edtName = findViewById(R.id.edtCname);
        edtAge = findViewById(R.id.edtAge);
        edtNo = findViewById(R.id.edtPhone);
        edtExp = findViewById(R.id.edtExp);
        edtGender = findViewById(R.id.edtGen);

        multilinetextview = findViewById(R.id.edtSkill);

        imgCam = findViewById(R.id.img_btn_upload);

        btnReg = findViewById(R.id.btn_Register);

        fireStore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        mAuth = FirebaseAuth.getInstance();

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerCaregiver();
            }
        });

        imgCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
        }
    }

    private void registerCaregiver() {
        String name = edtName.getText().toString().trim();
        String age = edtAge.getText().toString().trim();
        String gender = edtGender.getText().toString().trim();
        String phone = edtNo.getText().toString().trim();
        String experience = edtExp.getText().toString().trim();
        String skills = multilinetextview.getText().toString().trim();

        // Check if any field is empty
        if (name.isEmpty() || age.isEmpty() || gender.isEmpty() || phone.isEmpty() || experience.isEmpty() || skills.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the current user UID
        String uid = mAuth.getCurrentUser().getUid();

        // Create a map to store caregiver data
        caregiverData = new HashMap<>();
        caregiverData.put("Name", name);
        caregiverData.put("Age", age);
        caregiverData.put("Gender", gender);
        caregiverData.put("Contact_No", phone);
        caregiverData.put("Experience", experience);
        caregiverData.put("Skills", skills);

        // Add caregiver data to Firestore
        fireStore.collection("Caregivers")
                .document(uid)
                .set(caregiverData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Caregiver registered successfully", Toast.LENGTH_SHORT).show();

                    // Start the caregiver login activity upon successful registration
                    startCaregiverLoginActivity();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error registering caregiver", Toast.LENGTH_SHORT).show();
                    // Handle the failure
                });

        if (imageUri != null) {
            uploadImage(uid);
        }
    }

    private void uploadImage(String uid) {
        StorageReference fileRef = storageReference.child("caregiver_images/" + uid + ".jpg");

        fileRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        saveImageUrlToFirestore(uid, uri.toString());
                    });
                })
                .addOnFailureListener(e -> {
                    // Handle failures
                });
    }

    private void saveImageUrlToFirestore(String uid, String imageUrl) {
        // Add the imageUrl to the caregiverData map
        caregiverData.put("ImageURL", imageUrl);

        // Update the Firestore document with the image URL
        fireStore.collection("Caregivers")
                .document(uid)
                .update("ImageURL", imageUrl)
                .addOnSuccessListener(aVoid -> {
                    // Image URL successfully updated in Firestore
                })
                .addOnFailureListener(e -> {
                    // Handle the failure
                });
    }

    private void startCaregiverLoginActivity() {
        Intent intent = new Intent(this, CaregiverLogin.class);
        startActivity(intent);
        finish();
    }
}