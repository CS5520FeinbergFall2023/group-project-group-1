package edu.northeastern.numad23fa_groupproject1;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

import edu.northeastern.numad23fa_groupproject1.History.HistoryActivity;
import edu.northeastern.numad23fa_groupproject1.History.HistoryModel;
import edu.northeastern.numad23fa_groupproject1.Login.LoginActivity;
import edu.northeastern.numad23fa_groupproject1.Login.UserModel;

public class MainActivity extends AppCompatActivity {
    Spinner countrySpinner;
    TextView pickCountryTV, optionQuestionTV;
    Button languageBtn, historyBtn;
    ArrayList<HistoryModel> historyEvent;
    String countrySelected, selectedCountry;
    Button logoutButton;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // retrieve current user from sharedPreference
        sharedPreferences = getSharedPreferences("admin1", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("USER", "");
        UserModel user = gson.fromJson(json, UserModel.class);

        // set the textview and buttons
        LinearLayout secondOptionsLayout = findViewById(R.id.secondOptions);
        optionQuestionTV = findViewById(R.id.optionQuestionTV);
        languageBtn = findViewById(R.id.languageBtn);
        historyBtn = findViewById(R.id.historyBtn);

        // setup the spinner
        countrySpinner = findViewById(R.id.countrySpinner);
        String[] countries = {"Select a country...", "India", "Malaysia"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(arrayAdapter);

        // restore data if this is not the first time this Activity is opened
        countrySelected = sharedPreferences.getString("COUNTRY", "");
        if (!countrySelected.equals("")) {
            // set the spinner option
            selectedCountry = countrySelected;
            int spinnerPosition = arrayAdapter.getPosition(selectedCountry);
            countrySpinner.setSelection(spinnerPosition);

            // set the second box as visible
            secondOptionsLayout.setVisibility(View.VISIBLE);
            optionQuestionTV.setVisibility(View.VISIBLE);
            languageBtn.setText("Language: " + getLanguage(selectedCountry));
            languageBtn.setVisibility(View.VISIBLE);
            historyBtn.setVisibility(View.VISIBLE);
        }

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCountry = parent.getItemAtPosition(position).toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("COUNTRY", selectedCountry);
                editor.commit();

                //   make the option buttons visible if a country is selected
                if (!selectedCountry.equals("Select a country...")) {
//                    Log.d(TAG, "country selected: " + selectedCountry);
                    countrySelected = selectedCountry;
                    secondOptionsLayout.setVisibility(View.VISIBLE);
                    optionQuestionTV.setVisibility(View.VISIBLE);
                    languageBtn.setText("Language: " + getLanguage(selectedCountry));
                    languageBtn.setVisibility(View.VISIBLE);
                    historyBtn.setVisibility(View.VISIBLE);
//                    cultureBtn.setVisibility(View.VISIBLE);
//                    galleryBtn.setVisibility(View.VISIBLE);
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
                Intent langugageActivityIntent = new Intent(MainActivity.this, LanguageActivity.class);
//                langugageActivityIntent.putExtra("USER_NAME", mUserEmail);
//                langugageActivityIntent.putExtra("COUNTRY", countrySelected);
                startActivity(langugageActivityIntent);
            }
        });

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyIntent = new Intent(MainActivity.this, HistoryActivity.class);
                historyIntent.putParcelableArrayListExtra("HISTORY_EVENTS", historyEvent);
                startActivity(historyIntent);
            }
        });

        // logout
        logoutButton = findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutConfirmationDialog();
            }
        });

        //when back button is pressed, it will display logout confirmation dialog
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                showLogoutConfirmationDialog();
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);
    }

    private String getLanguage(String country) {
        if (country.equals("India")) {
            return "Hindi";
        } else if (country.equals("Malaysia")) {
            return "Malay";
        }
        return null;
    }

    private void logoutUser() {
        FirebaseAuth.getInstance().signOut();

        // clear shared preferences to log the user out:
        SharedPreferences sharedPreferences = getSharedPreferences("admin1", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        //bringing us back to login page
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    private void showLogoutConfirmationDialog() {
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.logout_dialog_box, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        TextView title = dialogView.findViewById(R.id.dialogTitle);
        TextView message = dialogView.findViewById(R.id.dialogMessage);
        Button confirmLogoutButton = dialogView.findViewById(R.id.confirmLogoutButton);
        Button cancelLogoutButton = dialogView.findViewById(R.id.cancelLogoutButton);

        AlertDialog dialog = builder.create();

        //for logout button
        confirmLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if confirm, log out user and leads back to login page
                logoutUser();
                dialog.dismiss();
            }
        });

        //for cancel button
        cancelLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do nothing
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}