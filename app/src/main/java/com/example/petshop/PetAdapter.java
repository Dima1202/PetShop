package com.example.petshop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {

    private List<Pet> petList;
    private OnEditDeleteClickListener onEditDeleteClickListener; // New interface for edit and delete

    public PetAdapter() {
        this.petList = new ArrayList<>();
    }

    public void setPetList(List<Pet> petList) {
        this.petList = petList;
    }

    public void setOnEditDeleteClickListener(OnEditDeleteClickListener onEditDeleteClickListener) {
        this.onEditDeleteClickListener = onEditDeleteClickListener;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_view_struct, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        Pet pet = petList.get(position);

        // Set pet details to the ViewHolder
        holder.petName.setText(pet.getName());
        holder.petAge.setText(pet.getAge());
        holder.petBreed.setText(pet.getBreed());
        holder.petGender.setText(pet.getGender());
        holder.petColor.setText(pet.getColor());
        holder.petFeatures.setText(pet.getFeatures());

        // Load and display the pet image using the loadUploadedImage method
        StorageReference imageRef = FirebaseStorage.getInstance().getReference("pets/" + pet.getImage() + ".jpg");
        loadUploadedImage(imageRef, holder.petImage);

        // Set click listeners for edit/delete actions
        holder.btnEdit.setOnClickListener(view -> {
            if (onEditDeleteClickListener != null) {
                onEditDeleteClickListener.onEditClick(position);
            }
        });

        holder.btnDelete.setOnClickListener(view -> {
            if (onEditDeleteClickListener != null) {
                onEditDeleteClickListener.onDeleteClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    public static class PetViewHolder extends RecyclerView.ViewHolder {
        ImageView petImage;
        TextView petName;
        TextView petAge;
        TextView petBreed;
        TextView petGender;
        TextView petColor;
        TextView petFeatures;
        Button btnEdit, btnDelete;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            petImage = itemView.findViewById(R.id.certificate);
            petName = itemView.findViewById(R.id.crName);
            petAge = itemView.findViewById(R.id.crAge);
            petBreed = itemView.findViewById(R.id.crGender);
            petGender = itemView.findViewById(R.id.crNo);
            petColor = itemView.findViewById(R.id.crexp);
            petFeatures = itemView.findViewById(R.id.crski);
            btnEdit = itemView.findViewById(R.id.btn_book);
            btnDelete = itemView.findViewById(R.id.btn_del);
        }
    }

    private void loadUploadedImage(StorageReference imageRef, ImageView imageView) {
        imageRef.getDownloadUrl()
                .addOnSuccessListener(uri -> {
                    try {
                        URL url = new URL(uri.toString());
                        InputStream inputStream = url.openStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        imageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })
                .addOnFailureListener(e -> {
                    imageView.setImageResource(R.drawable.img1);
                });
    }

    // New interface for edit and delete actions
    public interface OnEditDeleteClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }
}



