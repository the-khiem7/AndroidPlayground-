package trinc.com.lab1.lab5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import trinc.com.lab1.R;

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

        Button btnLab51 = findViewById(R.id.btnLab51);
        Button btnLab52 = findViewById(R.id.btnLab52);

        btnLab51.setOnClickListener(v ->
                startActivity(new Intent(this, Lab51Activity.class)));
        btnLab52.setOnClickListener(v ->
                startActivity(new Intent(this, Lab52Activity.class)));
    }
}
