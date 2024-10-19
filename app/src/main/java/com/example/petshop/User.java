package com.example.petshop;

import org.jetbrains.annotations.Nullable;

public class User {
    String Name,UserEmail;
    @Nullable String contact;

    public User(){}
    public User(String name, String userEmail, @Nullable String contact) {
        Name = name;
        UserEmail = userEmail;
        this.contact = contact;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    @Nullable
    public String getContact() {
        return contact;
    }

    public void setContact(@Nullable String contact) {
        this.contact = contact;
    }
}
