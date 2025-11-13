package trinc.com.lab1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import trinc.com.lab1.lab3.Lab31Activity;
import trinc.com.lab1.lab3.Lab3Activity;
import trinc.com.lab1.lab4.Lab4Activity;
import trinc.com.lab1.lab5.Lab51Activity;
import trinc.com.lab1.lab5.Lab52Activity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLinearA = findViewById(R.id.btnLinearA);
        Button btnLinearB = findViewById(R.id.btnLinearB);
        Button btnRelative = findViewById(R.id.btnRelative);
        Button btnConstraint = findViewById(R.id.btnConstraint);
        Button btnLab2 = findViewById(R.id.btnlab2);
        Button btnLab3 = findViewById(R.id.btnlab3);
        Button btnLab31 = findViewById(R.id.btnlab31);
        Button btnLab4 = findViewById(R.id.btnlab4);
        Button btnLab51 = findViewById(R.id.btnlab51);
        Button btnLab52 = findViewById(R.id.btnlab52);



        btnLinearA.setOnClickListener(v ->
                startActivity(new Intent(this, LinearLayoutA.class)));

        btnLinearB.setOnClickListener(v ->
                startActivity(new Intent(this, LinearLayoutB.class)));

        btnRelative.setOnClickListener(v ->
                startActivity(new Intent(this, RelativeLayout.class)));

        btnConstraint.setOnClickListener(v ->
                startActivity(new Intent(this, ConstrainLayout.class)));

        btnLab2.setOnClickListener(v ->
                startActivity(new Intent(this, Lab2Activity.class)));

        btnLab31.setOnClickListener(v ->
                startActivity(new Intent(this, Lab31Activity.class)));

        btnLab3.setOnClickListener(v ->
                startActivity(new Intent(this, Lab3Activity.class)));

        btnLab4.setOnClickListener(v ->
                startActivity(new Intent(this, Lab4Activity.class)));

        btnLab51.setOnClickListener(v ->
                startActivity(new Intent(this, Lab51Activity.class)));

        btnLab52.setOnClickListener(v ->
                startActivity(new Intent(this, Lab52Activity.class)));
    }
}
