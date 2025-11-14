package com.example.lab13;

import com.example.NguyenVanDuyKhiem_SE180168_Lab13_AuthWithEmail.R;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnSignUp, btnSignIn, btnSignOut, btnVerifyEmail, btnCheckVerification;
    private TextView tvStatus;
    private FirebaseAuth mAuth;

    // New view for success animation
    private ImageView ivSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lab13);

        // Initialize Firebase Auth using the custom helper (google-services plugin is not applied)
        mAuth = FirebaseConfigHelper.provideAuth(this);

        // Initialize views
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignOut = findViewById(R.id.btnSignOut);
        btnVerifyEmail = findViewById(R.id.btnVerifyEmail);
        btnCheckVerification = findViewById(R.id.btnCheckVerification);
        tvStatus = findViewById(R.id.tvStatus);
        ivSuccess = findViewById(R.id.ivSuccess);

        // Start entrance animation for the card container
        View card = findViewById(R.id.cardContainer);
        Animation enter = AnimationUtils.loadAnimation(this, R.anim.card_enter);
        card.startAnimation(enter);

        // Set click listeners
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        btnVerifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationEmail();
            }
        });

        btnCheckVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailVerification();
            }
        });

        // Check current user
        updateUI(mAuth.getCurrentUser());
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void signUp() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up success - Send verification email
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                user.sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(MainActivity.this,
                                                            "Sign up successful! Verification email sent to " + user.getEmail(),
                                                            Toast.LENGTH_LONG).show();
                                                } else {
                                                    Toast.makeText(MainActivity.this,
                                                            "Failed to send verification email",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                            updateUI(user);
                        } else {
                            // Sign up fails
                            Toast.makeText(MainActivity.this, "Sign up failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void signIn() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            Toast.makeText(MainActivity.this, "Sign in successful!", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // Sign in fails
                            Toast.makeText(MainActivity.this, "Sign in failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void signOut() {
        mAuth.signOut();
        Toast.makeText(this, "Signed out successfully", Toast.LENGTH_SHORT).show();
        updateUI(null);
    }

    private void sendVerificationEmail() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this,
                                        "Verification email sent to " + user.getEmail(),
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(MainActivity.this,
                                        "Failed to send verification email: " + task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkEmailVerification() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        updateUI(mAuth.getCurrentUser());
                        if (user.isEmailVerified()) {
                            // Play success animated vector
                            if (ivSuccess != null) {
                                ivSuccess.setVisibility(View.VISIBLE);
                                AnimatedVectorDrawableCompat avd = AnimatedVectorDrawableCompat.create(MainActivity.this, R.drawable.avd_check);
                                ivSuccess.setImageDrawable(avd);
                                if (avd != null) avd.start();

                                // Hide after a short delay
                                ivSuccess.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        ivSuccess.setVisibility(View.GONE);
                                    }
                                }, 1400);
                            }

                            Toast.makeText(MainActivity.this,
                                    "Email is verified!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this,
                                    "Email is not verified yet. Please check your inbox.",
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this,
                                "Failed to check verification status",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            String verificationStatus = user.isEmailVerified() ? " (Verified ✓)" : " (Not Verified ✗)";
            tvStatus.setText("Status: Logged in as " + user.getEmail() + verificationStatus);
            btnSignOut.setEnabled(true);
            btnSignUp.setEnabled(false);
            btnSignIn.setEnabled(false);
            btnVerifyEmail.setEnabled(!user.isEmailVerified());
            btnCheckVerification.setEnabled(true);
        } else {
            tvStatus.setText("Status: Not logged in");
            btnSignOut.setEnabled(false);
            btnSignUp.setEnabled(true);
            btnSignIn.setEnabled(true);
            btnVerifyEmail.setEnabled(false);
            btnCheckVerification.setEnabled(false);
        }
    }
}
