package edu.northeastern.numad23fa_groupproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import edu.northeastern.numad23fa_groupproject1.Learn.ModuleSelectionActivity;
import edu.northeastern.numad23fa_groupproject1.Quiz.QuestionModel;
import edu.northeastern.numad23fa_groupproject1.Quiz.QuizActivity;
import edu.northeastern.numad23fa_groupproject1.Quiz.QuizDataCallback;
import edu.northeastern.numad23fa_groupproject1.leaderboard.LeaderboardActivity;

public class LanguageActivity extends AppCompatActivity {

    Button quizBtn;
    Button leaderBtn;

    Button learnBtn;

    String country;

    List<QuestionModel> questionList = new ArrayList<>();

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        // retrieve country from shared preference
        sharedPreferences = getSharedPreferences("admin1", MODE_PRIVATE);
        country = sharedPreferences.getString("COUNTRY", "");
        Log.d("LanguageActivity ", country);

//        Intent intent = getIntent();
//        country = intent.getStringExtra("COUNTRY");

        quizBtn = findViewById(R.id.quizBtn);
        leaderBtn = findViewById(R.id.leaderBoardBtn);
        learnBtn = findViewById(R.id.learnBtn);

        quizBtn.setOnClickListener(v -> {
            getQuizData(new QuizDataCallback() {
                @Override
                public void onQuizDataLoaded(List<QuestionModel> questions) {
                    questionList.addAll(questions);
                    Intent quizActivityIntent = new Intent(LanguageActivity.this, QuizActivity.class);
//                    quizActivityIntent.putExtra("USER_NAME", "testUser");
//                    quizActivityIntent.putExtra("COUNTRY", country);
                    quizActivityIntent.putParcelableArrayListExtra("questionArray", (ArrayList<? extends Parcelable>) questionList);
                    startActivity(quizActivityIntent);
                }
            });

        });

        leaderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LeaderboardIntent = new Intent(LanguageActivity.this, LeaderboardActivity.class);
//                LeaderboardIntent.putExtra("USER_NAME", "testUser");
                startActivity(LeaderboardIntent);
            }
        });

        learnBtn.setOnClickListener((View v) -> {
            Intent LeaderboardIntent = new Intent(LanguageActivity.this, ModuleSelectionActivity.class);
//            LeaderboardIntent.putExtra("USER_NAME", "testUser");
            startActivity(LeaderboardIntent);
        });
    }

    private void getQuizData(final QuizDataCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("countries");
        collectionRef.document(country.toLowerCase(Locale.ROOT)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        ArrayList<Map<String, Object>> quizDataArray = (ArrayList<Map<String, Object>>) document.get("quizData");
                        if (quizDataArray != null) {
                            List<QuestionModel> questions = new ArrayList<>();
                            // Iterate through each map in the ArrayList
                            for (Map<String, Object> quizData : quizDataArray) {
                                QuestionModel question = new QuestionModel();
                                // Iterate through each key in the map
                                question.setCorrectAnswer(Integer.parseInt(String.valueOf(quizData.get("correct_answer"))));
                                question.setQuestion((String) quizData.get("question"));
                                List<String> optionsList = (List<String>) quizData.get("options");
                                if (optionsList != null && optionsList.size() >= 4) {
                                    question.setOptionOne(optionsList.get(0));
                                    question.setOptionTwo(optionsList.get(1));
                                    question.setOptionThree(optionsList.get(2));
                                    question.setOptionFour(optionsList.get(3));
                                    Log.d("Quiz data", "option_0" + optionsList.get(0) + " option_1" + optionsList.get(1) + " option_2" + optionsList.get(2) + " option_3" + optionsList.get(3));
                                } else {
                                    Log.d("Quiz data", "Options not available or insufficient options");
                                }
                                questions.add(question);
                            }
                            callback.onQuizDataLoaded(questions);
                        } else {
                            Log.d("Firestore", "No quizData field in the document.");
                        }
                    } else {
                        Log.d("Firestore", "No such document.");
                    }
                } else {
                    Log.w("Firestore", "Error getting document.", task.getException());
                }
            }
        });
    }
}

