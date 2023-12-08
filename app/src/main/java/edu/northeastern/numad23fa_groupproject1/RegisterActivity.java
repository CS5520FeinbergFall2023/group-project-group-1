package edu.northeastern.numad23fa_groupproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText editEmailText, editPasswordText;
    Button registerButton;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textView;

    UserModel user;
    SharedPreferences sharedPreferences;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null && sharedPreferences != null){
            if (sharedPreferences.contains("USER")) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            intent.putExtra("USER_ID", currentUser.getUid());
//            intent.putExtra("USER", user);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        editEmailText = findViewById(R.id.email);
        editPasswordText = findViewById(R.id.password);
        registerButton = findViewById(R.id.registerButton);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.loginNow);

        sharedPreferences = getSharedPreferences("admin1", MODE_PRIVATE);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password, username;
                email = String.valueOf(editEmailText.getText()).trim();
                password = String.valueOf(editPasswordText.getText()).trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(RegisterActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Account created successfully.",
                                            Toast.LENGTH_SHORT).show();
                                    // create a new corresponding document in the could firestore for this new user
                                    FirebaseUser newUser = mAuth.getCurrentUser();
                                    String uid = mAuth.getCurrentUser().getUid();
                                    user = new UserModel(uid, email);
                                    FirebaseFirestore.getInstance().collection("users").document(uid).set(user);

                                    // save current user locally
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    Gson gson = new Gson();
                                    String json = gson.toJson(user);
                                    editor.putString("USER", json);
                                    editor.commit();
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
//                                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
//                                            Toast.LENGTH_SHORT).show();
                                    Toast.makeText(RegisterActivity.this, "User Authentication Failed: "
                                            + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }

    // This is a helper function that creates a unique username based off of the email address
//    private String createUsername(String tempUsername) {
//        String username = tempUsername;
//        // search through database to find if the tempUsername is unique or not
//        // count amount of tempUsername
//
//        Query query = db.collection("users").whereEqualTo("tempUsername", tempUsername);
//        AggregateQuery countQuery = query.count();
//        countQuery.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    // Count fetched successfully
//                    AggregateQuerySnapshot snapshot = task.getResult();
//                    Log.d(TAG, "Count: " + snapshot.getCount());
//                    username = tempUsername + String.valueOf(snapshot.getCount());
//                } else {
//                    Log.d(TAG, "Count failed: ", task.getException());
//                }
//            }
//        });
//        return username;
//    }
}