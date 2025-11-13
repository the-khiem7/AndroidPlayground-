package com.example.prm392_lab_11_api;


import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.prm392_lab_11_api.adapter.TraineeAdapter;
import com.example.prm392_lab_11_api.model.Trainee;
import com.example.prm392_lab_11_api.repository.TraineeRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TraineeRepository repo;
    private TraineeAdapter adapter;
    private SwipeRefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main_lab11);

            // Repo
            repo = new TraineeRepository();

            RecyclerView rv = findViewById(R.id.recyclerTrainees);
            rv.setLayoutManager(new LinearLayoutManager(this));
            adapter = new TraineeAdapter(new TraineeAdapter.OnItemAction() {
                @Override
                public void onEdit(Trainee t) {
                    showCreateEditDialog(t);
                }

                @Override
                public void onDelete(Trainee t) {
                    confirmDelete(t);
                }
            });
            rv.setAdapter(adapter);

            swipe = findViewById(R.id.swipe);
            swipe.setOnRefreshListener(this::loadData);

            FloatingActionButton fab = findViewById(R.id.fabAdd);
            fab.setOnClickListener(v -> showCreateEditDialog(null));

            loadData();
        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate: " + e.getMessage(), e);
            Toast.makeText(this, "Error initializing app: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void loadData() {
        swipe.setRefreshing(true);
        try {
            repo.getAll().enqueue(new Callback<List<Trainee>>() {
                @Override
                public void onResponse(Call<List<Trainee>> call, Response<List<Trainee>> response) {
                    swipe.setRefreshing(false);
                    if (response.isSuccessful()) {
                        adapter.setItems(response.body());
                    } else {
                        Log.e(TAG, "Load failed: " + response.code());
                        Toast.makeText(MainActivity.this, "Load failed: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Trainee>> call, Throwable t) {
                    swipe.setRefreshing(false);
                    Log.e(TAG, "Error loading data: " + t.getMessage(), t);
                    Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            swipe.setRefreshing(false);
            Log.e(TAG, "Exception in loadData: " + e.getMessage(), e);
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showCreateEditDialog(Trainee existing) {
        boolean isEdit = existing != null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_trainee, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        TextInputEditText etName = view.findViewById(R.id.etName);
        TextInputEditText etEmail = view.findViewById(R.id.etEmail);
        TextInputEditText etPhone = view.findViewById(R.id.etPhone);
        TextInputEditText etPosition = view.findViewById(R.id.etPosition);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        Button btnSave = view.findViewById(R.id.btnSave);

        if (isEdit) {
            etName.setText(existing.getName());
            etEmail.setText(existing.getEmail());
            etPhone.setText(existing.getPhone());
            etPosition.setText(existing.getPosition());
        }

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnSave.setOnClickListener(v -> {
            String name = etName.getText() == null ? "" : etName.getText().toString().trim();
            String email = etEmail.getText() == null ? "" : etEmail.getText().toString().trim();
            String phone = etPhone.getText() == null ? "" : etPhone.getText().toString().trim();
            String position = etPosition.getText() == null ? "" : etPosition.getText().toString().trim();

            if (name.isEmpty()) {
                etName.setError("Name required");
                return;
            }

            if (isEdit) {
                existing.setName(name);
                existing.setEmail(email);
                existing.setPhone(phone);
                existing.setPosition(position);
                repo.update(existing.getId(), existing).enqueue(new Callback<Trainee>() {
                    @Override
                    public void onResponse(Call<Trainee> call, Response<Trainee> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                            loadData();
                        } else {
                            Toast.makeText(MainActivity.this, "Update failed: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Trainee> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Trainee t = new Trainee(name, email, phone, position);
                repo.create(t).enqueue(new Callback<Trainee>() {
                    @Override
                    public void onResponse(Call<Trainee> call, Response<Trainee> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Created", Toast.LENGTH_SHORT).show();
                            loadData();
                        } else {
                            Toast.makeText(MainActivity.this, "Create failed: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Trainee> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        dialog.show();
    }

    private void confirmDelete(Trainee t) {
        new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Delete " + t.getName() + "?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    repo.delete(t.getId()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                                loadData();
                            } else {
                                Toast.makeText(MainActivity.this, "Delete failed: " + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t1) {
                            Toast.makeText(MainActivity.this, "Error: " + t1.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
