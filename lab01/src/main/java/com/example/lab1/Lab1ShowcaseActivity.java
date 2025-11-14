package com.example.lab1;

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

/**
 * Simple hub to launch the different layout demos that belong to Lab 1.
 */
public class Lab1ShowcaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab1_showcase);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        bindCard(R.id.cardLab1LinearA, R.color.lab_accent_1, "LINEAR A",
                "Linear Layout – phiên bản A",
                "Tham khảo bố cục tuyến tính cơ bản.",
                () -> startActivity(new Intent(this, LinearLayoutA.class)));
        bindCard(R.id.cardLab1LinearB, R.color.lab_accent_2, "LINEAR B",
                "Linear Layout – phiên bản B",
                "Biến thể với căn chỉnh khác.",
                () -> startActivity(new Intent(this, LinearLayoutB.class)));
        bindCard(R.id.cardLab1Relative, R.color.lab_accent_3, "RELATIVE",
                "Relative Layout demo",
                "Thử bố cục tương đối linh hoạt.",
                () -> startActivity(new Intent(this, RelativeLayout.class)));
        bindCard(R.id.cardLab1Constraint, R.color.lab_accent_4, "CONSTRAINT",
                "Constraint Layout demo",
                "Sử dụng ConstraintLayout hiện đại.",
                () -> startActivity(new Intent(this, ConstrainLayout.class)));
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
