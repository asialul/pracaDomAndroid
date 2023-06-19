package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText email_editText, password_editText;
    Button login_btn;
    ProgressBar progressBar;
    TextView registerTextView_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_editText = findViewById(R.id.email_editText);
        password_editText = findViewById(R.id.password_editText);
        login_btn = findViewById(R.id.login_btn);
        progressBar = findViewById(R.id.progressBar);
        registerTextView_btn = findViewById(R.id.registerTextView_btn);

        login_btn.setOnClickListener(v -> userLogin());
        registerTextView_btn.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class)));
    }

    void userLogin() {
        String email = email_editText.getText().toString();
        String password = password_editText.getText().toString();

        boolean isValidated = validateAccountData(email, password);
        if (!isValidated) { return; }

        loginFirebase(email, password);
    }

    void loginFirebase(String email, String password) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        changeInProgress(true);
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeInProgress(false);

                if (task.isSuccessful()) {
                    if (firebaseAuth.getCurrentUser().isEmailVerified()) {

                    }

                } else () {

                }
            }
        });
    }

    boolean validateAccountData(String email, String password) {

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_editText.setError("This is not a valid email address.");
            return false;
        }
        if (password.length() < 6) {
            password_editText.setError("Password has to be longer than 5 characters.");
            return false;
        }
        return true;
    }

    void changeInProgress(boolean inProgress) {
        if (inProgress) {
            progressBar.setVisibility(View.VISIBLE);
            login_btn.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            login_btn.setVisibility(View.VISIBLE);
        }
    }
}