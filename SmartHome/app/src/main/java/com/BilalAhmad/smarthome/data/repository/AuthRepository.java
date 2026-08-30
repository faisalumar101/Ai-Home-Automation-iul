package com.BilalAhmad.smarthome.data.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class AuthRepository {
    private final FirebaseAuth mAuth;

    public AuthRepository() {
        this.mAuth = FirebaseAuth.getInstance();
    }

    public interface AuthCallback{
        void onSuccess(FirebaseUser user);
        void onFailure(String errorMessage);
    }
    public interface SimpleCallback {
        void onSuccess();
        void onFailure(String errorMessage);
    }

    //Sign In
    public void loginUser(String email, String password, AuthCallback callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> callback.onSuccess(authResult.getUser()))
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    //Sign Up
    public void signUpUser(String name, String email, String password, AuthCallback callback) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser user = authResult.getUser();
                    if (user != null) {

                        // 1. Send Verification Email First
                        user.sendEmailVerification()
                                .addOnSuccessListener(unused -> {

                                    // 2. Update Display Name after email sends
                                    UserProfileChangeRequest profileUpdates =
                                            new UserProfileChangeRequest.Builder()
                                                    .setDisplayName(name)
                                                    .build();

                                    user.updateProfile(profileUpdates); // Fire & forget profile update

                                    callback.onSuccess(user);
                                })
                                .addOnFailureListener(e -> callback.onFailure("Failed to send verification email: " + e.getMessage()));
                    } else {
                        callback.onFailure("User creation returned null.");
                    }
                })
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    //Resend Email Verification
    public void sendEmailVerification(SimpleCallback callback) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnSuccessListener(unused -> callback.onSuccess())
                    .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
        } else {
            callback.onFailure("No user is currently signed in.");
        }
    }

    //Reload & Check Verification Status
    public void checkVerificationStatus(SimpleCallback callback) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.reload().addOnCompleteListener(task -> {
                FirebaseUser updatedUser = mAuth.getCurrentUser();
                if (task.isSuccessful() && updatedUser != null && updatedUser.isEmailVerified()) {
                    callback.onSuccess();
                } else {
                    callback.onFailure("Email is not verified yet. Please check your inbox.");
                }
            });
        } else {
            callback.onFailure("User session expired. Please log in again.");
        }
    }

    //Send Password Reset Email
    public void sendPasswordReset(String email, SimpleCallback callback) {
        mAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener(unused -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    // Unverified User Delete & SignOut
    public void deleteUnverifiedUser(SimpleCallback callback) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null && !user.isEmailVerified()) {
            user.delete().addOnCompleteListener(task -> {
                mAuth.signOut();
                if (task.isSuccessful()) {
                    callback.onSuccess();
                } else {
                    callback.onFailure(task.getException() != null ?
                            task.getException().getMessage() : "Failed to delete account");
                }
            });
        } else {
            mAuth.signOut();
            callback.onSuccess();
        }
    }

    //Logout
    public void logout() {
        mAuth.signOut();
    }

    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }
}
