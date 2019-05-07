package com.dimco.firstapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dimco.firstapp.entity.Question;
import com.dimco.firstapp.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String CURRENT_QUESTION_ID = "";
    private static final String EXTRA_ANSWER_IS_TRUE = "com.dimco.firstapp.android.quiz.answer_is_true";
    private static final String CHEATED_QUESTION_LIST = "CHEATED_QUESTION_LIST";
    private static final int REQUEST_CODE_CHEAT = 1998;

    private Set<Integer> cheatedSet = new HashSet<>();

    private TextView questionText;

    private int currentQuestionId;
    private boolean isCheater;

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
            currentQuestionId = savedInstanceState.getInt(CURRENT_QUESTION_ID, 0);
            cheatedSet = new HashSet<>(savedInstanceState.getIntegerArrayList(CHEATED_QUESTION_LIST));
        }

        questionText = findViewById(R.id.question_text);
        generateQuestionText();

        ImageButton nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(v -> {
            currentQuestionId = (currentQuestionId + 1) % questionBank.length;
            isCheater = false;
            generateQuestionText();
        });

        ImageButton previousButton = findViewById(R.id.previous_button);
        previousButton.setOnClickListener(v -> {
            currentQuestionId = (currentQuestionId == 0) ? questionBank.length - 1 : (currentQuestionId - 1) % questionBank.length;
            isCheater = false;
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

        Button cheatButton = findViewById(R.id.cheat_button);
        cheatButton.setOnClickListener(v -> {
            boolean answer = questionBank[currentQuestionId].isAnswerTrue();
            startActivityForResult(newIntent(MainActivity.this, answer), REQUEST_CODE_CHEAT);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            assert data != null;
            isCheater = CheatActivity.wasAnswerShown(data);
            cheatedSet.add(currentQuestionId);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private static Intent newIntent(Context context, boolean answer) {
        Intent intent = new Intent(context, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answer);

        return intent;
    }

    private void generateQuestionText() {
        int question = questionBank[currentQuestionId].getResId();
        questionText.setText(question);
    }

    private void checkAnswer(boolean userAnswer) {
        boolean isAnswerTrue = questionBank[currentQuestionId].isAnswerTrue();
        int messageResId;

        if (!cheatedSet.contains(currentQuestionId)) {
            messageResId = (userAnswer == isAnswerTrue) ? R.string.correct_answer : R.string.incorrect_answer;
        } else {
            messageResId = R.string.judgment_toast;
        }

        Toast.makeText(MainActivity.this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        outState.putInt(CURRENT_QUESTION_ID, currentQuestionId);
        outState.putIntegerArrayList(CHEATED_QUESTION_LIST, new ArrayList<>(cheatedSet));
    }
}
