package edu.northeastern.numad23fa_groupproject1;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import edu.northeastern.numad23fa_groupproject1.History.HistoryActivity;

public class HomepageActivity extends AppCompatActivity {
    Spinner countrySpinner;
    TextView pickCountryTV, optionQuestionTV;

    Button languageBtn, historyBtn, cultureBtn, galleryBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        // set the textview and buttons
        optionQuestionTV = findViewById(R.id.optionQuestionTV);
        languageBtn = findViewById(R.id.languageBtn);
        historyBtn = findViewById(R.id.historyBtn);
        cultureBtn = findViewById(R.id.cultureBtn);
        galleryBtn = findViewById(R.id.galleryBtn);

        // setup the spinner
        countrySpinner = findViewById(R.id.countrySpinner);
        String[] countries = {"Select a country...", "India", "Malaysia"};

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(arrayAdapter);

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCountry = parent.getItemAtPosition(position).toString();

                //   make the option buttons visible if a country is selected
                if (!selectedCountry.equals("Select a country...")) {
                    Log.d(TAG, "country selected: " + selectedCountry);
                    optionQuestionTV.setVisibility(View.VISIBLE);
                    languageBtn.setText("Language: " + getLanguage(selectedCountry));
                    languageBtn.setVisibility(View.VISIBLE);
                    historyBtn.setVisibility(View.VISIBLE);
                    cultureBtn.setVisibility(View.VISIBLE);
                    galleryBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // onClickListeners for option (language, history, culture, gallery) buttons
        languageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyIntent = new Intent(HomepageActivity.this, HistoryActivity.class);
                startActivity(historyIntent);
            }
        });

        cultureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private String getLanguage(String country) {
        // TODO: store (country:language) in a hashmap?
        if (country.equals("India")) {
            return "Hindi";
        } else if (country.equals("Malaysia")) {
            return "Malay";
        }
        return null;
    }
}