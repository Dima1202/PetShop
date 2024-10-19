package com.example.petshop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class View_Pets extends AppCompatActivity {

    private FirebaseFirestore fStore;
    private FirebaseAuth fAuth;
    private RecyclerView recyclerView;
    private PetAdapter petAdapter;
    private List<Pet> petList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pets);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.RecyVPet);
        petAdapter = new PetAdapter();
        petList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(petAdapter);

        // Load and display the pet data
        loadPetsData();

        petAdapter.setOnEditDeleteClickListener(new PetAdapter.OnEditDeleteClickListener() {
            @Override
            public void onEditClick(int position) {
                Pet selectedPet = petList.get(position);
                showOptionsDialog(selectedPet);
            }

            @Override
            public void onDeleteClick(int position) {
                performDeleteAction(position);
            }
        });
    }

    private void showOptionsDialog(Pet pet) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Options for " + pet.getName());

        String[] options = {"Edit", "Delete"};

        builder.setItems(options, (dialog, which) -> {
            switch (which) {
                case 0:
                    editPet(pet);
                    break;
                case 1:
                    // Perform delete action directly
                    performDeleteAction(petList.indexOf(pet));
                    break;
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    private void editPet(Pet pet) {
        Intent intent = new Intent(this, Edit_Pet.class);
        intent.putExtra("petId", pet.getDocumentId());
        intent.putExtra("petName", pet.getName());
        intent.putExtra("petAge", pet.getAge());
        intent.putExtra("petBreed", pet.getBreed());
        intent.putExtra("petGender", pet.getGender());
        intent.putExtra("petColor", pet.getColor());
        intent.putExtra("petFeatures", pet.getFeatures());
        startActivity(intent);
    }

    private void performDeleteAction(int position) {
        if (position < 0 || position >= petList.size()) {
            return; // Ensure position is within valid range
        }

        Pet pet = petList.get(position);
        String userID = fAuth.getCurrentUser().getUid();
        String petDocumentId = pet.getDocumentId();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Deletion");
        builder.setMessage("Are you sure you want to delete " + pet.getName() + "?");
        builder.setPositiveButton("Delete", (dialog, which) -> {
            // Remove the pet from the list
            petList.remove(position);
            petAdapter.notifyItemRemoved(position);

            // Update the adapter with the modified list
            petAdapter.setPetList(petList);

            // Construct the document path
            String documentPath = "users/" + userID + "/pets/" + petDocumentId;

            // Delete the pet document
            fStore.document(documentPath)
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Pet deleted successfully", Toast.LENGTH_SHORT).show();
                        deletePetImage(petDocumentId);
                        Log.d("DeletePet", "UserID: " + userID);
                        Log.d("DeletePet", "Pet Document ID: " + petDocumentId);
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Error deleting pet", Toast.LENGTH_SHORT).show();
                    });
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            // User canceled deletion, do nothing
        });

        builder.show();
    }



    private void deletePetImage(String petDocumentId) {
        StorageReference imageRef = FirebaseStorage.getInstance().getReference("pets/" + petDocumentId + ".jpg");
        imageRef.delete()
                .addOnSuccessListener(aVoid -> {
                    // Image deleted successfully
                })
                .addOnFailureListener(e -> {
                    // Handle the failure to delete image
                });
    }

    private void loadPetsData() {
        String userID = fAuth.getCurrentUser().getUid();
        CollectionReference petsCollection = fStore.collection("users").document(userID).collection("pets");

        petsCollection.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Pet> petList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Pet pet = document.toObject(Pet.class);
                        petList.add(pet);
                    }
                    this.petList = petList;
                    petAdapter.setPetList(petList);
                    petAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error loading pets data", Toast.LENGTH_SHORT).show();
                });
    }
}


