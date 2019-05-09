package com.dimco.firstapp.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.dimco.firstapp.R;

import java.util.Locale;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "com.dimco.firstapp.android.quiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "com.dimco.firstapp.android.quiz.answer_shown";

    private boolean answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        answer = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        TextView currentAPITextView = findViewById(R.id.current_api);
        currentAPITextView.setText(String.format(Locale.US, "%s %s", "Current API level", Build.VERSION.SDK_INT));

        TextView answerTextView = findViewById(R.id.answerTextView);
        Button showAnswer = findViewById(R.id.showAnswerButton);
        showAnswer.setOnClickListener(v -> {
            answerTextView.setText((answer) ? R.string.true_button : R.string.false_button);
            setAnswerResult();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int cx = showAnswer.getWidth() / 2;
                int cy = showAnswer.getHeight() / 2;
                float radius = showAnswer.getWidth();
                Animator anim = ViewAnimationUtils
                        .createCircularReveal(showAnswer, cx, cy, radius, 0);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        answerTextView.setVisibility(View.VISIBLE);
                        showAnswer.setVisibility(View.INVISIBLE);
                    }
                });
                anim.start();
            } else {
                answerTextView.setVisibility(View.VISIBLE);
                showAnswer.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void setAnswerResult() {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, true);
        setResult(RESULT_OK, data);
    }

    public static boolean wasAnswerShown(Intent intent) {
        return intent.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }
}
