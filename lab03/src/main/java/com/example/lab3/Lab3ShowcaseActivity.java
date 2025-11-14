package com.example.lab3;

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

import com.example.lab3.R;

/**
 * Entry point for Lab 3 that lets the user jump to either exercise variant.
 */
public class Lab3ShowcaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab3_showcase);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        bindCard(R.id.cardLab3Main, R.color.lab_accent_2, "LAB 3",
                "Lab 3 – Custom adapter",
                "Hiển thị danh sách sách với custom adapter.",
                () -> startActivity(new Intent(this, Lab3Activity.class)));
        bindCard(R.id.cardLab31, R.color.lab_accent_3, "LAB 3.1",
                "Lab 3.1 – CRUD danh sách",
                "Thêm/sửa/xóa danh sách khóa học bằng ListView.",
                () -> startActivity(new Intent(this, Lab31Activity.class)));
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
