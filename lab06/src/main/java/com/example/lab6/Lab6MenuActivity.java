package com.example.lab6;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.example.lab6.exercise1.Exercise1Activity;
import com.example.lab6.exercise2.Exercise2Activity;
import com.example.lab6.exercise3.Exercise3Activity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

/**
 * Presents quick shortcuts to the three option menu exercises.
 */
public class Lab6MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab6_menu);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        bindCard(R.id.cardLab6Exercise1, R.color.lab_accent_1, "EX 1",
                "Exercise 1 – Options menu",
                "Menu nằm ở action bar với các mục cơ bản.",
                () -> startActivity(new Intent(this, Exercise1Activity.class)));
        bindCard(R.id.cardLab6Exercise2, R.color.lab_accent_2, "EX 2",
                "Exercise 2 – Context menu",
                "Menu xuất hiện khi long-press trên item.",
                () -> startActivity(new Intent(this, Exercise2Activity.class)));
        bindCard(R.id.cardLab6Exercise3, R.color.lab_accent_3, "EX 3",
                "Exercise 3 – Popup menu",
                "Menu popup nhỏ gọn khi chạm vào nút.",
                () -> startActivity(new Intent(this, Exercise3Activity.class)));
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
