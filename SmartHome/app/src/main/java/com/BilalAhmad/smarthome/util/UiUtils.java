package com.BilalAhmad.smarthome.util;

import android.graphics.Color;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class UiUtils {
    // Success Snackbar (Green)
    public static void showSuccess(View rootView, String message) {
        // Success Snackbar (Green)
        if (rootView == null) return;
            Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG);
            snackbar.setBackgroundTint(Color.parseColor("#4CAF50")); // Green
            snackbar.setTextColor(Color.WHITE);
            snackbar.show();
        }

        // Error Snackbar (Red)
        public static void showError(View rootView, String message) {
            if (rootView == null) return;
            Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG);
            snackbar.setBackgroundTint(Color.parseColor("#E53935")); // Red
            snackbar.setTextColor(Color.WHITE);
            snackbar.show();
        }

        // Info/Warning Snackbar (Dark Gray)
        public static void showInfo(View rootView, String message) {
            if (rootView == null) return;
            Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG);
            snackbar.setBackgroundTint(Color.parseColor("#323232")); // Charcoal
            snackbar.setTextColor(Color.WHITE);
            snackbar.show();
        }
}
