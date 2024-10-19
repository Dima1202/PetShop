package com.example.petshop;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import com.example.petshop.Job;

public class JobRequestsAdapter extends BaseAdapter {
    private List<Job> jobRequestList;
    private Context context;
    private FirebaseFirestore firestore;

    public JobRequestsAdapter(List<Job> jobRequestList, Context context) {
        this.jobRequestList = jobRequestList;
        this.context = context;
        this.firestore = FirebaseFirestore.getInstance(); // Initialize Firestore
    }

    @Override
    public int getCount() {
        return jobRequestList.size();
    }

    @Override
    public Object getItem(int position) {
        return jobRequestList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // Method to add a Job object to the adapter
    public void addJob(Job job) {
        jobRequestList.add(job);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.activity_job_request_listview, parent, false);

            // Create a ViewHolder and store references to your views
            viewHolder = new ViewHolder();
            viewHolder.textViewCustomerName = view.findViewById(R.id.textViewCaregiverName);
            viewHolder.textViewPetDetails = view.findViewById(R.id.textViewPetDetails);
            viewHolder.textViewBookingDetails = view.findViewById(R.id.textViewBookingDetails);

            view.setTag(viewHolder);
        } else {
            // View is being recycled; retrieve the ViewHolder object from tag
            viewHolder = (ViewHolder) view.getTag();
        }

        // Get the Job object for the current position
        Job jobRequest = jobRequestList.get(position);

        // Set the data to views using the ViewHolder
        viewHolder.textViewBookingDetails.setText("Booking Details: " + jobRequest.getBookingDetails());
        viewHolder.textViewPetDetails.setText("Pet Details: " + jobRequest.getPetDetails());

        // Fetch user and pet details from Firestore using the user ID and pet ID
        fetchUserAndPetDetails(jobRequest.getUserId(), jobRequest.getPetId(), viewHolder);

        return view;
    }

    // ViewHolder pattern to improve performance by recycling views
    private static class ViewHolder {
        TextView textViewCustomerName;
        TextView textViewPetDetails;
        TextView textViewBookingDetails;
    }

    private void fetchUserAndPetDetails(String userId, String petId, ViewHolder viewHolder) {
        if (userId != null && petId != null) {
            // Assume you have a "users" collection in Firestore
            CollectionReference usersCollection = firestore.collection("users");

            // Fetch user details using document ID directly
            usersCollection.document(userId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot userSnapshot = task.getResult();
                            if (userSnapshot.exists()) {
                                // User document exists, retrieve details
                                String customerName = userSnapshot.getString("Name"); // Update with the correct field name
                                viewHolder.textViewCustomerName.setText("Customer Name: " + customerName);
                            } else {
                                // Handle the case where the user document does not exist
                                Log.d("Firestore", "User document does not exist");
                                viewHolder.textViewCustomerName.setText("User details not available");
                            }
                        } else {
                            // Handle errors during Firestore data retrieval
                            Log.e("Firestore", "Error fetching user details", task.getException());
                            viewHolder.textViewCustomerName.setText("Error fetching user details");
                        }
                    });

            // Fetch pet details using document ID directly
            usersCollection.document(userId).collection("pets").document(petId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot petSnapshot = task.getResult();
                            if (petSnapshot.exists()) {
                                // Pet document exists, retrieve details
                                String petName = petSnapshot.getString("petName");
                                int petAge = petSnapshot.getLong("Age").intValue(); // Update with the correct field name
                                String petBreed = petSnapshot.getString("Breed"); // Update with the correct field name

                                // Display pet details in the TextView
                                Log.d("Firestore", "Pet Name: " + petName + ", Age: " + petAge + ", Breed: " + petBreed);
                                viewHolder.textViewPetDetails.setText("Pet Details: " + petName + ", Age: " + String.valueOf(petAge) + ", Breed: " + petBreed);
                            } else {
                                // Handle the case where the pet document does not exist
                                Log.d("Firestore", "Pet document does not exist");
                                viewHolder.textViewPetDetails.setText("Pet details not available");
                            }
                        } else {
                            // Handle errors during Firestore data retrieval
                            Log.e("Firestore", "Error fetching pet details", task.getException());
                            viewHolder.textViewPetDetails.setText("Error fetching pet details");
                        }
                    });
        } else {
            // Handle the case where userId or petId is null
            viewHolder.textViewPetDetails.setText("User ID or Pet ID is null");
        }
    }


}







