package edu.northeastern.numad23fa_groupproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      
        // TODO: remove this button; this is a dummy placement button that opens the homepage activity
        Button button = findViewById(R.id.dummyButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homePageIntent = new Intent(MainActivity.this, HomepageActivity.class);
                startActivity(homePageIntent);
            }
        });
    }
}