package edu.northeastern.numad23fa_groupproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        Intent intent = getIntent();

        String userName = intent.getStringExtra("USER_NAME");
        TextView tvName = findViewById(R.id.tv_name);
        tvName.setText(userName);

        int totalQuestions = intent.getIntExtra("TOTAL_QUESTIONS", 0);
        int correctAnswers = intent.getIntExtra("CORRECT_ANSWERS", 0);

        TextView tvScore = findViewById(R.id.tv_score);
        tvScore.setText("Your Score is " + correctAnswers + " out of " + totalQuestions + ".");

        Button btnFinish = findViewById(R.id.btn_finish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizResultActivity.this, MainActivity.class));
            }
        });
    }
}