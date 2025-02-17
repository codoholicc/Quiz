package com.example.testseries.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.testseries.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {
    private TextView txtEmail;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Initialize Firebase Auth
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        // Bind views
        txtEmail = findViewById(R.id.txtEmail);
        btnLogout = findViewById(R.id.btnLogout);

        // Set user email to TextView
        if (firebaseAuth.getCurrentUser() != null) {
            txtEmail.setText(firebaseAuth.getCurrentUser().getEmail());
        }

        // Logout button click listener
        btnLogout.setOnClickListener(view -> {
            firebaseAuth.signOut();
            Intent intent = new Intent(ProfileActivity.this, login.class);
            startActivity(intent);
            finish(); // Close current activity
        });

    }
}