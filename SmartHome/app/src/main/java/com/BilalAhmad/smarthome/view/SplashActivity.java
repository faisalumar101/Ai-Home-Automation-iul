package com.BilalAhmad.smarthome.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.BilalAhmad.smarthome.databinding.ActivitySplashBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;
    private FirebaseAuth mAuth;

    // Animation Duration in milliseconds
//    private static final long ANIMATION_DURATION = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        mAuth = FirebaseAuth.getInstance();

        // Start the intro animation
        startSplashAnimation();

    }


    private void startSplashAnimation() {
        // Direct container animation
        View titleContainer = binding.llSplashTitleContainer;

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(titleContainer, View.SCALE_X, 1.0f, 0.7f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(titleContainer, View.SCALE_Y, 1.0f, 0.7f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(titleContainer, View.ALPHA, 1.0f, 0.0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY, alpha);
        animatorSet.setStartDelay(1500);
        animatorSet.setDuration(1200);

        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                checkUserAuthentication();
            }
        });
    }

    private void checkUserAuthentication() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {

            currentUser.reload().addOnCompleteListener(task -> {

                if (task.isSuccessful() && mAuth.getCurrentUser() != null && mAuth.getCurrentUser().isEmailVerified()) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, EmailVerificationActivity.class));
                }
                finish();
            }).addOnFailureListener(e -> {
                // Fallback if network fails during reload
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();

            });
        } else {

            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();

        }
    }
}