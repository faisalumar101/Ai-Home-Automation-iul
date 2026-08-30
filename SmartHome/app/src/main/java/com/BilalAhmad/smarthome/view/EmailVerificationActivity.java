package com.BilalAhmad.smarthome.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.BilalAhmad.smarthome.data.repository.AuthRepository;
import com.BilalAhmad.smarthome.databinding.ActivityEmailVerificationBinding;
import com.google.firebase.auth.FirebaseUser;

public class EmailVerificationActivity extends AppCompatActivity {

    private ActivityEmailVerificationBinding binding;
    private AuthRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEmailVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new AuthRepository();

        binding.btnIHaveVerified.setOnClickListener(v -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            repository.checkVerificationStatus(new AuthRepository.SimpleCallback() {
                @Override
                public void onSuccess() {
                    binding.progressBar.setVisibility(View.GONE);

                    Toast.makeText(EmailVerificationActivity.this, "Email verified successfully! please login.", Toast.LENGTH_SHORT).show();
                    repository.logout();
                    startActivity(new Intent(EmailVerificationActivity.this, LoginActivity.class));
                    finish();
                }

                @Override
                public void onFailure(String errorMessage) {
                    Toast.makeText(EmailVerificationActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        });

        binding.btnResendEmail.setOnClickListener(v -> {
            repository.sendEmailVerification(new AuthRepository.SimpleCallback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(EmailVerificationActivity.this, "Verification email resent!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(String errorMessage) {
                    Toast.makeText(EmailVerificationActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(EmailVerificationActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}