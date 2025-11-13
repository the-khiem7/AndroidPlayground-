package trinc.com.lab1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Simple hub to launch the different layout demos that belong to Lab 1.
 */
public class Lab1ShowcaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab1_showcase);

        Button btnLinearA = findViewById(R.id.btnLab1LinearA);
        Button btnLinearB = findViewById(R.id.btnLab1LinearB);
        Button btnRelative = findViewById(R.id.btnLab1Relative);
        Button btnConstraint = findViewById(R.id.btnLab1Constraint);

        btnLinearA.setOnClickListener(v ->
                startActivity(new Intent(this, LinearLayoutA.class)));
        btnLinearB.setOnClickListener(v ->
                startActivity(new Intent(this, LinearLayoutB.class)));
        btnRelative.setOnClickListener(v ->
                startActivity(new Intent(this, RelativeLayout.class)));
        btnConstraint.setOnClickListener(v ->
                startActivity(new Intent(this, ConstrainLayout.class)));
    }
}
