package trinc.com.lab1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Lab2Activity extends AppCompatActivity {

    // --- KHAI BÁO BIẾN ---
    EditText txtMin, txtMax, edtSo1, edtSo2;
    TextView txtResult, txtKetQua;
    Button btnGenerate, btnCong, btnTru, btnNhan, btnChia;
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab2); // layout đã gộp 2 phần

        // --- ÁNH XẠ VIEW ---
        // Phần Random
        txtMin = findViewById(R.id.txtMin);
        txtMax = findViewById(R.id.txtMax);
        txtResult = findViewById(R.id.txtResult);
        btnGenerate = findViewById(R.id.btnGenerate);

        // Phần Máy tính
        edtSo1 = findViewById(R.id.edtSo1);
        edtSo2 = findViewById(R.id.edtSo2);
        txtKetQua = findViewById(R.id.txtKetQua);
        btnCong = findViewById(R.id.btnCong);
        btnTru = findViewById(R.id.btnTru);
        btnNhan = findViewById(R.id.btnNhan);
        btnChia = findViewById(R.id.btnChia);

        // --- XỬ LÝ RANDOM ---
        btnGenerate.setOnClickListener(v -> {
            try {
                int min = Integer.parseInt(txtMin.getText().toString());
                int max = Integer.parseInt(txtMax.getText().toString());

                if (min > max) {
                    Toast.makeText(Lab2Activity.this, "Min phải <= Max!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int result = random.nextInt((max - min) + 1) + min;
                txtResult.setText("Result: " + result);

            } catch (Exception e) {
                Toast.makeText(Lab2Activity.this, "Nhập số hợp lệ!", Toast.LENGTH_SHORT).show();
            }
        });

        // --- XỬ LÝ MÁY TÍNH ---
        btnCong.setOnClickListener(v -> tinhToan('+'));
        btnTru.setOnClickListener(v -> tinhToan('-'));
        btnNhan.setOnClickListener(v -> tinhToan('*'));
        btnChia.setOnClickListener(v -> tinhToan('/'));
    }

    // --- HÀM TÍNH TOÁN ---
    private void tinhToan(char phepTinh) {
        String s1 = edtSo1.getText().toString();
        String s2 = edtSo2.getText().toString();

        if (s1.isEmpty() || s2.isEmpty()) {
            txtKetQua.setText("Vui lòng nhập đủ 2 số");
            return;
        }

        double a = Double.parseDouble(s1);
        double b = Double.parseDouble(s2);
        double kq = 0;

        switch (phepTinh) {
            case '+': kq = a + b; break;
            case '-': kq = a - b; break;
            case '*': kq = a * b; break;
            case '/':
                if (b == 0) {
                    txtKetQua.setText("Không thể chia cho 0");
                    return;
                }
                kq = a / b;
                break;
        }
        txtKetQua.setText("Kết quả: " + kq);
    }
}
