package com.example.demodatabase.data.remote;

import com.example.demodatabase.data.model.Product;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductApi {
    @GET("products")
    Call<List<Product>> getProducts();
    @GET("products/{id}")
    Call<Product> getProduct(@Path("id") int id);
}
