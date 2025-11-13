package com.example.lab8;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "LAB8_CHANNEL";
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    sendNotification();
                } else {
                    Toast.makeText(this, "Permission denied. Cannot send notification.", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();

        Button btnSendNotification = findViewById(R.id.btnSendNotification);
        btnSendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askForNotificationPermission();
            }
        });
    }

    private void askForNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                sendNotification();
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        } else {
            sendNotification();
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Lab8 Notification Channel";
            String description = "Channel for Lab 8 Notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void sendNotification() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Title push notification")
                .setContentText("Message push notification")
                .setSmallIcon(R.drawable.ic_notification_custom)
                .setLargeIcon(bitmap)
                .setColor(getResources().getColor(R.color.colorAccent))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
                    PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        notificationManager.notify(getNotificationId(), notification);
    }
    private int getNotificationId() {
        return (int) new Date().getTime();
    }
}