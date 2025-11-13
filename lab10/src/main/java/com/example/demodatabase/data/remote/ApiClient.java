package com.example.demodatabase.data.remote;

import android.content.Context;

import com.example.demodatabase.data.local.TokenManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {

    private static String BASE_URL = "https://localhost:7067/";


    private static ProductApi INSTANCE;

    private static Retrofit retrofit;
    private static ProductApi productApi;
    private static AuthApi authApi;
    private static CartApi cartApi;

    private static TokenManager tokenManager;


    public static void init(Context appContext) {
        if (retrofit != null) return;

        tokenManager = TokenManager.get(appContext.getApplicationContext());

        HttpLoggingInterceptor log = new HttpLoggingInterceptor();
        log.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder base = insecureClientBuilder()
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .connectionSpecs(Arrays.asList(
                        new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                                .tlsVersions(TlsVersion.TLS_1_2)
                                .allEnabledCipherSuites()
                                .build()
                ))

                .addInterceptor(new AuthInterceptor(tokenManager))
                .addInterceptor(log)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);

        OkHttpClient client = base.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        productApi = retrofit.create(ProductApi.class);
        authApi    = retrofit.create(AuthApi.class);
        cartApi    = retrofit.create(CartApi.class);

        // Initialize MockApiWrapper with real API instances
        com.example.demodatabase.data.mock.MockApiWrapper.init(productApi, authApi, cartApi);
    }


    public static ProductApi get() {
        ensureInit();
        return productApi;
    }

    public static ProductApi products() {
        ensureInit();
        return productApi;
    }

    public static AuthApi auth() {
        ensureInit();
        return authApi;
    }


    public static CartApi cart() {
        ensureInit();
        return cartApi;
    }


    public static void setBaseUrl(String url) {
        if (retrofit != null) throw new IllegalStateException("ApiClient already initialized");
        BASE_URL = url;
    }

    private static void ensureInit() {
        if (retrofit == null)
            throw new IllegalStateException("ApiClient.init(context) chưa được gọi!");
    }


    private static OkHttpClient.Builder insecureClientBuilder() {
        try {
            javax.net.ssl.X509TrustManager trustAll = new javax.net.ssl.X509TrustManager() {
                public void checkClientTrusted(java.security.cert.X509Certificate[] c, String a) {}
                public void checkServerTrusted(java.security.cert.X509Certificate[] c, String a) {}
                public java.security.cert.X509Certificate[] getAcceptedIssuers() { return new java.security.cert.X509Certificate[]{}; }
            };
            javax.net.ssl.TrustManager[] trustManagers = new javax.net.ssl.TrustManager[]{ trustAll };

            javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("TLS");
            sc.init(null, trustManagers, new java.security.SecureRandom());
            javax.net.ssl.SSLSocketFactory sf = sc.getSocketFactory();

            return new OkHttpClient.Builder()
                    .sslSocketFactory(sf, trustAll)
                    .hostnameVerifier((hostname, session) ->
                            "localhost".equalsIgnoreCase(hostname) ||
                                    "10.0.2.2".equals(hostname) ||
                                    "127.0.0.1".equals(hostname)
                    );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
