package com.example.lab11.model;

import com.google.gson.annotations.SerializedName;

public class Trainee {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("phone")
    private String phone;

    @SerializedName("position")
    private String position;

    public Trainee() {}

    public Trainee(String name, String email, String phone, String position) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.position = position;
    }

    // getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
}
