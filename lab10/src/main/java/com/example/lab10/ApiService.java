package com.example.lab10;

import com.example.lab10.LoginRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("auth/login")
    Call<String> login(@Body LoginRequest request);
}
