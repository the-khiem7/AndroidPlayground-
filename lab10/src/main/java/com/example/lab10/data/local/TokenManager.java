package com.example.lab10.data.local;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
    private static final String PREF = "auth_pref";
    private static final String KEY_ACCESS = "access_token";
    private static final String KEY_REFRESH = "refresh_token";
    private static final String KEY_TYPE = "token_type"; // thường là "Bearer"

    private static TokenManager INSTANCE;
    private final SharedPreferences sp;

    private TokenManager(Context ctx) {
        sp = ctx.getApplicationContext().getSharedPreferences(PREF, Context.MODE_PRIVATE);
    }

    public static TokenManager get(Context ctx) {
        if (INSTANCE == null) INSTANCE = new TokenManager(ctx);
        return INSTANCE;
    }

    public void save(String tokenType, String accessToken, String refreshToken) {
        sp.edit()
                .putString(KEY_TYPE, tokenType == null || tokenType.isEmpty() ? "Bearer" : tokenType)
                .putString(KEY_ACCESS, accessToken)
                .putString(KEY_REFRESH, refreshToken)
                .apply();
    }

    public String getAccessToken() { return sp.getString(KEY_ACCESS, null); }
    public String getTokenType()   { return sp.getString(KEY_TYPE, "Bearer"); }
    public String getRefreshToken(){ return sp.getString(KEY_REFRESH, null); }

    public boolean isLoggedIn() { return getAccessToken() != null && !getAccessToken().isEmpty(); }

    public void clear() { sp.edit().clear().apply(); }
}
