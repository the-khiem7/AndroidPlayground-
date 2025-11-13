package trinc.com.lab1.lab3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import trinc.com.lab1.R;

/**
 * Entry point for Lab 3 that lets the user jump to either exercise variant.
 */
public class Lab3ShowcaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab3_showcase);

        Button btnLab30 = findViewById(R.id.btnLab3Main);
        Button btnLab31 = findViewById(R.id.btnLab31);

        btnLab30.setOnClickListener(v ->
                startActivity(new Intent(this, Lab3Activity.class)));
        btnLab31.setOnClickListener(v ->
                startActivity(new Intent(this, Lab31Activity.class)));
    }
}
