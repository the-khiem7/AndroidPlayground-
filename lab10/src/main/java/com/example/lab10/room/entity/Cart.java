package com.example.lab10.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "carts")
public class Cart {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int cartId;

    private int userId;

    public Cart(int userId) {
        this.userId = userId;
    }

    public int getCartId() { return cartId; }
    public void setCartId(int cartId) { this.cartId = cartId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}
