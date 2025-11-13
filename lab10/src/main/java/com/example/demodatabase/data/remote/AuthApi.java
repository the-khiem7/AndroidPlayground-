package com.example.demodatabase.data.remote;

import com.example.demodatabase.data.remote.dto.LoginRequest;
import com.example.demodatabase.data.remote.dto.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {
    @POST("auth/login")
    Call<String> login(@Body LoginRequest body);
}
