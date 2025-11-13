package com.example.androidplayground;

import android.content.Context;
import android.content.Intent;

/**
 * Simple descriptor for a lab entry displayed on the launcher screen.
 */
class LabItem {
    interface IntentFactory {
        Intent create(Context context);
    }

    private final String title;
    private final String description;
    private final IntentFactory intentFactory;

    LabItem(String title, String description, IntentFactory intentFactory) {
        this.title = title;
        this.description = description;
        this.intentFactory = intentFactory;
    }

    String getTitle() {
        return title;
    }

    String getDescription() {
        return description;
    }

    Intent buildIntent(Context context) {
        return intentFactory.create(context);
    }
}
