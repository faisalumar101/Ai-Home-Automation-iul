package com.BilalAhmad.smarthome.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.BilalAhmad.smarthome.databinding.ActivityLoginBinding;
import com.BilalAhmad.smarthome.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        setupObservers();
        setupClickListeners();
    }

    private void setupObservers() {
        viewModel.getIsLoading().observe(this, isLoading -> {
            binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            binding.btnLogin.setEnabled(!isLoading);
        });

        viewModel.getErrorMessage().observe(this, error -> {
            if (error != null) {
                Toast.makeText(LoginActivity.this, error, Toast.LENGTH_LONG).show();
            }
        });

        viewModel.getIsLoginSuccess().observe(this, success -> {
            if (Boolean.TRUE.equals(success)) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });

        viewModel.getIsEmailUnverified().observe(this, unverified -> {
            if (Boolean.TRUE.equals(unverified)) {
                Toast.makeText(this, "Please verify your email before logging in.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, EmailVerificationActivity.class));
                finish();
            }
        });
    }

    private void setupClickListeners() {
        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();
            viewModel.login(email, password);
        });

        binding.tvSignUp.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        });
    }
}