package com.example.demodatabase.ui.cart;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demodatabase.R;
import com.example.demodatabase.data.model.CartResponse;
import com.example.demodatabase.data.remote.ApiClient;
import com.example.demodatabase.ui.product.adapter.CartAdapter;
import com.google.android.material.appbar.MaterialToolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    private static final String TAG = "Cart";
    private CartAdapter adapter;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        RecyclerView rv = findViewById(R.id.rvCart);
        progress = findViewById(R.id.progress);

        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CartAdapter();
        rv.setAdapter(adapter);

        loadCart();
    }


    private void loadCart() {
        progress.setVisibility(View.VISIBLE);
        // Use MockApiWrapper - automatically switches between mock and real API
        com.example.demodatabase.data.mock.MockApiWrapper.getCart().enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(@NonNull Call<CartResponse> call, @NonNull Response<CartResponse> resp) {
                progress.setVisibility(View.GONE);
                if (!resp.isSuccessful() || resp.body() == null) {
                    Log.e(TAG, "HTTP " + resp.code());
                    Toast.makeText(CartActivity.this, "HTTP " + resp.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                CartResponse cart = resp.body();
                int count = cart.items == null ? 0 : cart.items.size();
                Log.d(TAG, "cartId=" + cart.cartId + ", items=" + count);
                adapter.submit(cart.items);
            }

            @Override
            public void onFailure(@NonNull Call<CartResponse> call, @NonNull Throwable t) {
                progress.setVisibility(View.GONE);
                Log.e(TAG, "FAIL: " + t.getMessage(), t);
                Toast.makeText(CartActivity.this, "FAIL: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}