package edu.northeastern.numad23fa_groupproject1.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import edu.northeastern.numad23fa_groupproject1.MainActivity;
import edu.northeastern.numad23fa_groupproject1.R;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText editEmailText, editPasswordText;
    Button loginButton;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textView;
    SharedPreferences sharedPreferences;

    private int[] backgrounds = new int[] {R.drawable.background2, R.drawable.background3,
            R.drawable.background4, R.drawable.background5, R.drawable.background6};
    private int currentBackgroundIndex = 0;
    private Handler backgroundChangeHandler = new Handler();
    private Runnable backgroundChangeRunnable = new Runnable() {

        @Override
        public void run() {
            findViewById(R.id.loginLayout).setBackgroundResource(backgrounds[currentBackgroundIndex]);
            currentBackgroundIndex = (currentBackgroundIndex + 1) % backgrounds.length;

            //scheduling the background photo to change every 5 seconds
            backgroundChangeHandler.postDelayed(this, 3000);
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null && sharedPreferences != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        backgroundChangeHandler.post(backgroundChangeRunnable);

        sharedPreferences = getSharedPreferences("admin1", MODE_PRIVATE);

        editEmailText = findViewById(R.id.email);
        editPasswordText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.registerNow);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(editEmailText.getText()).trim();
                password = String.valueOf(editPasswordText.getText()).trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(LoginActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Login successfully.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    saveUser(mAuth.getCurrentUser().getUid());
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "User Authentication Failed: "
                                            + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        backgroundChangeHandler.removeCallbacks(backgroundChangeRunnable);
    }

    // This is a helper function that saves current user locally
    private void saveUser(String uid) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("users");
        collectionRef.document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        UserModel user = doc.toObject(UserModel.class);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(user);
                        editor.putString("USER", json);
                        editor.commit();
                    }
                }
            }
        });
    }
}