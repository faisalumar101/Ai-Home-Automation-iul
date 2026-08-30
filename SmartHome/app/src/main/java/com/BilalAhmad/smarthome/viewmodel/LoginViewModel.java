package com.BilalAhmad.smarthome.viewmodel;

import android.text.TextUtils;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.BilalAhmad.smarthome.data.repository.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends ViewModel {
    private final AuthRepository repository;

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoginSuccess = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isEmailUnverified = new MutableLiveData<>();

    public LoginViewModel() {
        this.repository = new AuthRepository();
    }

    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public LiveData<String> getErrorMessage() { return errorMessage; }
    public LiveData<Boolean> getIsLoginSuccess() { return isLoginSuccess; }
    public LiveData<Boolean> getIsEmailUnverified() { return isEmailUnverified; }

    public void login(String email, String password) {
        // Input Validations
        if (TextUtils.isEmpty(email)) {
            errorMessage.setValue("Please enter your email address.");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorMessage.setValue("Please enter a valid email address.");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            errorMessage.setValue("Please enter your password.");
            return;
        }

        isLoading.setValue(true);

        repository.loginUser(email, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(FirebaseUser user) {
                isLoading.setValue(false);
                if (user != null && user.isEmailVerified()) {
                    isLoginSuccess.setValue(true);
                } else {
                    isEmailUnverified.setValue(true);
                }
            }

            @Override
            public void onFailure(String error) {
                isLoading.setValue(false);
                errorMessage.setValue(error);
            }
        });
    }
}
