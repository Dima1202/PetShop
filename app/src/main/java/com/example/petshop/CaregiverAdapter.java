package com.example.petshop;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;


public class CaregiverAdapter extends RecyclerView.Adapter<CaregiverAdapter.ViewHolder> {

    private List<Caregiver> caregiverList;
    private FirebaseFirestore firestore;

    public CaregiverAdapter(List<Caregiver> caregiverList) {
        this.caregiverList = caregiverList;
        this.firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.caregiver_display, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Caregiver caregiver = caregiverList.get(position);

        // Display name tags in front of the data
        holder.txtName.setText("Name: " + caregiver.getName());
        holder.txtAge.setText("Age: " + caregiver.getAge());
        holder.txtGender.setText("Gender: " + caregiver.getGender());
        holder.txtContactNo.setText("Contact No: " + caregiver.getContact_No());
        holder.txtExperience.setText("Experience: " + caregiver.getExperience());
        holder.txtSkills.setText("Skills: " + caregiver.getSkills());

        // Fetch and display average rating
        fetchAndDisplayRatings(caregiver.getName(), holder.txtAverageRating);

        // Load image using Glide
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.img2)
                .error(R.drawable.img2);

        Glide.with(holder.itemView.getContext())
                .load(caregiver.getImageURL())
                .apply(requestOptions)
                .into(holder.imageCaregiver);
    }

    @Override
    public int getItemCount() {
        return caregiverList.size();
    }

    private void fetchAndDisplayRatings(String caregiverName, TextView txtAverageRating) {
        // Reference to the "ratings" sub-collection under the caregiver's document
        CollectionReference ratingsCollection = firestore.collection("ratings");

        // Query to fetch ratings for a specific caregiver name
        Query query = ratingsCollection.whereEqualTo("caregiverId", caregiverName);

        // Fetch all ratings for this caregiver
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                int totalRatings = 0;
                int numberOfRatings = task.getResult().size();

                for (QueryDocumentSnapshot document : task.getResult()) {
                    // Assuming you have a field named "rating" in your rating documents
                    double rating = document.getDouble("rating");
                    totalRatings += rating;
                }

                double averageRating = numberOfRatings > 0 ? totalRatings / numberOfRatings : 0;

                // Display average rating
                txtAverageRating.setText("Average Rating: " + averageRating);
            } else {
                // Handle errors
                txtAverageRating.setText("Average Rating: N/A (Failed to fetch ratings)");
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageCaregiver;
        TextView txtName, txtAge, txtGender, txtContactNo, txtExperience, txtSkills, txtAverageRating;
        Button Btnbook;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageCaregiver = itemView.findViewById(R.id.imgcer);
            txtName = itemView.findViewById(R.id.crName);
            txtAge = itemView.findViewById(R.id.crAge);
            txtGender = itemView.findViewById(R.id.crGender);
            txtContactNo = itemView.findViewById(R.id.crNo);
            txtExperience = itemView.findViewById(R.id.crexp);
            txtSkills = itemView.findViewById(R.id.crski);
            txtAverageRating = itemView.findViewById(R.id.txtAverageRating);
            Btnbook = itemView.findViewById(R.id.btn_book);

            Btnbook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Get the caregiver ID and name from the clicked item
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Caregiver clickedCaregiver = caregiverList.get(position);
                        String caregiverId = clickedCaregiver.getDocumentId();
                        String caregiverName = clickedCaregiver.getName();

                        // Use the context of the itemView to start the pricingPlan activity
                        Context context = itemView.getContext();

                        // Start the pricingPlan activity with the caregiver ID and name as extras
                        Intent intent = new Intent(context, pricingPlan.class);
                        intent.putExtra("CAREGIVER_ID", caregiverId);
                        intent.putExtra("CAREGIVER_NAME", caregiverName);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}




