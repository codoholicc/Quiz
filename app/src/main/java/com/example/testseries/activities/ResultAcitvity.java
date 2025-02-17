package com.example.testseries.activities;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.testseries.R;
import com.example.testseries.model.Question;
import com.example.testseries.model.Test;
import com.google.gson.Gson;
import java.util.Map;

public class ResultAcitvity extends AppCompatActivity {
    Test test;
    TextView textScore;
    TextView txtAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result_acitvity);
        textScore = findViewById(R.id.txtScore);
        txtAnswer = findViewById(R.id.txtAnswer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setUpView();
    }

    private void setUpView() {
        String testData = getIntent().getStringExtra("Test");
        if (testData == null || testData.isEmpty()) {
            return;
        }
        test = new Gson().fromJson(testData, Test.class);
        if (test == null || test.getQuestions() == null) {
            return;
        }
        setAnswerView();
        calculateScore();
    }

    private void setAnswerView() {

        StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, Question> entry : test.getQuestions().entrySet()) {
            Question question = entry.getValue();
            if (question != null) {
                builder.append("<font color='#18206F'><b>Question: ")
                        .append(question.getDescription())
                        .append("</b></font><br/><br/>");
                builder.append("<font color='#009688'>Answer: ")
                        .append(question.getAnswer())
                        .append("</font><br/><br/>");
            }
            Log.d("TAG", "Question: " + question.getDescription());
            Log.d("TAG", "User Answer: " + question.getUserAnswer());
            Log.d("TAG", "Correct Answer: " + question.getAnswer());

        }

        txtAnswer.setText(Html.fromHtml(builder.toString(), Html.FROM_HTML_MODE_COMPACT));
    }


    private void calculateScore() {
        int score = 0;

        for (Map.Entry<String, Question> entry : test.getQuestions().entrySet()) {
            Question question = entry.getValue();

            if (question != null) {
                String correctAnswer = question.getAnswer();
                String userAnswer = question.getUserAnswer();

                // Null check aur trim + ignore case comparison
                if (userAnswer != null && correctAnswer != null &&
                        userAnswer.trim().equalsIgnoreCase(correctAnswer.trim())) {

                    score += 10;
                    Log.d("TAG", "Score updated for question: " + question.getDescription());
                } else {
                    Log.d("TAG", "Incorrect answer for: " + question.getDescription());
                }
            }
        }

        textScore.setText("Your Score: " + score + "/20");
        Log.d("TAG", "Final Score: " + score);
    }

}
