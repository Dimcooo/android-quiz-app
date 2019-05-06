package com.dimco.firstapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dimco.firstapp.entity.Question;
import com.dimco.firstapp.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String KEY_INDEX = "key_index";

    private TextView questionText;

    private int currentQuestionId;

    private Question[] questionBank = new Question[]{
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called");
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            currentQuestionId = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        questionText = findViewById(R.id.question_text);
        generateQuestionText();

        ImageButton nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(v -> {
            currentQuestionId = (currentQuestionId + 1) % questionBank.length;
            generateQuestionText();
        });

        ImageButton previousButton = findViewById(R.id.previous_button);
        previousButton.setOnClickListener(v -> {
            currentQuestionId = (currentQuestionId == 0) ? questionBank.length - 1 : (currentQuestionId - 1) % questionBank.length;
            generateQuestionText();
        });

        Button trueButton = findViewById(R.id.true_button);
        trueButton.setOnClickListener(v -> checkAnswer(true));

        Button falseButton = findViewById(R.id.false_button);
        falseButton.setOnClickListener(v -> checkAnswer(false));

        questionText.setOnClickListener(v -> {
            currentQuestionId = (currentQuestionId + 1) % questionBank.length;
            MainActivity.this.generateQuestionText();
        });
    }

    private void generateQuestionText() {
        int question = questionBank[currentQuestionId].getResId();
        questionText.setText(question);
    }

    private void checkAnswer(boolean userAnswer) {
        boolean isAnswerTrue = questionBank[currentQuestionId].isAnswerTrue();
        int messageResId = (userAnswer == isAnswerTrue) ? R.string.correct_answer : R.string.incorrect_answer;

        Toast.makeText(MainActivity.this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        outState.putInt(KEY_INDEX, currentQuestionId);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}
