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

public class SignUp extends AppCompatActivity {
    EditText email_sign;
    EditText pass_sign;
    EditText c_pass_sign;
    Button btn_sign;
    TextView btn_log;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        firebaseAuth = FirebaseAuth.getInstance();
        email_sign = findViewById(R.id.email_sign);
        pass_sign = findViewById(R.id.pass_sign);
        c_pass_sign = findViewById(R.id.c_pass_sign);
        btn_sign = findViewById(R.id.btn_sign);
        btn_log = findViewById(R.id.btn_log);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpUser();
            }
        });
        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, login.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void SignUpUser() {
        String email = email_sign.getText().toString();
        String pass = pass_sign.getText().toString();
        String c_pass = c_pass_sign.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(c_pass)) {
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!pass.equals(c_pass)) {
            Toast.makeText(this, "Password and Confirm Password do not match", Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUp.this, MainActivity2.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SignUp.this, "Error creating user.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}