package com.dimco.firstapp;

public class Question {
    private int resId;

    public Question(int resId, boolean answerTrue) {
        this.resId = resId;
        this.answerTrue = answerTrue;
    }

    private boolean answerTrue;

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public boolean isAnswerTrue() {
        return answerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        this.answerTrue = answerTrue;
    }
}
