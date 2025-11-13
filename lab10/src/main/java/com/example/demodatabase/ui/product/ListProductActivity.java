package com.example.demodatabase.ui.product;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demodatabase.R;
import com.example.demodatabase.data.remote.ApiClient;
import com.example.demodatabase.room.AppDatabase;
import com.example.demodatabase.room.entity.Product;
import com.example.demodatabase.ui.cart.CartActivity;
import com.example.demodatabase.ui.product.adapter.ProductAdapter;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListProductActivity extends AppCompatActivity {

    private static final String TAG = "ListProduct";
    private ProgressBar progress;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_product);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("List Product");
        }


        RecyclerView rv = findViewById(R.id.rvProducts);
        progress = findViewById(R.id.progress);

        int spanCount = 2;
        rv.setLayoutManager(new GridLayoutManager(this, spanCount));
        int spacing = getResources().getDimensionPixelSize(R.dimen.product_card_spacing); // 6dp nếu bạn dùng như trước
        rv.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, true));

        adapter = new ProductAdapter(this);
        rv.setAdapter(adapter);


        adapter.setOnItemClick(p -> {
            Intent i = new Intent(this, ProductDetailActivity.class);
            i.putExtra(ProductDetailActivity.EXTRA_ID, p.productId);
            startActivity(i);
        });


        loadProducts();
    }

    private void loadProducts() {
        progress.setVisibility(View.VISIBLE);
        Log.d(TAG, "Calling: GET products");

        // Tải từ cache trước (trong background thread)
        new Thread(() -> {
            Log.d(TAG, "Thread: Loading from Room cache...");

            List<Product> cached = AppDatabase.getDatabase(this)
                    .productDao()
                    .getAllProducts();
            Log.d(TAG, "Thread: Cache size = " + cached.size());

            if (!cached.isEmpty()) {
                runOnUiThread(() -> {
                        Log.d(TAG, "UI: Displaying cached products");
                    // Convert ProductEntity sang Product để hiển thị
                    List<com.example.demodatabase.data.model.Product> products = convertToProducts(cached);
                    adapter.submit(products);
                });
            }
        }).start();

        // Use MockApiWrapper - automatically switches between mock and real API
        com.example.demodatabase.data.mock.MockApiWrapper.getProducts().enqueue(new Callback<List<com.example.demodatabase.data.model.Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<com.example.demodatabase.data.model.Product>> call,
                                   @NonNull Response<List<com.example.demodatabase.data.model.Product>> resp) {
                progress.setVisibility(View.GONE);
                Log.d(TAG, "onResponse code=" + resp.code());

                if (resp.isSuccessful() && resp.body() != null) {
                    List<com.example.demodatabase.data.model.Product> items = resp.body();
                    Log.d(TAG, "items.size=" + items.size());
                    adapter.submit(items);

                    // Cache vào Room database
                    new Thread(() -> {
                        AppDatabase db = AppDatabase.getDatabase(ListProductActivity.this);
                        db.productDao().clearProducts();
                        List<Product> entities = convertToEntities(items);
                        for (Product entity : entities) {
                            db.productDao().insert(entity);
                        }
                    }).start();
                } else {
                    String err = "";
                    try {
                        err = resp.errorBody() != null ? resp.errorBody().string() : "";
                    } catch (Exception ignore) {
                    }
                    Log.e(TAG, "HTTP " + resp.code() + " " + err);
                    Toast.makeText(ListProductActivity.this,
                            "HTTP " + resp.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<com.example.demodatabase.data.model.Product>> call, @NonNull Throwable t) {
                progress.setVisibility(View.GONE);
                Log.e(TAG, "onFailure: " + t.getClass().getSimpleName() + " - " + t.getMessage(), t);
                Toast.makeText(ListProductActivity.this,
                        "FAIL: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_cart) {
            startActivity(new Intent(this, CartActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Product> convertToEntities(List<com.example.demodatabase.data.model.Product> products) {
        List<Product> entities = new ArrayList<>();
        for (com.example.demodatabase.data.model.Product p : products) {
            Product entity = new Product();
            entity.setProductId(p.productId);
            entity.setName(p.name);
            entity.setDescription(p.description);
            entity.setPrice(p.price);
            entity.setImageUrl(p.imageUrl);
            entities.add(entity);
        }
        return entities;
    }

    private List<com.example.demodatabase.data.model.Product> convertToProducts(List<Product> entities) {
        List<com.example.demodatabase.data.model.Product> products = new ArrayList<>();
        for (Product e : entities) {
            com.example.demodatabase.data.model.Product p = new com.example.demodatabase.data.model.Product();
            p.productId = e.getProductId();
            p.name = e.getName();
            p.description = e.getDescription();
            p.price = (long) e.getPrice();
            p.imageUrl = e.getImageUrl();
            products.add(p);
        }
        return products;
    }
    private static class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private final int spanCount;
        private final int spacing;
        private final boolean includeEdge;

        GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                   @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % spanCount;

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount;
                outRect.right = (column + 1) * spacing / spanCount;

                if (position < spanCount) {
                    outRect.top = spacing;
                }
                outRect.bottom = spacing;
            } else {
                outRect.left = column * spacing / spanCount;
                outRect.right = spacing - (column + 1) * spacing / spanCount;
                if (position >= spanCount) {
                    outRect.top = spacing;
                }
            }
        }
    }
}
