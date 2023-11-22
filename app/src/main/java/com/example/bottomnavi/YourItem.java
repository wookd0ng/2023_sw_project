package com.example.bottomnavi;

public class YourItem {

    private String imageUrl;
    private String text;
    private String location;

    public YourItem(String imageUrl, String text, String location) {
        this.imageUrl = imageUrl;
        this.text = text;
        this.location = location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getText() {
        return text;
    }

    public String getLocation() {
        return location;
    }

}
