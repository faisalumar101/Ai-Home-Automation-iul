package com.BilalAhmad.smarthome.viewmodel;

import android.text.TextUtils;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.BilalAhmad.smarthome.data.repository.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

public class SignUpViewModel extends ViewModel {
    private final AuthRepository repository;

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isSignUpSuccess = new MutableLiveData<>();

    public SignUpViewModel() {
        this.repository = new AuthRepository();
    }

    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public LiveData<String> getErrorMessage() { return errorMessage; }
    public LiveData<Boolean> getIsSignUpSuccess() { return isSignUpSuccess; }

    // SignUpViewModel.java me signUp method update:
    public void signUp(String name, String email, String password) {
        if (TextUtils.isEmpty(name)) {
            errorMessage.setValue("Please enter your name.");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            errorMessage.setValue("Please enter an email address.");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorMessage.setValue("Please enter a valid email address.");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            errorMessage.setValue("Please enter a password.");
            return;
        }
        if (password.length() < 6) {
            errorMessage.setValue("Password must be at least 6 characters long.");
            return;
        }

        isLoading.setValue(true);


        repository.signUpUser(name, email, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(FirebaseUser user) {
                isLoading.setValue(false);
                isSignUpSuccess.setValue(true);
            }

            @Override
            public void onFailure(String error) {
                isLoading.setValue(false);
                errorMessage.setValue(error);
            }
        });
    }
}
