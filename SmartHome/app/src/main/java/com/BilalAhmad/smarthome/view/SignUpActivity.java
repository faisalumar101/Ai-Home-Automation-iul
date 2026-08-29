package com.BilalAhmad.smarthome.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.BilalAhmad.smarthome.R;
import com.BilalAhmad.smarthome.databinding.ActivitySignUpBinding;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.btnSignUp.setOnClickListener(v -> handleSignUp());

        binding.tvLogin.setOnClickListener(v-> finish());

    }
    private void handleSignUp(){
        String name = binding.etName.getText() != null ? binding.etName.getText().toString().trim() : "";
        String email = binding.etEmail.getText() != null ? binding.etEmail.getText().toString().trim() : "";
        String password = binding.etPassword.getText() != null ? binding.etPassword.getText().toString().trim() : "";

        //Validation
        if (name.isEmpty()) {
            binding.tilName.setError("Name is required");
            return;
        } else {
            binding.tilName.setError(null); // Clear error
        }
        if (email.isEmpty()) {
            binding.tilEmail.setError("Email is required");
            return;
        } else {
            binding.tilEmail.setError(null);
        }
        if (password.isEmpty()) {
            binding.tilPassword.setError("Password is required");
            return;
        } else {
            binding.tilPassword.setError(null);
        }
        if(password.length()<6){
            binding.tilPassword.setError("Password must be at least 6 characters");
            return;
        } else {
            binding.tilPassword.setError(null);
        }

        //show progress bar and disable login button while authentication
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.btnSignUp.setEnabled(false);

        // --- NAVIGATION & FIREBASE TRIGGER ---
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Send Verification Email
                            user.sendEmailVerification().addOnCompleteListener(emailTask -> {
                                binding.progressBar.setVisibility(View.GONE);
                                if (emailTask.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this,
                                            "Account created! Please check your email for verification.",
                                            Toast.LENGTH_LONG).show();

                                    // Navigate to Email Verification Activity
                                    Intent intent = new Intent(SignUpActivity.this, EmailVerificationActivity.class);
                                    startActivity(intent);
                                    finish(); // Prevents user from pressing Back to return to Sign Up
                                } else {
                                    Toast.makeText(SignUpActivity.this,
                                            "Failed to send verification email: " + emailTask.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                    binding.btnSignUp.setEnabled(true);
                                }
                            });
                        }
                    } else {
                        binding.progressBar.setVisibility(View.GONE);
                        binding.btnSignUp.setEnabled(true);
                        Toast.makeText(SignUpActivity.this,
                                "Registration failed: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}