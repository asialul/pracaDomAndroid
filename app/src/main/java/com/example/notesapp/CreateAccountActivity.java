package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateAccountActivity extends AppCompatActivity {

    EditText email_editText, password_editText, confirmPassword_editText;
    Button createAccount_btn;
    ProgressBar progressBar;
    TextView loginTextView_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        email_editText = findViewById(R.id.email_editText);
        password_editText = findViewById(R.id.password_editText);
        confirmPassword_editText = findViewById(R.id.confirmPassword_editText);
        createAccount_btn = findViewById(R.id.createAccount_btn);
        progressBar = findViewById(R.id.progressBar);
        loginTextView_btn = findViewById(R.id.loginTextView_btn);

        createAccount_btn.setOnClickListener(v -> createAccount());
        loginTextView_btn.setOnClickListener(v -> finish());
    }

    void createAccount() {
        String email = email_editText.getText().toString();
        String password = password_editText.getText().toString();
        String confirmPassword = confirmPassword_editText.getText().toString();

        boolean isValidated = validateAccountData(email, password, confirmPassword);
        if (!isValidated) { return; }

        createAccountFirebase(email, password);

    }

    boolean validateAccountData(String email, String password, String confirmPassword) {

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_editText.setError("This is not a valid email address.");
            return false;
        }
        if (password.length() < 6) {
            password_editText.setError("Password has to be longer than 5 characters.");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            confirmPassword_editText.setError("Passwords do nor match!");
            return false;
        }
        return true;

    }

    void createAccountFirebase(String email, String password) {
        changeInProgress(true);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(CreateAccountActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(CreateAccountActivity.this,
                                    "Account created successfully. Check your email to verify.", Toast.LENGTH_SHORT).show();
                            firebaseAuth.getCurrentUser().sendEmailVerification();
                            firebaseAuth.signOut();
                            finish();

                        } else {

                        }

                    }
                });
    }

    void changeInProgress(boolean inProgress) {
        if (inProgress) {
            progressBar.setVisibility(View.VISIBLE);
            createAccount_btn.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            createAccount_btn.setVisibility(View.VISIBLE);
        }
    }
}