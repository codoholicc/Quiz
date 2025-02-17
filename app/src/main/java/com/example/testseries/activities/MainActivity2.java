package com.example.testseries.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testseries.R;
import com.example.testseries.adapters.TestAdapter;
import com.example.testseries.model.Test;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private TestAdapter adapter;
    private List<Test> TestList = new ArrayList<>();
    private FirebaseFirestore firestore;
    private View btnDatePicker;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
         navigationView = findViewById(R.id.navigationView);
        btnDatePicker = findViewById(R.id.btnDatePicker);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setUpViews();
    }

    private void setUpViews() {
        setUpFireStore();
        setUpDrawerLayout();
        setUpRecyclerView();
        setUpDatePicker();
    }

    private void setUpDrawerLayout() {
        // ye mere tooolbar ko actionbar ki tarah use kr rha hai
        setSupportActionBar(findViewById(R.id.appbar));
        DrawerLayout mainDrawer = findViewById(R.id.main); // Replace R.id.mainDrawer with your DrawerLayout ID
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mainDrawer,
                R.string.test, // Replace with your actual resource ID for open drawer description
                R.string.app_name  // Replace with your actual resource ID for close drawer description
        );
        mainDrawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        // Set navigation item click listener
        navigationView.setNavigationItemSelectedListener(item -> {
            Intent intent = new Intent(MainActivity2.this, ProfileActivity.class);
            startActivity(intent);
            mainDrawer.closeDrawers();
            return true;
        });
    }



    private void setUpRecyclerView() {
        adapter = new TestAdapter(this, TestList);
        RecyclerView testRecyclerView = findViewById(R.id.testRecylerView);
        testRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        testRecyclerView.setAdapter(adapter);
    }
    private void setUpFireStore() {
        firestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firestore.collection("test");
        collectionReference.addSnapshotListener((value, error) -> {
            if (value == null || error != null) {
                Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show();
                return;
            }
            Log.d("DATA", value.toObjects(Test.class).toString());
            TestList.clear();
            TestList.addAll(value.toObjects(Test.class));
            adapter.notifyDataSetChanged();
        });
    }
    private void setUpDatePicker() {
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker().build();
                datePicker.show(getSupportFragmentManager(), "DatePicker");

                datePicker.addOnPositiveButtonClickListener(selection -> {
                    Log.d("DATEPICKER", datePicker.getHeaderText());
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    String formattedDate = dateFormatter.format(new Date(selection));
                    Intent intent = new Intent(MainActivity2.this, QuestionAcitvity.class);
                    intent.putExtra("DATE",formattedDate);
                    startActivity(intent);
                });

                datePicker.addOnNegativeButtonClickListener(view -> {
                    Log.d("DATEPICKER", "Date Picker Cancelled");
                });

                datePicker.addOnCancelListener(dialog -> {
                    Log.d("DATEPICKER", "Date Picker Closed");
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}