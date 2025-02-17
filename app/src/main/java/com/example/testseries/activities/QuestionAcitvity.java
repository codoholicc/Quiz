package com.example.testseries.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testseries.R;
import com.example.testseries.adapters.OptionAdapter;
import com.example.testseries.model.Question;
import com.example.testseries.model.Test;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

public class QuestionAcitvity extends AppCompatActivity {
    private List<Test> test = null;
    private Map<String, Question> questions = null;
    int index = 1;
    Button btnNext, btnSubmit, btnPrevious;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.acitvity_question);
        btnNext = findViewById(R.id.btnNext);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnPrevious = findViewById(R.id.btnPrevious);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setUpFirestore();
        setUpEventListener();
    }

    private void setUpEventListener() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                bindViews();
            }
        }); btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index--;
                bindViews();
            }
        }); btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Log.d("FINAL QUIZ",questions.toString());
               Intent intent = new Intent(QuestionAcitvity.this, ResultAcitvity.class);
                String json = new Gson().toJson(test.get(0));
                intent.putExtra("Test",json);
                startActivity(intent);
            }
        });
    }

    private void setUpFirestore() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        String date = getIntent().getStringExtra("DATE");

        if (date != null) {
            firestore.collection("test")
                    .whereEqualTo("title", date)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                            test = queryDocumentSnapshots.toObjects(Test.class);
                            questions = test.get(0).getQuestions();
                            bindViews();
                        }
                    });
        }
    }



    private void bindViews() {
        btnPrevious.setVisibility(View.GONE);
        btnNext.setVisibility(View.GONE);
        btnSubmit.setVisibility(View.GONE);
        if(index == 1){//first question
            btnNext.setVisibility(View.VISIBLE);

        }
        else if(index == questions.size()){//last question
            btnSubmit.setVisibility(View.VISIBLE);
            btnPrevious.setVisibility(View.VISIBLE);
        }
        else{//middle
            btnNext.setVisibility(View.VISIBLE);
            btnPrevious.setVisibility(View.VISIBLE);
        }
        Question question = questions.get("question" + index);
        if (question != null) {
            TextView description = findViewById(R.id.description);
            description.setText(question.getDescription());
            OptionAdapter optionAdapter = new OptionAdapter(this, question);
            RecyclerView optionList = findViewById(R.id.optionList);
            optionList.setLayoutManager(new LinearLayoutManager(this));
            optionList.setAdapter(optionAdapter);
            optionList.setHasFixedSize(true);
        }
    }
}

