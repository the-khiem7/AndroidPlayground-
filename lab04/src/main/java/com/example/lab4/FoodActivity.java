package com.example.lab4;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.example.lab4.R;

public class FoodActivity extends AppCompatActivity {

    ArrayList<MenuItem> foodList;
    ArrayList<OrderItem> selectedFoods = new ArrayList<>();
    ListView lvFood;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        lvFood = findViewById(R.id.lvFood);
        btnBack = findViewById(R.id.btnBack);

        // Danh sách món ăn
        foodList = new ArrayList<>();
        foodList.add(new MenuItem("Bánh bèo chén", "Đặc sản miền Trung", 30000, R.drawable.banhbeo));
        foodList.add(new MenuItem("Bún Bò Huế", "Cay nồng đúng vị Huế", 55000, R.drawable.bunbo));
        foodList.add(new MenuItem("Bánh cuốn Tây Sơn", "Đặc sản Bình Định", 20000, R.drawable.banhcuon));
        foodList.add(new MenuItem("Bún Thịt Nướng", "Thịt nướng, rau sống, đồ chua, nước mắm", 40000, R.drawable.bunthitnuong));

        FoodAdapter adapter = new FoodAdapter(this, foodList);
        lvFood.setAdapter(adapter);

        // Click item để chọn số lượng
        lvFood.setOnItemClickListener((parent, view, position, id) -> {
            MenuItem item = foodList.get(position);

            AlertDialog.Builder builder = new AlertDialog.Builder(FoodActivity.this);
            builder.setTitle("Chọn số lượng");

            View dialogView = getLayoutInflater().inflate(R.layout.dialog_quantity, null);
            EditText edtQuantity = dialogView.findViewById(R.id.edtQuantity);
            edtQuantity.setText("1");
            builder.setView(dialogView);

            builder.setPositiveButton("Thêm", (dialog, which) -> {
                int quantity = Integer.parseInt(edtQuantity.getText().toString());
                selectedFoods.add(new OrderItem(item.getName(), item.getPrice(), quantity));
                Toast.makeText(FoodActivity.this, "Đã chọn " + item.getName() + " x" + quantity, Toast.LENGTH_SHORT).show();
            });

            builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
            builder.create().show();
        });

        // Nút quay lại
        btnBack.setOnClickListener(v -> {
            Intent result = new Intent();
            result.putParcelableArrayListExtra("selectedFoods", selectedFoods);

            // gửi thêm Bundle (ghi chú, giảm giá)
            Bundle bundle = new Bundle();
            bundle.putString("note", "Đặt đơn lúc " +
                    new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date()));
            bundle.putInt("discount", 10); // giảm giá 10% nếu > 100k
            result.putExtras(bundle);

            setResult(RESULT_OK, result);
            finish();
        });
    }
}
