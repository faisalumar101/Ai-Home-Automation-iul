package com.BilalAhmad.smarthome.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.BilalAhmad.smarthome.data.repository.AuthRepository;
import com.BilalAhmad.smarthome.databinding.ActivityEmailVerificationBinding;
import com.BilalAhmad.smarthome.util.UiUtils;
import com.google.firebase.auth.FirebaseUser;

public class EmailVerificationActivity extends AppCompatActivity {

    private ActivityEmailVerificationBinding binding;
    private AuthRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEmailVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent() != null && getIntent().hasExtra("EXTRA_MESSAGE")) {
            String msg = getIntent().getStringExtra("EXTRA_MESSAGE");
            UiUtils.showSuccess(binding.getRoot(), msg);
        }

        repository = new AuthRepository();

        binding.btnIHaveVerified.setOnClickListener(v -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            repository.checkVerificationStatus(new AuthRepository.SimpleCallback() {
                @Override
                public void onSuccess() {
                    binding.progressBar.setVisibility(View.GONE);
                    repository.logout();

                    Intent intent = new Intent(EmailVerificationActivity.this, LoginActivity.class);
                    intent.putExtra("EXTRA_MESSAGE", "Account verified! Please login.");
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailure(String errorMessage) {
                    binding.progressBar.setVisibility(View.GONE);
                    UiUtils.showError(binding.getRoot(), errorMessage);
                }
            });
        });

        binding.btnResendEmail.setOnClickListener(v -> {
            repository.sendEmailVerification(new AuthRepository.SimpleCallback() {
                @Override
                public void onSuccess() {
                    UiUtils.showSuccess(binding.getRoot(), "Verification email resent!");
                }

                @Override
                public void onFailure(String errorMessage) {
                    UiUtils.showError(binding.getRoot(), errorMessage);
                }
            });
        });

        binding.tvBackToSignUp.setOnClickListener(v -> {
            FirebaseUser user = repository.getCurrentUser();
            String name = (user != null && user.getDisplayName() != null) ? user.getDisplayName() : "";
            String email = (user != null && user.getEmail() != null) ? user.getEmail() : "";
            repository.deleteUnverifiedUser(new AuthRepository.SimpleCallback() {
                @Override
                public void onSuccess() {
                    Intent intent = new Intent(EmailVerificationActivity.this, SignUpActivity.class);
                    intent.putExtra("EXTRA_NAME", name);
                    intent.putExtra("EXTRA_EMAIL", email);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailure(String errorMessage) {
                    UiUtils.showError(binding.getRoot(), errorMessage);
                }
            });
        });
    }
}