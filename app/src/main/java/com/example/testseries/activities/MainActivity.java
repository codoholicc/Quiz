package com.example.testseries.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.testseries.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Check if the user is already logged in
        if (auth.getCurrentUser() != null) {
//            Toast.makeText(this, "User is already logged in!", Toast.LENGTH_SHORT).show();
            redirect("MAIN");
            finish();
        }
        // Set up button click listener
        findViewById(R.id.btn_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirect("LOGIN");
            }
        });
    }
    private void redirect(String name) {
        Intent intent;

        switch (name) {
            case "LOGIN":
                intent = new Intent(this, login.class);
                break;
            case "MAIN":
                intent = new Intent(this, MainActivity2.class);
                break;
            default:
                throw new IllegalArgumentException("No path exists");
        }

        startActivity(intent);
        finish();
    }
}