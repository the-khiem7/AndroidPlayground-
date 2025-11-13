package com.example.lab7;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.content.res.ColorStateList;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lab07);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        bindCard(
                R.id.cardRequestPermission,
                R.color.lab_accent_1,
                "REQUEST",
                "Xin quyền CAMERA",
                "Hiển thị dialog xin quyền camera từ hệ thống.",
                this::requestCameraPermission
        );

        bindCard(
                R.id.cardOpenSettings,
                R.color.lab_accent_2,
                "SETTINGS",
                "Mở phần cài đặt ứng dụng",
                "Cho phép người dùng bật lại quyền thủ công.",
                this::openAppSettings
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "PERMISSION is Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void bindCard(int cardId,
                          int accentColorRes,
                          String badge,
                          String title,
                          String description,
                          Runnable onClick) {
        MaterialCardView card = findViewById(cardId);
        if (card == null) {
            return;
        }

        TextView badgeView = card.findViewById(R.id.tvOptionBadge);
        TextView titleView = card.findViewById(R.id.tvOptionTitle);
        TextView descriptionView = card.findViewById(R.id.tvOptionDescription);
        View accentView = card.findViewById(R.id.viewAccent);
        ImageView arrowView = card.findViewById(R.id.ivOptionArrow);

        titleView.setText(title);
        descriptionView.setText(description);
        badgeView.setText(badge);

        int accentColor = ContextCompat.getColor(this, accentColorRes);
        accentView.setBackgroundColor(accentColor);
        badgeView.setBackgroundTintList(ColorStateList.valueOf(accentColor));
        arrowView.setImageTintList(ColorStateList.valueOf(accentColor));

        card.setOnClickListener(v -> onClick.run());
    }

    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "PERMISSION is Granted", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_CODE);
        }
    }

    private void openAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }
}
