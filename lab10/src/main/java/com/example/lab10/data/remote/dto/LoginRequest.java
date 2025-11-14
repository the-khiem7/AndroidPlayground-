package com.example.lab10.data.remote.dto;

public class LoginRequest {
    public String username;
    public String password;

    public LoginRequest(String u, String p) {
        this.username = u;
        this.password = p;
    }
}
