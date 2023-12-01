package edu.northeastern.numad23fa_groupproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private int mCurrentPosition = 1; // Default and the first question position
    private ArrayList<Question> mQuestionsList = null;

    private int mSelectedOptionPosition = 0;
    private int mCorrectAnswers = 0;
    private String mUserName = null;


    // Declare UI elements
    private TextView tvOptionOne, tvOptionTwo, tvOptionThree, tvOptionFour;
    private ProgressBar progressBar;
    private TextView tvProgress, tvQuestion;
    private TextView btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Intent intent = getIntent();
        if (intent != null) {
            mUserName = intent.getStringExtra("USER_NAME");
        }

        mQuestionsList = getQuestions();

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

    private ArrayList<Question> getQuestions() {
        ArrayList<Question> questions = new ArrayList<>();

        // Question 1
        questions.add(new Question(1, "Which river is known as the 'Ganga' in India?",
                "Yamuna", "Brahmaputra", "Godavari",
                "Ganges", 4));

        // Question 2
//        questions.add(new Question(2, "What is the capital city of the Indian state Maharashtra?",
//                "Delhi", "Mumbai", "Chennai",
//                "Kolkata", 2));
//
//        // Question 3
//        questions.add(new Question(3, "Which Indian festival is known as the 'Festival of Lights'?",
//                "Holi", "Diwali", "Navratri",
//                "Eid", 2));
//
//        // Question 4
//        questions.add(new Question(4, "In which state is the Taj Mahal located?",
//                "Rajasthan", "Uttar Pradesh", "Madhya Pradesh",
//                "Maharashtra", 2));
//
//        // Question 5
//        questions.add(new Question(5, "Who is known as the 'Father of the Nation' in India?",
//                "Sardar Patel", "Jawaharlal Nehru", "Subhas Chandra Bose",
//                "Mahatma Gandhi", 4));
//
//        // Question 6
//        questions.add(new Question(6, "Which Indian city is famous for its IT industry and is known as the 'Silicon Valley of India'?",
//                "Mumbai", "Kolkata", "Bengaluru",
//                "Chennai", 3));
//
//        // Question 7
//        questions.add(new Question(7, "What is the currency of India?",
//                "Rupee", "Rupiah", "Ringgit",
//                "Yen", 1));
//
//        // Question 8
//        questions.add(new Question(8, "Which mountain range separates India from China?",
//                "Himalayas", "Vindhya Range", "Western Ghats",
//                "Aravalli Range", 1));
//
//        // Question 9
//        questions.add(new Question(9, "What is the national emblem of India?",
//                "Lotus", "Ashoka Chakra", "National Flower",
//                "Lion Capital of Ashoka", 4));
//
//        // Question 10
//        questions.add(new Question(10, "Who was the first Prime Minister of India?",
//                "Jawaharlal Nehru", "Indira Gandhi", "Sardar Patel",
//                "Rajendra Prasad", 1));

        return questions;
    }



    private void setOnClickListeners() {
        tvOptionOne.setOnClickListener(view -> selectedOptionView(tvOptionOne,1));
        tvOptionTwo.setOnClickListener(view -> selectedOptionView(tvOptionTwo, 2));
        tvOptionThree.setOnClickListener(view -> selectedOptionView(tvOptionThree, 3));
        tvOptionFour.setOnClickListener(view -> selectedOptionView(tvOptionFour, 4));

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
            case 1:
                tvOptionOne.setBackground(ContextCompat.getDrawable(this, drawableView));
                break;
            case 2:
                tvOptionTwo.setBackground(ContextCompat.getDrawable(this, drawableView));
                break;
            case 3:
                tvOptionThree.setBackground(ContextCompat.getDrawable(this, drawableView));
                break;
            case 4:
                tvOptionFour.setBackground(ContextCompat.getDrawable(this, drawableView));
                break;
        }
    }
}