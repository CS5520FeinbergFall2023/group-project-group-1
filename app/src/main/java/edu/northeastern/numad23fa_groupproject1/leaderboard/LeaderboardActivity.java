package edu.northeastern.numad23fa_groupproject1.leaderboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.numad23fa_groupproject1.R;
import edu.northeastern.numad23fa_groupproject1.UserModel;

public class LeaderboardActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    List<UserModel> list;
    ScoreAdapter adapter;

    FirebaseFirestore db;
    CollectionReference scoresCollection;
//    Button leaderboardBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        recyclerView = findViewById(R.id.leaderboard_recycler);
        progressBar = findViewById(R.id.leaderboardProgress);

//        leaderboardBackBtn = findViewById(R.id.LeaderboardBackBtn);

//        leaderboardBackBtn.setOnClickListener(v -> {
//            // Go back to LanguageActivity when the back button is clicked
//            startActivity(new Intent(LeaderboardActivity.this, LanguageActivity.class));
//            finish();
//        });

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();
        scoresCollection = db.collection("users");

        list = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        scoresCollection
                .orderBy("score", Query.Direction.ASCENDING)
                .addSnapshotListener((snapshot, error) -> {
                    if (error != null) {
                        progressBar.setVisibility(View.GONE);
                        return;
                    }

                    if (snapshot != null) {
                        list.clear(); // Clear the list before adding new data

                        for (DocumentSnapshot document : snapshot.getDocuments()) {
                            UserModel data = document.toObject(UserModel.class);
                            if (data != null) {
                                list.add(data);
                            }
                        }

                        adapter = new ScoreAdapter(list, LeaderboardActivity.this);
                        recyclerView.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);
                    }
        });


    }
}