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

public class DrinkActivity extends AppCompatActivity {

    ArrayList<MenuItem> drinkList;
    ArrayList<OrderItem> selectedDrinks = new ArrayList<>();
    ListView lvDrink;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        lvDrink = findViewById(R.id.lvDrink);
        btnBack = findViewById(R.id.btnBack);

        // Danh sách đồ uống
        drinkList = new ArrayList<>();
        drinkList.add(new MenuItem("Nước ngọt", "Nước ngọt có gas", 15000, R.drawable.nuocngot));
        drinkList.add(new MenuItem("Nước ép", "Nước ép: ổi, cam, dứa...", 20000, R.drawable.nuocep));
        drinkList.add(new MenuItem("Trà đào", "Trà + đào ngâm, vị ngọt thanh", 20000, R.drawable.tradao));
        drinkList.add(new MenuItem("Nước sâm", "Thảo mộc mát: bí đao, rễ tranh, bông cúc...", 18000, R.drawable.nuocsam));

        DrinkAdapter adapter = new DrinkAdapter(this, drinkList);
        lvDrink.setAdapter(adapter);

        // Click chọn số lượng
        lvDrink.setOnItemClickListener((parent, view, position, id) -> {
            MenuItem item = drinkList.get(position);

            AlertDialog.Builder builder = new AlertDialog.Builder(DrinkActivity.this);
            builder.setTitle("Chọn số lượng");

            View dialogView = getLayoutInflater().inflate(R.layout.dialog_quantity, null);
            EditText edtQuantity = dialogView.findViewById(R.id.edtQuantity);
            edtQuantity.setText("1");
            builder.setView(dialogView);

            builder.setPositiveButton("Thêm", (dialog, which) -> {
                int quantity = Integer.parseInt(edtQuantity.getText().toString());
                selectedDrinks.add(new OrderItem(item.getName(), item.getPrice(), quantity));
                Toast.makeText(DrinkActivity.this, "Đã chọn " + item.getName() + " x" + quantity, Toast.LENGTH_SHORT).show();
            });

            builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
            builder.create().show();
        });

        // Nút quay lại
        btnBack.setOnClickListener(v -> {
            Intent result = new Intent();
            result.putParcelableArrayListExtra("selectedDrinks", selectedDrinks);

            Bundle bundle = new Bundle();
            bundle.putString("note", "Đặt đơn lúc " +
                    new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date()));
            bundle.putInt("discount", 10);
            result.putExtras(bundle);

            setResult(RESULT_OK, result);
            finish();
        });
    }
}
