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
import com.BilalAhmad.smarthome.databinding.ActivityEmailVerificationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailVerificationActivity extends AppCompatActivity {
    private ActivityEmailVerificationBinding binding;
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
        binding = ActivityEmailVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();


    }
    private void checkEmailVerificationStatus(){
        FirebaseUser user = mAuth.getCurrentUser();
        if(user == null){
            Toast.makeText(this, "Session Expired Please Login Again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        binding.progressBar.setVisibility(View.VISIBLE);
        // Reload user status from Firebase to fetch the latest isEmailVerified value
        user.reload().addOnCompleteListener(task -> {
            binding.progressBar.setVisibility(View.GONE);
            if (task.isSuccessful()) {
                if (user.isEmailVerified()) {
                    Toast.makeText(this, "Email verified successfully!", Toast.LENGTH_SHORT).show();
                     startActivity(new Intent(this, MainActivity.class));
                     finish();

                } else {
                    Toast.makeText(this, "Email is not verified yet. Please check your inbox.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Failed to check status. Try again.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void resendVerificationEmail() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            binding.progressBar.setVisibility(View.VISIBLE);
            user.sendEmailVerification().addOnCompleteListener(task -> {
                binding.progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Toast.makeText(this, "A new verification link has been sent to your email.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed to send email. Try again shortly.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}