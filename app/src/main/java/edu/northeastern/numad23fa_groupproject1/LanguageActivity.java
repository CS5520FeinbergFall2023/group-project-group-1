package edu.northeastern.numad23fa_groupproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LanguageActivity extends AppCompatActivity {

    Button quizBtn;
    Button leaderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        quizBtn = findViewById(R.id.quizBtn);
        leaderBtn = findViewById(R.id.leaderBoardBtn);

        quizBtn.setOnClickListener(v -> {
            Intent quizActivityIntent = new Intent(LanguageActivity.this, QuizActivity.class);
            quizActivityIntent.putExtra("USER_NAME", "testUser");
            startActivity(quizActivityIntent);
        });

        leaderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LeaderboardIntent = new Intent(LanguageActivity.this, LeaderboardActivity.class);
                LeaderboardIntent.putExtra("USER_NAME", "testUser");
                startActivity(LeaderboardIntent);
            }
        });
    }
}