package com.example.petshop;

import android.os.Parcel;
import android.os.Parcelable;


import android.os.Parcel;
import android.os.Parcelable;

public class Job implements Parcelable {

    public Job() {
        // Default constructor required for Firestore
    }
    private String customerName;
    private String petDetails;
    private String bookingDetails;
    private String petId;
    private String userId;


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPetDetails() {
        return petDetails;
    }

    public void setPetDetails(String petDetails) {
        this.petDetails = petDetails;
    }

    public String getBookingDetails() {
        return bookingDetails;
    }

    public void setBookingDetails(String bookingDetails) {
        this.bookingDetails = bookingDetails;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // Parcelable implementation
    protected Job(Parcel in) {
        customerName = in.readString();
        petDetails = in.readString();
        bookingDetails = in.readString();
        petId = in.readString();
        userId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(customerName);
        dest.writeString(petDetails);
        dest.writeString(bookingDetails);
        dest.writeString(petId);
        dest.writeString(userId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Job> CREATOR = new Creator<Job>() {
        @Override
        public Job createFromParcel(Parcel in) {
            return new Job(in);
        }

        @Override
        public Job[] newArray(int size) {
            return new Job[size];
        }
    };
    // End of Parcelable implementation
}





