package com.example.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class petInfo extends AppCompatActivity {

    EditText edtP_Name, edtP_age, edtBreed, edtGender, edtColor;
    TextView multilinetextview;
    Button btnReg;
    ImageButton imgbtn;
    ImageView imageView;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    Uri imageUri;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_info);

        edtP_Name = findViewById(R.id.edtPName);
        edtP_age = findViewById(R.id.edtPAge);
        edtBreed = findViewById(R.id.edtBreed);
        edtGender = findViewById(R.id.edtGender);
        edtColor = findViewById(R.id.edtCol);
        imageView = findViewById(R.id.imvPet);

        multilinetextview = findViewById(R.id.edtFeat);

        btnReg = findViewById(R.id.btnP_Reg);
        imgbtn = findViewById(R.id.imgbtnPet);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerPet();
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
            imgbtn.setImageURI(imageUri);
        }
    }

    private void registerPet() {
        String petName = edtP_Name.getText().toString().trim();
        String petAge = edtP_age.getText().toString().trim();
        String petBreed = edtBreed.getText().toString().trim();
        String petGender = edtGender.getText().toString().trim();
        String petColor = edtColor.getText().toString().trim();
        String petFeatures = multilinetextview.getText().toString().trim();

        // Check if any field is empty
        if (petName.isEmpty() || petAge.isEmpty() || petBreed.isEmpty() || petGender.isEmpty() || petColor.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a map to store pet data
        Map<String, Object> petData = new HashMap<>();
        petData.put("Name", petName);
        petData.put("Age", petAge);
        petData.put("Breed", petBreed);
        petData.put("Gender", petGender);
        petData.put("Color", petColor);
        petData.put("Features", petFeatures);

        // Get the current user ID
        String userID = fAuth.getCurrentUser().getUid();

        // Add pet data to Firestore under the user's collection
        DocumentReference userPetsRef = fStore.collection("users").document(userID).collection("pets").document();
        userPetsRef.set(petData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Pet information registered successfully", Toast.LENGTH_SHORT).show();
                    // Upload the image after successfully registering pet information
                    if (imageUri != null) {
                        uploadImage(userPetsRef.getId());
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error registering pet information", Toast.LENGTH_SHORT).show();
                    // Handle the failure
                });
    }

    private void uploadImage(String petDocumentId) {
        StorageReference fileRef = storageReference.child("pets/" + petDocumentId + ".jpg");

        fileRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    Toast.makeText(this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error uploading image", Toast.LENGTH_SHORT).show();
                    // Handle the failure
                });
    }

    private void loadUploadedImage(StorageReference imageRef) {
        imageRef.getDownloadUrl()
                .addOnSuccessListener(uri -> {
                    try {
                        URL url = new URL(uri.toString());
                        InputStream inputStream = url.openStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        imageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
                    // Handle the failure
                });
    }
}