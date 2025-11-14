package com.example.lab10;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
    // ⚠️ Nếu chạy trên emulator → 10.0.2.2
    // ⚠️ Nếu chạy thiết bị thật → IP thật của máy (vd 192.168.x.x)
      private static final String BASE_URL = "http://10.0.2.2:5140/";
    //private static final String BASE_URL = "http://192.168.1.199:5140/";

    private static Retrofit retrofit;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    // Thêm converter cho String
                    .addConverterFactory(ScalarsConverterFactory.create())
                    // Thêm converter cho JSON
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
