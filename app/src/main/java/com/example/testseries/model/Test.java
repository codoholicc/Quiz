package com.example.testseries.model;

import java.util.HashMap;
import java.util.Map;

public class Test {
    private String id;
    private String title;
    private Map<String, Question> questions;

    public Test(String s, String string) {
        this.id = "";
        this.title = "";
        this.questions = new HashMap<>();
    }

    public Test() {
        this.id = "";
        this.title = "";
        this.questions = new HashMap<>();
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Map<String, Question> questions) {
        this.questions = questions;
    }
}
