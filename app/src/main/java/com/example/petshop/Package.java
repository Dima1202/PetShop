package com.example.petshop;

import android.os.Parcel;
import android.os.Parcelable;

public class Package implements Parcelable {
    private String packageId;
    private String packageName;
    private float packagePrice;



    public Package() {
        // Default constructor required for Firestore
    }

    protected Package(Parcel in) {
        packageId = in.readString();
        packageName = in.readString();
        packagePrice = in.readFloat();
    }

    public static final Creator<Package> CREATOR = new Creator<Package>() {
        @Override
        public Package createFromParcel(Parcel in) {
            return new Package(in);
        }

        @Override
        public Package[] newArray(int size) {
            return new Package[size];
        }
    };

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public float getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(float packagePrice) {
        this.packagePrice = packagePrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(packageId);
        dest.writeString(packageName);
        dest.writeFloat(packagePrice);
    }
}

