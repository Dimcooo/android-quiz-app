package com.dimco.firstapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.dimco.firstapp.R;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "com.dimco.firstapp.android.quiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "com.dimco.firstapp.android.quiz.answer_shown";

    private boolean answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        answer = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        TextView answerTextView = findViewById(R.id.answerTextView);
        Button showAnswer = findViewById(R.id.showAnswerButton);
        showAnswer.setOnClickListener(v -> {
            answerTextView.setText((answer) ? R.string.true_button : R.string.false_button);
            setAnswerResult(true);
        });
    }

    private void setAnswerResult(boolean answer) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, answer);
        setResult(RESULT_OK, data);
    }

    public static boolean wasAnswerShown(Intent intent) {
        return intent.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }
}
