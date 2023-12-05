package edu.northeastern.numad23fa_groupproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;


public class QuizActivity extends AppCompatActivity {

    private int mCurrentPosition = 1; // Default and the first question position
    private ArrayList<Question> mQuestionsList = null;

    private int mSelectedOptionPosition = 0;
    private int mCorrectAnswers = 0;
    private String mUserName,country = null;


    // Declare UI elements
    private TextView tvOptionOne, tvOptionTwo, tvOptionThree, tvOptionFour;
    private ProgressBar progressBar;
    private TextView tvProgress, tvQuestion;
    private TextView btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        mQuestionsList = new ArrayList<>();
        Intent intent = getIntent();
        if (intent != null) {
            mUserName = intent.getStringExtra("USER_NAME");
            country = intent.getStringExtra("COUNTRY");
            mQuestionsList = getIntent().getParcelableArrayListExtra("questionArray");
        }
        // Initialize UI elements
        tvOptionOne = findViewById(R.id.tv_option_one);
        tvOptionTwo = findViewById(R.id.tv_option_two);
        tvOptionThree = findViewById(R.id.tv_option_three);
        tvOptionFour = findViewById(R.id.tv_option_four);
        progressBar = findViewById(R.id.progressBar);
        tvProgress = findViewById(R.id.tv_progress);
        tvQuestion = findViewById(R.id.tv_question);
        btnSubmit = findViewById(R.id.btn_submit);

        setQuestion();

        // Set click listeners
        setOnClickListeners();
    }






    private void setOnClickListeners() {
        tvOptionOne.setOnClickListener(view -> selectedOptionView(tvOptionOne,0));
        tvOptionTwo.setOnClickListener(view -> selectedOptionView(tvOptionTwo, 1));
        tvOptionThree.setOnClickListener(view -> selectedOptionView(tvOptionThree, 2));
        tvOptionFour.setOnClickListener(view -> selectedOptionView(tvOptionFour, 3));

        btnSubmit.setOnClickListener(view -> {
            if (mSelectedOptionPosition == 0) {
                mCurrentPosition++;
                if (mCurrentPosition <= mQuestionsList.size()) {
                    setQuestion();
                } else {
                    Intent intent = new Intent(this, QuizResultActivity.class);
                    intent.putExtra("USER_NAME", mUserName);
                    intent.putExtra("CORRECT_ANSWERS", mCorrectAnswers);
                    intent.putExtra("QUESTION_LIST_SIZE", mQuestionsList.size());
                    startActivity(intent);
                    finish();
                }
            } else {
                Question question = mQuestionsList.get(mCurrentPosition - 1);

                if (question.getCorrectAnswer() != mSelectedOptionPosition) {
                    answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg);
                } else {
                    mCorrectAnswers++;
                }

                answerView(question.getCorrectAnswer(), R.drawable.correct_option_border_bg);

                if (mCurrentPosition == mQuestionsList.size()) {
                    btnSubmit.setText("FINISH");
                } else {
                    btnSubmit.setText("GO TO NEXT QUESTION");
                }

                mSelectedOptionPosition = 0;
            }
        });
    }

    private void setQuestion() {
        Question question = mQuestionsList.get(mCurrentPosition - 1);

        defaultOptionsView();

        if (mCurrentPosition == mQuestionsList.size()) {
            btnSubmit.setText("FINISH");
        } else {
            btnSubmit.setText("SUBMIT");
        }

        progressBar.setProgress(mCurrentPosition);
        tvProgress.setText(mCurrentPosition + "/" + progressBar.getMax());

        tvQuestion.setText(question.getQuestion());
        // Assuming setImageResource is a method in your Question class to set the image
        // Replace it with the appropriate method or use an ImageView in your layout
        // ivImage.setImageResource(question.getImage());
        tvOptionOne.setText(question.getOptionOne());
        tvOptionTwo.setText(question.getOptionTwo());
        tvOptionThree.setText(question.getOptionThree());
        tvOptionFour.setText(question.getOptionFour());
    }

    private void selectedOptionView(TextView tv, int selectedOptionNum) {
        defaultOptionsView();

        mSelectedOptionPosition = selectedOptionNum;

        tv.setTextColor(Color.parseColor("#363A43"));
        tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
        tv.setBackground(ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg));
    }

    private void defaultOptionsView() {
        ArrayList<TextView> options = new ArrayList<>();
        options.add(0, tvOptionOne);
        options.add(1, tvOptionTwo);
        options.add(2, tvOptionThree);
        options.add(3, tvOptionFour);

        for (TextView option : options) {
            option.setTextColor(Color.parseColor("#7A8089"));
            option.setTypeface(Typeface.DEFAULT);
            option.setBackground(ContextCompat.getDrawable(this, R.drawable.default_option_border_bg));
        }
    }

    private void answerView(int answer, int drawableView) {
        switch (answer) {
            case 0:
                tvOptionOne.setBackground(ContextCompat.getDrawable(this, drawableView));
                break;
            case 1:
                tvOptionTwo.setBackground(ContextCompat.getDrawable(this, drawableView));
                break;
            case 2:
                tvOptionThree.setBackground(ContextCompat.getDrawable(this, drawableView));
                break;
            case 3:
                tvOptionFour.setBackground(ContextCompat.getDrawable(this, drawableView));
                break;
        }
    }
}