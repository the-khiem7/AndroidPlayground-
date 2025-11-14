package com.example.lab10;

public class LoginResponse {
    private String token; // giả sử API trả về token dạng {"token": "..."}
    public String getToken() {
        return token;
    }
}
