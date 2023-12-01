package edu.northeastern.numad23fa_groupproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LanguageActivity extends AppCompatActivity {

    Button quizBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        quizBtn = findViewById(R.id.quizBtn);

        quizBtn.setOnClickListener(v -> {
            Intent quizActivityIntent = new Intent(LanguageActivity.this, QuizActivity.class);
            quizActivityIntent.putExtra("USER_NAME", "testUser");
            startActivity(quizActivityIntent);
        });
    }
}