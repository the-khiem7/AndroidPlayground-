package com.example.lab10.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.lab10.room.entity.CartItem;

import java.util.List;

@Dao
public interface CartItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(CartItem item);

    @Update
    void update(CartItem item);

    @Delete
    void delete(CartItem item);

    @Query("SELECT * FROM cart_items WHERE cartId = :cartId")
    List<CartItem> getCartItemsByCartId(int cartId);

    @Query("SELECT * FROM cart_items WHERE cartId = :cartId AND productId = :productId LIMIT 1")
    CartItem getCartItemByCartIdAndProductId(int cartId, int productId);
}
