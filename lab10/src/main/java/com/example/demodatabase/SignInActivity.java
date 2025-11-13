package com.example.demodatabase;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.demodatabase.data.local.TokenManager;
import com.example.demodatabase.data.remote.ApiClient;
import com.example.demodatabase.data.remote.dto.LoginRequest;
import com.example.demodatabase.ui.product.ListProductActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    private TextInputEditText edtUsername, edtPassword;
    private MaterialButton btnSignIn;
    private TextView tvSignUp;

    private CharSequence btnLabelBackup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets sys = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(sys.left, sys.top, sys.right, sys.bottom);
            return insets;
        });


        ApiClient.init(getApplicationContext());

        // Clear tokens when using mock data to force login (for testing)
        if (com.example.demodatabase.data.mock.MockDataProvider.USE_MOCK_DATA) {
            com.example.demodatabase.data.local.TokenManager.get(getApplicationContext()).clear();
        }

        String token = com.example.demodatabase.data.local.TokenManager
                .get(getApplicationContext())
                .getAccessToken();

        if (token != null && !com.example.demodatabase.data.remote.JwtUtils.isExpired(token, 30)) {

            goToListProduct();
            return;
        } else if (token != null) {

            com.example.demodatabase.data.local.TokenManager.get(getApplicationContext()).clear();
        }


        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignIn   = findViewById(R.id.btnSignIn);
        tvSignUp    = findViewById(R.id.tvSignUp);

        btnLabelBackup = btnSignIn.getText();


        TextWatcher watcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { checkInputFields(); }
            @Override public void afterTextChanged(Editable s) {}
        };
        edtUsername.addTextChangedListener(watcher);
        edtPassword.addTextChangedListener(watcher);
        checkInputFields();


        tvSignUp.setOnClickListener(v -> {
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
        });


        btnSignIn.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        String username = safeText(edtUsername);
        String password = safeText(edtPassword);

        boolean ok = true;
        if (username.isEmpty()) { edtUsername.setError("Username is required"); ok = false; }
        else { edtUsername.setError(null); }

        if (password.isEmpty()) { edtPassword.setError("Password is required"); ok = false; }
        else { edtPassword.setError(null); }

        if (!ok) return;

        setLoading(true);

        // Use MockApiWrapper - automatically switches between mock and real API
        com.example.demodatabase.data.mock.MockApiWrapper.login(new LoginRequest(username, password))
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> resp) {
                        setLoading(false);

                        if (!resp.isSuccessful() || resp.body() == null) {
                            Toast.makeText(SignInActivity.this,
                                    "Login failed: HTTP " + resp.code(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String token = resp.body();
                        if (token != null) token = token.trim().replace("\"", "");

                        if (token == null || token.isEmpty()) {
                            Toast.makeText(SignInActivity.this,
                                    "Login failed: empty token", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        TokenManager.get(getApplicationContext()).save("Bearer", token, null);

                        Toast.makeText(SignInActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                        goToListProduct();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        setLoading(false);
                        Toast.makeText(SignInActivity.this,
                                "Login error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void goToListProduct() {
        Intent i = new Intent(SignInActivity.this, ListProductActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }


    private void setLoading(boolean loading) {
        btnSignIn.setEnabled(!loading);
        if (loading) {
            btnSignIn.setText("Signing in…");

            btnSignIn.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.darker_gray));
            btnSignIn.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        } else {
            btnSignIn.setText(btnLabelBackup);

            checkInputFields();
        }
    }


    private void checkInputFields() {
        String u = safeText(edtUsername);
        String p = safeText(edtPassword);
        boolean enable = !u.isEmpty() && !p.isEmpty();

        btnSignIn.setEnabled(enable);
        if (enable) {

            btnSignIn.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.black));
            btnSignIn.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        } else {

            btnSignIn.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.darker_gray));
            btnSignIn.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        }
    }

    private String safeText(TextInputEditText et) {
        return et.getText() == null ? "" : et.getText().toString().trim();
    }
}
