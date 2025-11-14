package com.example.lab10.data.remote;

import androidx.annotation.NonNull;

import com.example.lab10.data.local.TokenManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private final TokenManager tokenManager;

    public AuthInterceptor(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @NonNull @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();
        String token = tokenManager.getAccessToken();

        if (token != null && !JwtUtils.isExpired(token, 30)) {
            String type = tokenManager.getTokenType();
            Request withAuth = original.newBuilder()
                    .header("Authorization", type + " " + token)
                    .build();
            return chain.proceed(withAuth);
        } else {

            if (token != null && JwtUtils.isExpired(token, 30)) {
                tokenManager.clear();
            }
            return chain.proceed(original);
        }
    }
}
