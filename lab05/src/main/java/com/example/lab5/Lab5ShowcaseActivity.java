package com.example.lab5;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

import com.example.lab5.R;

/**
 * Hub screen for Lab 5 that exposes both exercises.
 */
public class Lab5ShowcaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab5_showcase);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        bindCard(R.id.cardLab51, R.color.lab_accent_1, "LAB 5.1",
                "Lab 5.1 – Module grid",
                "Danh sách module dạng lưới với custom adapter.",
                () -> startActivity(new Intent(this, Lab51Activity.class)));
        bindCard(R.id.cardLab52, R.color.lab_accent_4, "LAB 5.2",
                "Lab 5.2 – User manager",
                "Quản lý user với dialog CRUD và RecyclerView.",
                () -> startActivity(new Intent(this, Lab52Activity.class)));
    }

    private void bindCard(int cardId, int accentColorRes, String badge, String title, String description, Runnable action) {
        MaterialCardView card = findViewById(cardId);
        if (card == null) return;
        TextView badgeView = card.findViewById(R.id.tvOptionBadge);
        TextView titleView = card.findViewById(R.id.tvOptionTitle);
        TextView descriptionView = card.findViewById(R.id.tvOptionDescription);
        View accentView = card.findViewById(R.id.viewAccent);
        ImageView arrowView = card.findViewById(R.id.ivOptionArrow);

        badgeView.setText(badge);
        titleView.setText(title);
        descriptionView.setText(description);

        int accentColor = ContextCompat.getColor(this, accentColorRes);
        accentView.setBackgroundColor(accentColor);
        ViewCompat.setBackgroundTintList(badgeView, ColorStateList.valueOf(accentColor));
        arrowView.setImageTintList(ColorStateList.valueOf(accentColor));

        card.setOnClickListener(v -> action.run());
    }
}
