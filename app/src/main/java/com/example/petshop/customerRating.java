package com.example.petshop;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class customerRating {

        private String customerId;
        private String caregiverId;
        private String caregiverName;
        private float rating;
        private String comment;

        public customerRating() {
            // Default constructor required for Firestore
        }

        public customerRating(String customerId, String caregiverId, float rating, String comment,String caregiverName) {
            this.customerId = customerId;
            this.caregiverId = caregiverId;
            this.rating = rating;
            this.comment = comment;
            this.caregiverName = caregiverName;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getCaregiverId() {
            return caregiverId;
        }

        public void setCaregiverId(String caregiverId) {
            this.caregiverId = caregiverId;
        }
        public  String getCaregiverName(){return caregiverName;}
        public void setCaregiverName(String caregiverName) {
        this.caregiverName = caregiverName;
    }

    public float getRating() {
            return rating;
        }

        public void setRating(float rating) {
            this.rating = rating;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }


    }


