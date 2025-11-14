package com.example.lab10.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;

import com.example.lab10.room.entity.Cart;

@Dao
public interface CartDao {

    @Insert
    long insert(Cart cart);

    @Delete
    void delete(Cart cart);

    @Query("SELECT * FROM carts WHERE userId = :userId LIMIT 1")
    Cart getCartByUserId(int userId);

    @Query("SELECT * FROM carts WHERE cartId = :cartId")
    Cart getCartById(int cartId);
}
