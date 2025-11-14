package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import com.example.lab4.R;

public class Lab4Activity extends AppCompatActivity {

    private static final String TAG = "Lab4Activity";
    private static final int REQ_FOOD = 100;
    private static final int REQ_DRINK = 200;

    Button btnFood, btnDrink, btnExit, btnReset, btnConfirmOrder;
    TextView tvTotal;
    ListView lvOrder;

    ArrayList<OrderItem> allOrders = new ArrayList<>();
    OrderAdapter orderAdapter;
    String orderNote = null;
    int totalAmount = 0;
    int discountPercent = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab4);
        Log.d(TAG, "onCreate: Gọi khi activity được tạo");

        btnFood = findViewById(R.id.btnFood);
        btnDrink = findViewById(R.id.btnDrink);
        btnExit = findViewById(R.id.btnExit);
        btnReset = findViewById(R.id.btnReset);
        btnConfirmOrder = findViewById(R.id.btnConfirmOrder);
        lvOrder = findViewById(R.id.lvOrder);
        tvTotal = findViewById(R.id.tvTotal);

        // setup adapter
        orderAdapter = new OrderAdapter(this, allOrders);
        orderAdapter.setOnOrderChangeListener(this::updateTotal);
        lvOrder.setAdapter(orderAdapter);

        btnFood.setOnClickListener(v -> {
            Intent intent = new Intent(Lab4Activity.this, FoodActivity.class);
            startActivityForResult(intent, REQ_FOOD);
        });

        btnDrink.setOnClickListener(v -> {
            Intent intent = new Intent(Lab4Activity.this, DrinkActivity.class);
            startActivityForResult(intent, REQ_DRINK);
        });

        btnExit.setOnClickListener(v -> finish());

        btnReset.setOnClickListener(v -> {
            allOrders.clear();
            totalAmount = 0;
            discountPercent = 0;
            orderAdapter.notifyDataSetChanged();
            tvTotal.setText("Tổng tiền: 0 VNĐ");
        });

        btnConfirmOrder.setOnClickListener(v -> confirmOrder());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: Activity sắp hiển thị cho người dùng");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Activity đang ở foreground (tương tác được)");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Activity sắp dừng (chuẩn bị chuyển sang activity khác)");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: Activity không còn hiển thị với người dùng");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Activity bị hủy, giải phóng tài nguyên");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            ArrayList<OrderItem> items = null;

            if (requestCode == REQ_FOOD) {
                items = data.getParcelableArrayListExtra("selectedFoods");
            } else if (requestCode == REQ_DRINK) {
                items = data.getParcelableArrayListExtra("selectedDrinks");
            }

            if (items != null) {
                for (OrderItem newItem : items) {
                    boolean found = false;
                    for (OrderItem oldItem : allOrders) {
                        if (oldItem.getName().equals(newItem.getName())) {
                            // cộng dồn số lượng nếu trùng
                            oldItem.setQuantity(oldItem.getQuantity() + newItem.getQuantity());
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        allOrders.add(newItem);
                    }
                }
                orderAdapter.notifyDataSetChanged();
            }

            // Nhận discount
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                discountPercent = bundle.getInt("discount", 0);
                orderNote = bundle.getString("note");
            }

            updateTotal();
        }
    }

    private void updateTotal() {
        totalAmount = 0;
        for (OrderItem order : allOrders) {
            totalAmount += order.getPrice() * order.getQuantity();
        }
        tvTotal.setText("Tổng tiền tạm tính: " + totalAmount + " VNĐ");
    }

    private void confirmOrder() {
        int discountValue = 0;

        StringBuilder result = new StringBuilder();

        // 1. Tạm tính
        result.append("Tạm tính: ").append(totalAmount).append(" VNĐ\n");

        // 2. Giảm giá nếu có
        if (discountPercent > 0 && totalAmount > 100000) {
            discountValue = totalAmount * discountPercent / 100;
            result.append("Giảm giá: -")
                    .append(discountValue)
                    .append(" VNĐ (")
                    .append(discountPercent)
                    .append("%)\n");
            totalAmount -= discountValue;
        }

        // 3. Tổng tiền cuối
        result.append("Tổng tiền: ").append(totalAmount).append(" VNĐ\n");
        if (orderNote != null) {
            result.append("Ghi chú: ").append(orderNote);
        }

        tvTotal.setText(result.toString());
    }

}
