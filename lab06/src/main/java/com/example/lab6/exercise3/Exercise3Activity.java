package com.example.lab6.exercise3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.lab6.R;

public class Exercise3Activity extends AppCompatActivity {
    Button btnChonMau;
    ConstraintLayout manHinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise3);

        btnChonMau = (Button) findViewById(R.id.button_ChonMau);
        manHinh = (ConstraintLayout) findViewById(R.id.manHinh);

        registerForContextMenu(btnChonMau);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.menuDo) {
            manHinh.setBackgroundColor(Color.RED);
        } else if (itemId == R.id.menuVang) {
            manHinh.setBackgroundColor(Color.YELLOW);
        } else if (itemId == R.id.menuXanh) {
            manHinh.setBackgroundColor(Color.BLUE);
        }

        return super.onContextItemSelected(item);
    }
}