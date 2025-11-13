package trinc.com.lab5;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import trinc.com.lab5.R;

public class Lab52Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    ModuleAdapter moduleAdapter;
    List<Module> moduleList;
    FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab5_2);

        recyclerView = findViewById(R.id.recyclerViewModules);
        btnAdd = findViewById(R.id.btnAdd);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        moduleList = new ArrayList<>();
        moduleList.add(new Module(R.drawable.android, "ListView trong Android",
                "ListView trong Android là một thành phần dùng để nhóm nhiều mục (item)...", "Android"));
        moduleList.add(new Module(R.drawable.ios, "Xử lý sự kiện trong iOS",
                "Xử lý sự kiện trong iOS. Sau khi các bạn đã biết cách thiết kế giao diện...", "iOS"));

        moduleAdapter = new ModuleAdapter(moduleList);
        recyclerView.setAdapter(moduleAdapter);

        // Thêm mới item
        btnAdd.setOnClickListener(v -> showAddDialog());

        // Cập nhật / Xóa
        moduleAdapter.setOnItemClickListener(position -> showEditDialog(position));
    }

    private void showAddDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit, null);
        EditText edtTitle = dialogView.findViewById(R.id.edtTitle);
        EditText edtDesc = dialogView.findViewById(R.id.edtDesc);
        EditText edtPlatform = dialogView.findViewById(R.id.edtPlatform);

        new AlertDialog.Builder(this)
                .setTitle("Thêm Module mới")
                .setView(dialogView)
                .setPositiveButton("Thêm", (d, which) -> {
                    moduleList.add(new Module(R.drawable.android,
                            edtTitle.getText().toString(),
                            edtDesc.getText().toString(),
                            edtPlatform.getText().toString()));
                    moduleAdapter.notifyItemInserted(moduleList.size() - 1);
                    Toast.makeText(this, "Đã thêm module mới!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void showEditDialog(int position) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit, null);
        EditText edtTitle = dialogView.findViewById(R.id.edtTitle);
        EditText edtDesc = dialogView.findViewById(R.id.edtDesc);
        EditText edtPlatform = dialogView.findViewById(R.id.edtPlatform);

        // Set dữ liệu cũ
        Module module = moduleList.get(position);
        edtTitle.setText(module.getTitle());
        edtDesc.setText(module.getDescription());
        edtPlatform.setText(module.getPlatform());

        new AlertDialog.Builder(this)
                .setTitle("Cập nhật hoặc xóa Module")
                .setView(dialogView)
                .setPositiveButton("Cập nhật", (d, which) -> {
                    moduleList.set(position, new Module(module.getImageResId(),
                            edtTitle.getText().toString(),
                            edtDesc.getText().toString(),
                            edtPlatform.getText().toString()));
                    moduleAdapter.notifyItemChanged(position);
                    Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Xóa", (d, which) -> {
                    moduleList.remove(position);
                    moduleAdapter.notifyItemRemoved(position);
                    Toast.makeText(this, "Đã xóa module!", Toast.LENGTH_SHORT).show();
                })
                .setNeutralButton("Hủy", null)
                .show();
    }
}
