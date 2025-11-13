package com.example.demodatabase.data.remote;

import com.example.demodatabase.data.model.CartResponse;
import com.example.demodatabase.data.remote.dto.AddToCartRequest;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CartApi {
    @GET("cart")
    Call<CartResponse> getCart();
    @Headers({"Accept: text/plain", "Content-Type: application/json"})
    @POST("cart/add")
    Call<String> addToCart(@Body AddToCartRequest body);
}
