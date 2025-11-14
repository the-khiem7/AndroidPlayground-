package com.example.lab10.data.remote.dto;

public class AddToCartRequest {
    public int productId;
    public int quantity;

    public AddToCartRequest(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
