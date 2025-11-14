package com.example.lab10.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lab10.room.entity.Product;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert
    long insert(Product product);

    @Update
    void update(Product product);

    @Delete
    void delete(Product product);

    @Query("SELECT * FROM products")
    List<Product> getAllProducts();

    @Query("SELECT * FROM products WHERE productId = :id")
    Product getProductById(int id);

    @Query("SELECT * FROM products WHERE name LIKE :searchQuery")
    List<Product> searchProductsByName(String searchQuery);

    @Query("DELETE FROM products")
    void clearProducts();
}
