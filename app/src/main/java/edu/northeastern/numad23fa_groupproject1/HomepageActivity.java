package edu.northeastern.numad23fa_groupproject1;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import edu.northeastern.numad23fa_groupproject1.History.HistoryActivity;
import edu.northeastern.numad23fa_groupproject1.History.HistoryModel;

public class HomepageActivity extends AppCompatActivity {
    Spinner countrySpinner;
    TextView pickCountryTV, optionQuestionTV;

    Button languageBtn, historyBtn, cultureBtn, galleryBtn;

    ArrayList<HistoryModel> historyEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        //test
        historyEvent = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Fetch the country document
        CollectionReference collectionRef = db.collection("countries");

        // Fetch all documents in the collection
        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        String culture = document.getString("culture");
                        ArrayList<Map<String, String>>  historyList = (ArrayList<Map<java.lang.String,java.lang.String>>) document.get("history");
                        if (historyList != null) {
                            // Iterate through each map in the ArrayList
                            for (Map<String, String> historyMap : historyList) {
                                HistoryModel historyModel = new HistoryModel();
                                // Iterate through each key in the map
                                for (String key : historyMap.keySet()) {
                                    historyModel.setDate(historyMap.get("date"));
                                    historyModel.setDescription(historyMap.get("desc"));
                                    historyModel.setEventName(historyMap.get("event"));
//                                    historyModel.setImageId(findImageById(Integer.parseInt(historyMap.get("imageID")))); TODO
                                }
                                historyEvent.add(historyModel);
                            }
                        } else {
                            Log.d("Firestore", "No history field in the document.");
                        }
                        String language = document.getString("language");
                        ArrayList<Map<String, Object>> hashMapsArray = (ArrayList<Map<String, Object>>) document.get("quiz_data");
                        if (hashMapsArray != null) {
                            // Iterate through each HashMap in the array
                            for (Map<String, Object> hashMap : hashMapsArray) {
                                // Access key-value pairs in the HashMap
                                for (Map.Entry<String, Object> entry : hashMap.entrySet()) {
                                    String key = entry.getKey();
                                    Object value = entry.getValue();
                                    Log.d("Firestore", "Key: " + key + ", Value: " + value);
                                }
                            }
                        } else {
                            Log.d("Firestore", "No hashMapsArray field in the document.");
                        }
                    }
                } else {
                    Log.w("Firestore", "Error getting documents.", task.getException());
                }
            }
        });

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
                Intent quizActivityIntent = new Intent(HomepageActivity.this, LanguageActivity.class);
                quizActivityIntent.putExtra("USER_NAME", "testUser");
                startActivity(quizActivityIntent);
            }
        });

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyIntent = new Intent(HomepageActivity.this, HistoryActivity.class);
                historyIntent.putParcelableArrayListExtra("HISTORY_EVENTS", historyEvent);
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

    private int findImageById(int imageID) {
        switch (imageID) {
            case 0 :
                return R.drawable.history1;
            case 1:
                return R.drawable.history2;
            case 2:
                return R.drawable.history3;
            case 3:
                return R.drawable.history4;
        }
        return -1;
    }
}