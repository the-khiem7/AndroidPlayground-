package com.example.androidplayground;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.ColorRes;

/**
 * Simple descriptor for a lab entry displayed on the launcher screen.
 */
class LabItem {
    interface IntentFactory {
        Intent create(Context context);
    }

    private final String title;
    private final String description;
    private final String badge;
    @ColorRes
    private final int accentColorRes;
    private final IntentFactory intentFactory;

    LabItem(String title, String description, String badge, @ColorRes int accentColorRes, IntentFactory intentFactory) {
        this.title = title;
        this.description = description;
        this.badge = badge;
        this.accentColorRes = accentColorRes;
        this.intentFactory = intentFactory;
    }

    String getTitle() {
        return title;
    }

    String getDescription() {
        return description;
    }

    String getBadge() {
        return badge;
    }

    @ColorRes
    int getAccentColorRes() {
        return accentColorRes;
    }

    Intent buildIntent(Context context) {
        return intentFactory.create(context);
    }
}
