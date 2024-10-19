package com.example.petshop;

public class Caregiver {

    private String Name;
    private String Age;
    private String Gender;
    private String Contact_No;
    private String Experience;
    private String Skills;
    private String ImageURL;
    private float averageRating;
    private String documentId;

    // Default constructor (required for Firestore)
    public Caregiver() {
        // Default constructor is necessary for Firestore to convert documents to objects

    }

    // Parameterized constructor
    public Caregiver(String Name, String Age, String Gender, String ContactNo, String Experience, String Skills, String ImageURL) {
        this.Name = Name;
        this.Age = Age;
        this.Gender = Gender;
        this.Contact_No = ContactNo;
        this.Experience = Experience;
        this.Skills = Skills;
        this.ImageURL = ImageURL;
        this.averageRating = 0;
    }

    // Getters and setters

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String Age) {
        this.Age = Age;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getContact_No() {
        return Contact_No;
    }

    public void setContact_No(String Contact_No) {
        this.Contact_No = Contact_No;
    }

    public String getExperience() {
        return Experience;
    }

    public void setExperience(String Experience) {
        this.Experience = Experience;
    }

    public String getSkills() {
        return Skills;
    }

    public void setSkills(String Skills) {
        this.Skills = Skills;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String ImageURL) {
        this.ImageURL = ImageURL;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}

