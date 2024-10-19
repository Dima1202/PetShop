package com.example.petshop;

public class Pet {
    private String Name;
    private String Age;
    private String Breed;
    private String Gender;
    private String Color;
    private String Features;
    private  String Image;
    private String documentId;


    public Pet() {
    }

    public Pet(String Name, String Age, String Breed, String Gender, String Color, String Features, String Image) {
        this.Name = Name;
        this.Age = Age;
        this.Breed = Breed;
        this.Gender = Gender;
        this.Color = Color;
        this.Features = Features;
        this.Image = Image;

    }

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

    public String getBreed() {
        return Breed;
    }

    public void setBreed(String Breed) {
        this.Breed = Breed;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String Color) {
        this.Color = Color;
    }

    public String getFeatures() {
        return Features;
    }

    public void setFeatures(String Features) {
        this.Features = Features;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}

