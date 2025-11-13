package com.example.lab6.exercise2;

import android.os.Bundle;
import android.view.MenuInflater;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.example.lab6.R;

public class Exercise2Activity extends AppCompatActivity {
    Button btnPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise2);

        btnPopup = findViewById(R.id.btnPopup);

        btnPopup.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(Exercise2Activity.this, view);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_popup, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                btnPopup.setText(item.getTitle());
                return true;
            });

            popup.show();
        });
    }
}