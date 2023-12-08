package edu.northeastern.numad23fa_groupproject1.Quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import edu.northeastern.numad23fa_groupproject1.LanguageActivity;
import edu.northeastern.numad23fa_groupproject1.Login.UserModel;
import edu.northeastern.numad23fa_groupproject1.R;

public class QuizResultActivity extends AppCompatActivity {
    private static final String TAG = "QuizResultActivity";
    FirebaseFirestore db;
    CollectionReference userCollection;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        Intent intent = getIntent();
        db = FirebaseFirestore.getInstance();
        userCollection = db.collection("users");

//        String userName = intent.getStringExtra("USER_NAME");

        // retrieve user information from shared preference
        sharedPreferences = getSharedPreferences("admin1", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("USER", "");
        UserModel user = gson.fromJson(json, UserModel.class);

        String userName = user.getUsername();

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
//                // Create a map with name and score fields
//                Map<String, Object> scoreData = new HashMap<>();
//                scoreData.put("name", userName);
//                scoreData.put("score", correctAnswers);
//
//                // Add the score data to the 'scores' collection in Firestore
//                userCollection
//                        .add(scoreData)
//                        .addOnSuccessListener(documentReference -> {
//                            // Success: Score data added to Firestore
//                            startActivity(new Intent(QuizResultActivity.this, LanguageActivity.class));
//                        })
//                        .addOnFailureListener(e -> {
//                            // Failure: Handle error adding score data to Firestore
//                        });

                // Update score of current user (locally and in the Firestore)
                int currentScore = user.getScore();
                int newScore = currentScore + correctAnswers;
                user.setScore(newScore);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(user);
                editor.putString("USER", json);
                editor.apply();

                DocumentReference doc = userCollection.document(user.getUserId());
                doc.update("score", newScore)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG, "DocumentSnapshot successfully updated! " + currentScore + ", " + correctAnswers + ", " + newScore);
                                startActivity(new Intent(QuizResultActivity.this, LanguageActivity.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error updating document", e);
                            }
                        });
            }
        });
    }
}