package com.example.lab9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Database db;
    ArrayList<CongViec> list;
    CongViecAdapter adapter;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Nếu muốn toolbar custom: setSupportActionBar(findViewById(R.id.toolbar));
        db = new Database(this);
        lv = findViewById(R.id.listView);

        loadData();

        adapter = new CongViecAdapter(this, list, new CongViecAdapter.ItemListener() {
            @Override
            public void onEdit(CongViec cv) {
                showDialogSua(cv);
            }

            @Override
            public void onDelete(CongViec cv) {
                // xóa và load lại
                int rows = db.deleteCongViec(cv.getId());
                if (rows > 0) {
                    Toast.makeText(MainActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                    adapter.setList(list);
                } else {
                    Toast.makeText(MainActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        lv.setAdapter(adapter);
        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(v -> showDialogThem());
    }

    private void loadData() {
        list = db.getAllCongViec();
    }

    // Menu: add
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_congviec, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // khi click icon +
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            showDialogThem();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialogThem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_them_cong_viec, null);
        EditText edtTen = view.findViewById(R.id.edtTen);
        EditText edtNoiDung = view.findViewById(R.id.edtNoiDung);

        builder.setView(view)
                .setTitle("Thêm công việc")
                .setPositiveButton("Thêm", (dialog, which) -> {
                    String ten = edtTen.getText().toString().trim();
                    String noidung = edtNoiDung.getText().toString().trim();
                    if (ten.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Vui lòng nhập tên công việc", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    long id = db.addCongViec(new CongViec(ten, noidung));
                    if (id > 0) {
                        Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                        adapter.setList(list);
                    } else {
                        Toast.makeText(MainActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void showDialogSua(CongViec cv) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_sua_congviec, null);
        EditText edtTen = view.findViewById(R.id.edtTen);
        EditText edtNoiDung = view.findViewById(R.id.edtNoiDung);

        // Điền dữ liệu cũ
        edtTen.setText(cv.getTen());
        edtNoiDung.setText(cv.getNoiDung());

        builder.setView(view)
                .setTitle("Cập nhật công việc")
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String tenMoi = edtTen.getText().toString().trim();
                    String noiDungMoi = edtNoiDung.getText().toString().trim();

                    if (tenMoi.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Tên không được để trống", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    cv.setTen(tenMoi);
                    cv.setNoiDung(noiDungMoi);

                    int rows = db.updateCongViec(cv);
                    if (rows > 0) {
                        Toast.makeText(MainActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                        adapter.setList(list);
                    } else {
                        Toast.makeText(MainActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

}