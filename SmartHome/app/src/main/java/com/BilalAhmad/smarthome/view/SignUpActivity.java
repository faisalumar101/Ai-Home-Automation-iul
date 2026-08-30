package com.BilalAhmad.smarthome.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.BilalAhmad.smarthome.databinding.ActivitySignUpBinding;
import com.BilalAhmad.smarthome.util.UiUtils;
import com.BilalAhmad.smarthome.viewmodel.SignUpViewModel;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private SignUpViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);

        //Receiving data with intent
        if (getIntent() != null) {
            String passedName = getIntent().getStringExtra("EXTRA_NAME");
            String passedEmail = getIntent().getStringExtra("EXTRA_EMAIL");

            if (passedName != null && !passedName.isEmpty()) {
                binding.etName.setText(passedName);
            }
            if (passedEmail != null && !passedEmail.isEmpty()) {
                binding.etEmail.setText(passedEmail); //
            }
        }

        setupObservers();
        setupClickListeners();
    }

    private void setupObservers() {
        viewModel.getIsLoading().observe(this, isLoading -> {
            binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            binding.btnSignUp.setEnabled(!isLoading);
        });

        viewModel.getErrorMessage().observe(this, error -> {
            if (error != null) {
                UiUtils.showError(binding.getRoot(), error);
            }
        });

        viewModel.getIsSignUpSuccess().observe(this, success -> {
            if (Boolean.TRUE.equals(success)) {
                Intent intent = new Intent(SignUpActivity.this, EmailVerificationActivity.class);
                intent.putExtra("EXTRA_MESSAGE", "Verification email sent, please check your inbox.");
                startActivity(intent);
                finish();
            }
        });
    }

    private void setupClickListeners() {
        binding.btnSignUp.setOnClickListener(v -> {
            String name = binding.etName.getText() != null ? binding.etName.getText().toString().trim() : "";
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();

            viewModel.signUp(name, email, password);
        });

        binding.tvLogin.setOnClickListener(v -> finish());
    }
}