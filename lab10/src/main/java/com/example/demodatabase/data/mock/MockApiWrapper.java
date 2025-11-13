package com.example.demodatabase.data.mock;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.example.demodatabase.data.model.CartResponse;
import com.example.demodatabase.data.model.Product;
import com.example.demodatabase.data.remote.AuthApi;
import com.example.demodatabase.data.remote.CartApi;
import com.example.demodatabase.data.remote.ProductApi;
import com.example.demodatabase.data.remote.dto.AddToCartRequest;
import com.example.demodatabase.data.remote.dto.LoginRequest;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Mock API Wrapper
 * Provides mock responses when USE_MOCK_DATA is enabled
 * Otherwise delegates to real API
 */
public class MockApiWrapper {

    private static ProductApi realProductApi;
    private static AuthApi realAuthApi;
    private static CartApi realCartApi;
    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void init(ProductApi productApi, AuthApi authApi, CartApi cartApi) {
        realProductApi = productApi;
        realAuthApi = authApi;
        realCartApi = cartApi;
    }

    /**
     * Get all products - with mock support
     */
    public static Call<List<Product>> getProducts() {
        if (MockDataProvider.USE_MOCK_DATA) {
            return new MockCall<>(MockDataRepository.getAllProducts());
        }
        if (realProductApi == null) {
            throw new IllegalStateException("MockApiWrapper not initialized. Call ApiClient.init() first.");
        }
        return realProductApi.getProducts();
    }

    /**
     * Get product by ID - with mock support
     */
    public static Call<Product> getProduct(int id) {
        if (MockDataProvider.USE_MOCK_DATA) {
            Product product = MockDataRepository.getProductById(id);
            if (product != null) {
                return new MockCall<>(product);
            } else {
                // Return 404 error if product not found
                return new MockCall<>(null, 404, "Product not found");
            }
        }
        if (realProductApi == null) {
            throw new IllegalStateException("MockApiWrapper not initialized. Call ApiClient.init() first.");
        }
        return realProductApi.getProduct(id);
    }

    /**
     * Login - with mock support
     */
    public static Call<String> login(LoginRequest request) {
        if (MockDataProvider.USE_MOCK_DATA) {
            // Validate credentials
            com.example.demodatabase.room.entity.User user =
                MockDataRepository.validateLogin(request.username, request.password);

            if (user != null) {
                // Return mock token
                return new MockCall<>(MockDataRepository.getMockToken());
            } else {
                // Return error
                return new MockCall<>(null, 401, "Invalid credentials");
            }
        }
        if (realAuthApi == null) {
            throw new IllegalStateException("MockApiWrapper not initialized. Call ApiClient.init() first.");
        }
        return realAuthApi.login(request);
    }

    /**
     * Add to cart - with mock support
     */
    public static Call<String> addToCart(AddToCartRequest request) {
        if (MockDataProvider.USE_MOCK_DATA) {
            boolean success = MockDataRepository.addToCart(request.productId, request.quantity);
            if (success) {
                return new MockCall<>("Added to cart successfully");
            } else {
                return new MockCall<>(null, 404, "Product not found");
            }
        }
        if (realCartApi == null) {
            throw new IllegalStateException("MockApiWrapper not initialized. Call ApiClient.init() first.");
        }
        return realCartApi.addToCart(request);
    }

    /**
     * Get cart - with mock support
     */
    public static Call<CartResponse> getCart() {
        if (MockDataProvider.USE_MOCK_DATA) {
            return new MockCall<>(MockDataRepository.getCart());
        }
        if (realCartApi == null) {
            throw new IllegalStateException("MockApiWrapper not initialized. Call ApiClient.init() first.");
        }
        return realCartApi.getCart();
    }

    /**
     * Mock Call implementation for Retrofit
     */
    private static class MockCall<T> implements Call<T> {
        private final T data;
        private final int errorCode;
        private final String errorMessage;
        private boolean executed = false;
        private boolean cancelled = false;

        MockCall(T data) {
            this.data = data;
            this.errorCode = 200;
            this.errorMessage = null;
        }

        MockCall(T data, int errorCode, String errorMessage) {
            this.data = data;
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
        }

        @NonNull
        @Override
        public Response<T> execute() throws IOException {
            executed = true;

            // Simulate network delay
            if (MockDataProvider.MOCK_DELAY_MS > 0) {
                try {
                    Thread.sleep(MockDataProvider.MOCK_DELAY_MS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            if (errorCode == 200) {
                return Response.success(data);
            } else {
                return Response.error(errorCode,
                    okhttp3.ResponseBody.create(
                        okhttp3.MediaType.parse("application/json"),
                        "{\"error\":\"" + errorMessage + "\"}"
                    ));
            }
        }

        @Override
        public void enqueue(@NonNull Callback<T> callback) {
            if (executed) throw new IllegalStateException("Already executed");
            executed = true;

            // Simulate async network call with delay
            handler.postDelayed(() -> {
                if (cancelled) return;

                if (errorCode == 200) {
                    callback.onResponse(this, Response.success(data));
                } else {
                    callback.onResponse(this, Response.error(errorCode,
                        okhttp3.ResponseBody.create(
                            okhttp3.MediaType.parse("application/json"),
                            "{\"error\":\"" + errorMessage + "\"}"
                        )));
                }
            }, MockDataProvider.MOCK_DELAY_MS);
        }

        @Override
        public boolean isExecuted() {
            return executed;
        }

        @Override
        public void cancel() {
            cancelled = true;
        }

        @Override
        public boolean isCanceled() {
            return cancelled;
        }

        @NonNull
        @Override
        public Call<T> clone() {
            return new MockCall<>(data, errorCode, errorMessage);
        }

        @NonNull
        @Override
        public okhttp3.Request request() {
            return new okhttp3.Request.Builder()
                .url("http://mock.local/")
                .build();
        }

        @NonNull
        @Override
        public okio.Timeout timeout() {
            return okio.Timeout.NONE;
        }
    }
}

