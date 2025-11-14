package com.example.lab13;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

/**
 * Replaces the google-services Gradle plugin by configuring Firebase manually at runtime.
 */
final class FirebaseConfigHelper {

    private static final String CUSTOM_APP_NAME = "lab13-auth";

    private FirebaseConfigHelper() {
    }

    private static FirebaseApp ensureApp(Context context) {
        List<FirebaseApp> apps = FirebaseApp.getApps(context);
        for (FirebaseApp app : apps) {
            if (CUSTOM_APP_NAME.equals(app.getName())) {
                return app;
            }
        }

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:532912214089:android:8016f67ebe3f4762a9af98")
                .setProjectId("lab13auth-7f33e")
                .setApiKey("AIzaSyCfwZeH_R-mPMA2UQ8VAkjoDxdLrgdW8xg")
                .setGcmSenderId("532912214089")
                .setStorageBucket("lab13auth-7f33e.firebasestorage.app")
                .build();
        return FirebaseApp.initializeApp(context, options, CUSTOM_APP_NAME);
    }

    static FirebaseAuth provideAuth(Context context) {
        FirebaseApp app = ensureApp(context.getApplicationContext());
        return FirebaseAuth.getInstance(app);
    }
}
