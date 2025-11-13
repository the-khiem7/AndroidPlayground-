package trinc.com.lab1.lab3;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;


import trinc.com.lab1.R;

public class Lab3Activity extends AppCompatActivity {

    ListView lvSach;
    ArrayList<Sach> sach;
    SachAdapter sachAdapter;
    Button btnAdd;
    Button btnUpdate;

    Button btnDelete;
    int selectedPosition = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab3);
        mapping();
        sachAdapter = new SachAdapter(this, R.layout.sach_list, sach);
        lvSach.setAdapter(sachAdapter);
        // thêm mới bằng popup form
        btnAdd.setOnClickListener(v -> showAddDialog());
        lvSach.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position; // lưu lại item được chọn
            Toast.makeText(this, "Đã chọn: " + sach.get(position).getTen(), Toast.LENGTH_SHORT).show();
        });
        btnUpdate.setOnClickListener(v -> {
            if (selectedPosition == -1) {
                Toast.makeText(this, "Hãy chọn một sách trước!", Toast.LENGTH_SHORT).show();
            } else {
                showEditDialog(selectedPosition);
            }
        });
        btnDelete.setOnClickListener(v -> {
            if (selectedPosition == -1) {
                Toast.makeText(this, "Hãy chọn một sách trước khi xóa!", Toast.LENGTH_SHORT).show();
            } else {
                showDeleteDialog(selectedPosition);
            }
        });

    }

    private void mapping() {
        lvSach = findViewById(R.id.lvSach);
        sach = new ArrayList<>();
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        try {
            sach.add(new Sach("Cây cam ngọt của tôi", "truyện ngắn", R.drawable.caycam));
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void showAddDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_sach, null);
        EditText edtTenSach = dialogView.findViewById(R.id.edtTenSach);
        EditText edtTheLoai = dialogView.findViewById(R.id.edtTheLoai);
        Spinner spnHinh = dialogView.findViewById(R.id.spnHinh);

        // Danh sách ảnh sẵn có
        String[] arrHinh = {"caycam", "conan", "doremon"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrHinh);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnHinh.setAdapter(adapter);

        new AlertDialog.Builder(this)
                .setTitle("Thêm sách mới")
                .setView(dialogView)
                .setPositiveButton("Thêm", (dialog, which) -> {
                    String ten = edtTenSach.getText().toString();
                    String theLoai = edtTheLoai.getText().toString();
                    String hinhName = spnHinh.getSelectedItem().toString();

                    // Lấy id ảnh theo tên
                    int resId = getResources().getIdentifier(hinhName, "drawable", getPackageName());

                    sach.add(new Sach(ten, theLoai, resId));
                    sachAdapter.notifyDataSetChanged();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void showEditDialog(int position) {
        Sach s = sach.get(position);

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_sach, null);
        EditText edtTenSach = dialogView.findViewById(R.id.edtTenSach);
        EditText edtTheLoai = dialogView.findViewById(R.id.edtTheLoai);
        Spinner spnHinh = dialogView.findViewById(R.id.spnHinh);

        String[] arrHinh = {"caycam", "conan", "doremon"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrHinh);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnHinh.setAdapter(adapter);

        // điền dữ liệu cũ
        edtTenSach.setText(s.getTen());
        edtTheLoai.setText(s.getTheLoai());

        String currentImageName = getResources().getResourceEntryName(s.getHinh());
        int index = Arrays.asList(arrHinh).indexOf(currentImageName);
        if (index >= 0) spnHinh.setSelection(index);

        new AlertDialog.Builder(this)
                .setTitle("Cập nhật sách")
                .setView(dialogView)
                .setPositiveButton("Cập nhật", (dialog, which) -> {
                    s.setTen(edtTenSach.getText().toString());
                    s.setTheLoai(edtTheLoai.getText().toString());
                    String hinhName = spnHinh.getSelectedItem().toString();
                    int resId = getResources().getIdentifier(hinhName, "drawable", getPackageName());
                    s.setHinh(resId);
                    sachAdapter.notifyDataSetChanged();
                    selectedPosition = -1; // reset chọn
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
    private void showDeleteDialog(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa sách")
                .setMessage("Bạn có chắc chắn muốn xóa \"" + sach.get(position).getTen() + "\" không?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    sach.remove(position);
                    sachAdapter.notifyDataSetChanged();
                    selectedPosition = -1; // reset lại chọn
                    Toast.makeText(this, "Đã xóa sách!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }


}