package com.example.lab6;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab6.exercise1.Exercise1Activity;
import com.example.lab6.exercise2.Exercise2Activity;
import com.example.lab6.exercise3.Exercise3Activity;

/**
 * Presents quick shortcuts to the three option menu exercises.
 */
public class Lab6MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab6_menu);

        Button btnExercise1 = findViewById(R.id.btnLab6Exercise1);
        Button btnExercise2 = findViewById(R.id.btnLab6Exercise2);
        Button btnExercise3 = findViewById(R.id.btnLab6Exercise3);

        btnExercise1.setOnClickListener(v ->
                startActivity(new Intent(this, Exercise1Activity.class)));
        btnExercise2.setOnClickListener(v ->
                startActivity(new Intent(this, Exercise2Activity.class)));
        btnExercise3.setOnClickListener(v ->
                startActivity(new Intent(this, Exercise3Activity.class)));
    }
}
