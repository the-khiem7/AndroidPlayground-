package com.example.lab10.data.model;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("productId") public int productId;
    @SerializedName("name") public String name;
    @SerializedName("description") public String description;
    @SerializedName("price") public double price;
    @SerializedName("imageUrl") public String imageUrl;
}
