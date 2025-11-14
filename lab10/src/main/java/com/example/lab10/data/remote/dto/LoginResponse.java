package com.example.lab10.data.remote.dto;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName(value = "token", alternate = {"accessToken", "access_token"})
    public String accessToken;

    @SerializedName(value = "tokenType", alternate = {"token_type"})
    public String tokenType;

    @SerializedName(value = "refreshToken", alternate = {"refresh_token"})
    public String refreshToken;

    @SerializedName(value = "expiresIn", alternate = {"expires_in"})
    public Long expiresIn;
}
