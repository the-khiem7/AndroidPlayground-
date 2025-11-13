package com.example.demodatabase.room;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.demodatabase.room.dao.CartDao;
import com.example.demodatabase.room.dao.CartItemDao;
import com.example.demodatabase.room.dao.ProductDao;
import com.example.demodatabase.room.dao.UserDao;
import com.example.demodatabase.room.entity.Cart;
import com.example.demodatabase.room.entity.CartItem;
import com.example.demodatabase.room.entity.Product;
import com.example.demodatabase.room.entity.User;
import com.example.demodatabase.ui.product.ListProductActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Product.class, Cart.class, CartItem.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract ProductDao productDao();
    public abstract CartDao cartDao();
    public abstract CartItemDao cartItemDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "ecommerce_db") // Tên Database
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}