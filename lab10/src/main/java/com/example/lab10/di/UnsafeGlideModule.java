package com.example.lab10.di;

import android.content.Context;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@GlideModule
public class UnsafeGlideModule extends AppGlideModule {
    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        // Tạo client trust-all y chang ApiClient
        OkHttpClient client = buildInsecureOkHttp();

        // Thay HTTP stack mặc định bằng OkHttp
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(client));
    }

    private OkHttpClient buildInsecureOkHttp() {
        try {
            javax.net.ssl.TrustManager[] trustAll = new javax.net.ssl.TrustManager[]{
                    new javax.net.ssl.X509TrustManager() {
                        public void checkClientTrusted(java.security.cert.X509Certificate[] c, String a) {}
                        public void checkServerTrusted(java.security.cert.X509Certificate[] c, String a) {}
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() { return new java.security.cert.X509Certificate[]{}; }
                    }
            };
            javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("TLS");
            sc.init(null, trustAll, new java.security.SecureRandom());
            javax.net.ssl.SSLSocketFactory sf = sc.getSocketFactory();

            HttpLoggingInterceptor log = new HttpLoggingInterceptor();
            log.setLevel(HttpLoggingInterceptor.Level.BASIC);

            return new OkHttpClient.Builder()
                    .sslSocketFactory(sf, (javax.net.ssl.X509TrustManager) trustAll[0])
                    .hostnameVerifier((h, s) -> true)
                    .addInterceptor(log)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Tắt manifest parsing để tránh xung đột modules khác (tuỳ chọn)
    @Override public boolean isManifestParsingEnabled() { return false; }
}
