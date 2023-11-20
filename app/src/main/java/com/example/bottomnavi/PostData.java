package com.example.bottomnavi;

public class PostData {
    private String text;
    private String imageUrl;
    private String location;

    public PostData() {
        // Default constructor required for Firebase
    }

    public PostData(String text, String imageUrl, String location) {
        this.text = text;
        this.imageUrl = imageUrl;
        this.location = location;
    }

    public String getText() {
        return text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLocation() { return location; }
}