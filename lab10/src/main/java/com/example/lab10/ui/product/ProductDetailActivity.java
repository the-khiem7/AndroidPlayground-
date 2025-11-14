package com.example.lab10.ui.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.lab10.R;
import com.example.lab10.data.remote.ApiClient;
import com.example.lab10.room.AppDatabase;
import com.example.lab10.room.entity.Product;
import com.example.lab10.ui.product.adapter.AddToCartSheet;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "product_id";
    private final NumberFormat vn = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
    private ShapeableImageView img1;
    private TextView tvName, tvPrice, tvDesc;
    private ProgressBar progress;
    private MaterialButton btnAdd;
    private com.example.lab10.data.model.Product current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
            toolbar.inflateMenu(R.menu.menu_product_detail);
            toolbar.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.action_cart) {
                    startActivity(new Intent(this, com.example.lab10.ui.cart.CartActivity.class));
                    return true;
                }
                return false;
            });
        }

        vn.setMinimumFractionDigits(0);
        vn.setMaximumFractionDigits(0);

        img1 = findViewById(R.id.imgDetail);
        tvName = findViewById(R.id.tvDetailName);
        tvPrice = findViewById(R.id.tvDetailPrice);
        tvDesc = findViewById(R.id.tvDetailDesc);
        progress = findViewById(R.id.progress);
        btnAdd = findViewById(R.id.btnAddToCart);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id <= 0) {
            Toast.makeText(this, "Product id không hợp lệ", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // LOAD OFFLINE từ ROOM trước
        new Thread(() -> {
            Product cached = AppDatabase.getDatabase(this)
                    .productDao()
                    .getProductById(id);

            if (cached != null) {
                runOnUiThread(() -> {
                    com.example.lab10.data.model.Product p = new com.example.lab10.data.model.Product();
                    p.productId = cached.getProductId();
                    p.name = cached.getName();
                    p.description = cached.getDescription();
                    p.price = cached.getPrice();
                    p.imageUrl = cached.getImageUrl();
                    current = p;
                    bind(p);
                });
            }
        }).start();

        loadDetail(id);

        btnAdd.setOnClickListener(v -> {
            if (current == null) return;
            String img = normalizeDevUrl(current.imageUrl);
            AddToCartSheet.newInstance(current.productId, current.name, current.price, img)
                    .show(getSupportFragmentManager(), "add_to_cart");
        });


    }

    private void loadDetail(int id) {
        showLoading(true);
        // Use MockApiWrapper - automatically switches between mock and real API
        com.example.lab10.data.mock.MockApiWrapper.getProduct(id).enqueue(new Callback<com.example.lab10.data.model.Product>() {
            @Override
            public void onResponse(Call<com.example.lab10.data.model.Product> call, Response<com.example.lab10.data.model.Product> res) {
                showLoading(false);
                if (!res.isSuccessful() || res.body() == null) {
                    Toast.makeText(ProductDetailActivity.this, "Không tải được chi tiết - Product ID: " + id, Toast.LENGTH_SHORT).show();
                    finish(); // Close activity if product not found
                    return;
                }
                current = res.body();

                if (current == null) {
                    Toast.makeText(ProductDetailActivity.this, "Product not found", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }

                bind(current);

                // 🟡 3️⃣ UPDATE CACHE trong ROOM
                new Thread(() -> {
                    try {
                        Product e = new Product();
                        e.setProductId(current.productId);
                        e.setName(current.name);
                        e.setDescription(current.description);
                        e.setPrice(current.price);
                        e.setImageUrl(current.imageUrl);
                        AppDatabase.getDatabase(ProductDetailActivity.this)
                                .productDao()
                                .insert(e);
                    } catch (Exception ex) {
                        // Ignore cache errors
                    }
                }).start();

            }

            @Override
            public void onFailure(Call<com.example.lab10.data.model.Product> call, Throwable t) {
                showLoading(false);
                Toast.makeText(ProductDetailActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_LONG).show();
                finish(); // Close activity on failure
            }
        });
    }

    private void bind(com.example.lab10.data.model.Product p) {
        if (p == null) {
            Toast.makeText(this, "Product data is null", Toast.LENGTH_SHORT).show();
            return;
        }

        tvName.setText(p.name == null ? "" : p.name);
        try {
            tvPrice.setText(vn.format(p.price) + "đ");
        } catch (Exception e) {
            tvPrice.setText("N/A");
        }
        tvDesc.setText(p.description == null ? "" : p.description);

        String url = p.imageUrl;
        if (url != null) {
            url = url.replace("http://localhost:5140", "https://localhost:7067");
            url = url.replace("https://localhost:7067", "https://localhost:7067");
        }

        try {
            Glide.with(this)
                    .load(url)
                    .placeholder(R.drawable.ic_image_placeholder)
                    .error(R.drawable.ic_broken_image_24)
                    .into(img1);
        } catch (Exception e) {
            // Handle Glide errors gracefully
        }
    }

    private void showLoading(boolean show) {
        progress.setVisibility(show ? View.VISIBLE : View.GONE);
        btnAdd.setEnabled(!show);
    }

    private String normalizeDevUrl(String url) {
        if (url == null) return null;
        return url.replace("http://localhost:5140", "http://10.0.2.2:5140")
                .replace("https://localhost:7067", "https://10.0.2.2:7067");
    }

}
