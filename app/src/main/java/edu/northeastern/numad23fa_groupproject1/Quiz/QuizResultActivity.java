package edu.northeastern.numad23fa_groupproject1.Quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import edu.northeastern.numad23fa_groupproject1.LanguageActivity;
import edu.northeastern.numad23fa_groupproject1.R;
import edu.northeastern.numad23fa_groupproject1.UserModel;

public class QuizResultActivity extends AppCompatActivity {
    FirebaseFirestore db;
    CollectionReference scoresCollection;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        Intent intent = getIntent();
        db = FirebaseFirestore.getInstance();
        scoresCollection = db.collection("users"); // Replace "scores" with your Firestore collection name

        // get user information from shared preference
        sharedPreferences = getSharedPreferences("admin1", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("USER", "");
        UserModel user = gson.fromJson(json, UserModel.class);
        String userName = user.getUsername();
//        String userName = intent.getStringExtra("USER_NAME");

        TextView tvName = findViewById(R.id.tv_name);
        tvName.setText(userName);

        int totalQuestions = intent.getIntExtra("QUESTION_LIST_SIZE", 0);
        int correctAnswers = intent.getIntExtra("CORRECT_ANSWERS", 0);

        TextView tvScore = findViewById(R.id.tv_score);
        tvScore.setText("Your Score is " + correctAnswers + " out of " + totalQuestions + ".");

        Button btnFinish = findViewById(R.id.btn_finish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a map with name and score fields
                Map<String, Object> scoreData = new HashMap<>();
                scoreData.put("name", userName);
                scoreData.put("score", correctAnswers);

//                // Add the score data to the 'scores' collection in Firestore
//                scoresCollection
//                        .add(scoreData)
//                        .addOnSuccessListener(documentReference -> {
//                            // Success: Score data added to Firestore
//                            startActivity(new Intent(QuizResultActivity.this, LanguageActivity.class));
//                        })
//                        .addOnFailureListener(e -> {
//                            // Failure: Handle error adding score data to Firestore
//                        });

                //

            }
        });
    }
}