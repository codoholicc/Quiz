package com.example.testseries.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.testseries.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    private EditText email_login, pass_login;
    TextView btnSignUp;
    Button btn_login;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login2);
        email_login = findViewById(R.id.email_login);
        pass_login = findViewById(R.id.pass_login);
        btn_login = findViewById(R.id.btn_login);
        btnSignUp = findViewById(R.id.btnSignUp);
        firebaseAuth = FirebaseAuth.getInstance();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, SignUp.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void login() {
        String email = email_login.getText().toString();
        String password = pass_login.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Email/password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Authenticate user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(login.this, " Logged In Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(login.this, MainActivity2.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(login.this, "Log In Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}