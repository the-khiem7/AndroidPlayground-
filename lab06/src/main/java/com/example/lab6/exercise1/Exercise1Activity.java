package com.example.lab6.exercise1;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.lab6.R;

public class Exercise1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String itemName = item.getTitle().toString();

        Toast.makeText(this, "Bạn đã chọn item " + itemName, Toast.LENGTH_SHORT).show();
        return true;
    }
}