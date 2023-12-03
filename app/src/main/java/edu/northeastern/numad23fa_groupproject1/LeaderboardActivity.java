package edu.northeastern.numad23fa_groupproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    List<ScoreData> list;
    ScoreAdapter adapter;

    FirebaseFirestore db;
    CollectionReference scoresCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        recyclerView = findViewById(R.id.leaderboard_recycler);
        progressBar = findViewById(R.id.leaderboardProgress);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();
        scoresCollection = db.collection("scores"); // Replace "scores" with your Firestore collection name

        list = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        scoresCollection.addSnapshotListener((snapshot, error) -> {
            if (error != null) {
                progressBar.setVisibility(View.GONE);
                return;
            }

            if (snapshot != null) {
                list.clear(); // Clear the list before adding new data

                for (DocumentSnapshot document : snapshot.getDocuments()) {
                    ScoreData data = document.toObject(ScoreData.class);
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