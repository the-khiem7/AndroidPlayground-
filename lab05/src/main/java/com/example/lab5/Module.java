package com.example.lab5;


public class Module {
    private int imageResId;
    private String title;
    private String description;
    private String platform;

    public Module(int imageResId, String title, String description, String platform) {
        this.imageResId = imageResId;
        this.title = title;
        this.description = description;
        this.platform = platform;
    }

    public int getImageResId() { return imageResId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getPlatform() { return platform; }
}

