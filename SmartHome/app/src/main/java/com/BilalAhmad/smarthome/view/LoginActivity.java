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
import com.BilalAhmad.smarthome.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

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

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(v -> handleLogin());

        binding.tvSignUp.setOnClickListener(v->{
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }
    private void handleLogin() {
        String email = binding.etEmail.getText() != null ? binding.etEmail.getText().toString().trim() : "";
        String password = binding.etPassword.getText() != null ? binding.etPassword.getText().toString().trim() : "";

        //Validation
        if (email.isEmpty()) {
            binding.tilEmail.setError("Email is required");
            return;
        } else {
            binding.tilEmail.setError(null); // Clear error
        }

        if (password.isEmpty()) {
            binding.tilPassword.setError("Password is required");
            return;
        } else {
            binding.tilPassword.setError(null); // Clear error
        }

        //show progress bar and disable login button while authentication
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.btnLogin.setEnabled(false);

        //toast feedback
        Toast.makeText(this, "Logging in...", Toast.LENGTH_SHORT).show();

        //TODO: Authenticate user with email and password


    }
}