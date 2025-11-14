package com.example.lab10.data.remote;

import android.util.Base64;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class JwtUtils {
    private JwtUtils() {}

    public static long getExpirationEpochSeconds(String jwt) {
        try {
            String[] parts = jwt.split("\\.");
            if (parts.length < 2) return 0L;
            byte[] decoded = base64UrlDecode(parts[1]);
            String json = new String(decoded, StandardCharsets.UTF_8);
            JSONObject obj = new JSONObject(json);
            return obj.optLong("exp", 0L);
        } catch (Exception e) {
            return 0L;
        }
    }

    public static boolean isExpired(String jwt, long skewSeconds) {
        long exp = getExpirationEpochSeconds(jwt);
        if (exp <= 0L) return false; // nếu server không có exp, coi như không hết hạn
        long now = System.currentTimeMillis() / 1000L;
        return now + Math.max(0, skewSeconds) >= exp;
    }

    private static byte[] base64UrlDecode(String s) {
        String t = s.replace('-', '+').replace('_', '/');
        int pad = (4 - (t.length() % 4)) % 4;
        for (int i = 0; i < pad; i++) t += "=";
        return Base64.decode(t, Base64.DEFAULT);
    }
}
