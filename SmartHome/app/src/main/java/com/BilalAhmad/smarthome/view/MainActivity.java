package com.BilalAhmad.smarthome.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.BilalAhmad.smarthome.R;
import com.BilalAhmad.smarthome.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Inflate binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        setupBackPressHandler();
        setupBottomNavigation();

        binding.navigationView.setNavigationItemSelectedListener(this);

        // Profile Click -> Open Side Navigation Drawer
        binding.ivUserProfile.setOnClickListener(v -> {
            if (!binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });


    }
    private void setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            //Reset all menu item views to normal scale
            for (int i = 0; i < binding.bottomNavigation.getMenu().size(); i++) {
                int menuItemId = binding.bottomNavigation.getMenu().getItem(i).getItemId();
                View itemView = binding.bottomNavigation.findViewById(menuItemId);
                if (itemView != null) {
                    itemView.animate().scaleX(1.0f).scaleY(1.0f).setDuration(150).start();
                }
            }

            // Pop effect
            View selectedView = binding.bottomNavigation.findViewById(item.getItemId());
            if (selectedView != null) {
                selectedView.animate().scaleX(1.15f).scaleY(1.15f).setDuration(200).start();
            }

            // Navigation Fragments handling logic
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                // Show Overview
            } else if (id == R.id.nav_analytics) {
                // Show Analytics
            } else if (id == R.id.nav_automation) {
                // Show Automation
            }

            return true;
        });
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Toast.makeText(this, "My Profile Clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_members) {
            Toast.makeText(this, "Family Members Clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_invite_code) {
            Toast.makeText(this, "House Invite Code Clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_settings) {
            Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
            // Intent for Login page will be attached here
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupBackPressHandler() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                }
            }
        });
    }

}