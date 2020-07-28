package me.rooshi.podcastapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private TextInputLayout emailTextInputLayout;
    private TextInputLayout passwordTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        emailTextInputLayout = findViewById(R.id.emailTextInputLayout);
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout);
    }

    public void onRegister(View view) {
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        //TODO: add email and password to the new activity
        registerIntent.putExtra("email", getStringFromTextInputLayout(emailTextInputLayout));
        registerIntent.putExtra("password", getStringFromTextInputLayout(passwordTextInputLayout));
        startActivity(registerIntent);
    }

    public void onEmailSignIn(View view) {
        //TODO: check for email format
        String email = getStringFromTextInputLayout(emailTextInputLayout);

        String password = getStringFromTextInputLayout(passwordTextInputLayout);

        if (TextUtils.isEmpty(email)) {
            //TODO: replace toast with something better looking
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_LONG).show();
                            } else {
                                finish();
                            }
                        }
                    });
        }
    }

    public String getStringFromTextInputLayout(TextInputLayout textInputLayout) {
        EditText editText = textInputLayout.getEditText();
        String s = null;
        if (editText != null) {
            s = editText.getText().toString();
        }
        if (s == null) {
            return "";
        } else {
            return s;
        }
    }
}