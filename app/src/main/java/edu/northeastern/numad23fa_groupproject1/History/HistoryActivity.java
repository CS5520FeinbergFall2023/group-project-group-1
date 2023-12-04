package edu.northeastern.numad23fa_groupproject1.History;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

import edu.northeastern.numad23fa_groupproject1.R;

public class HistoryActivity extends AppCompatActivity {
    public String Country;
    private RecyclerView recyclerView;
    private RVAdapter rvAdapter;
    private RecyclerView.LayoutManager rvLayoutManager;

    private ArrayList<HistoryModel> historyEvents;
    private final ArrayList<HistoryModel> eventList = new ArrayList<>();

    // variables for persistency of data when orientation is changed
    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";
    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Intent intent = getIntent();
        if (intent != null) {
            historyEvents = intent.getParcelableArrayListExtra("HISTORY_EVENTS");
        }
        // create the recycler view
        createRV();

        // items are retrieved if this is not the first time opening the activity
        if (savedInstanceState != null && savedInstanceState.containsKey(NUMBER_OF_ITEMS)) {
            if (eventList == null || eventList.size() == 0) {
                int size = savedInstanceState.getInt(NUMBER_OF_ITEMS);

                for (int i = 0; i < size; i++) {
                    int itemImageId = savedInstanceState.getInt(KEY_OF_INSTANCE + i + "0");
                    String itemDate = savedInstanceState.getString(KEY_OF_INSTANCE + i + "1");
                    String itemEventName = savedInstanceState.getString(KEY_OF_INSTANCE + i + "2");
                    String itemDescription = savedInstanceState.getString(KEY_OF_INSTANCE + i + "3");
                    boolean itemVisibility = savedInstanceState.getBoolean(KEY_OF_INSTANCE + i + "4");


                    HistoryModel item = new HistoryModel(itemImageId, itemDate, itemEventName,
                            itemDescription, itemVisibility);
                    eventList.add(item);
                    rvAdapter.notifyItemInserted(0);
                }
            }
        } else { // first time opening activity
            // load local data
            addItems(loadList());

            // load data from the database
//            addItems(historyEvents);
        }

    }

    // this method saves the instances of items into a Bundle
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        int size = eventList == null ? 0 : eventList.size();
        outState.putInt(NUMBER_OF_ITEMS, size);

        for (int i = 0; i < size; i++) {
            outState.putInt(KEY_OF_INSTANCE + i + "0", eventList.get(i).getImageId());
            outState.putString(KEY_OF_INSTANCE + i + "1", eventList.get(i).getDate());
            outState.putString(KEY_OF_INSTANCE + i + "2", eventList.get(i).getEventName());
            outState.putString(KEY_OF_INSTANCE + i + "3", eventList.get(i).getDescription());
            outState.putBoolean(KEY_OF_INSTANCE + i + "4", eventList.get(i).isVisibility());
        }

        super.onSaveInstanceState(outState);
    }

    // this method creates and sets up the recycler view
    private void createRV() {
        rvLayoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(rvLayoutManager);
        recyclerView.setHasFixedSize(true);
        rvAdapter = new RVAdapter(eventList, this);
        recyclerView.setAdapter(rvAdapter);
    }

    // Given a list of historical events, this method adds them into the recyclerview
    private void addItems(ArrayList<HistoryModel> addEvents) {
        for (int i = 0; i < addEvents.size(); i++) {
            eventList.add(addEvents.get(i));
            rvAdapter.notifyItemInserted(0);
        }
    }

    // Load list of historical events
    private ArrayList<HistoryModel> loadList() {
        ArrayList<HistoryModel> addEvent = new ArrayList<>();
        int size = 10;
        int imageID = 0;
        int date = 1900;
        for (int i = 0; i < size; i++) {
            switch (i % 4) {
                case 0 :
                    imageID = R.drawable.history1;
                    break;
                case 1:
                    imageID = R.drawable.history2;
                    break;
                case 2:
                    imageID = R.drawable.history3;
                    break;
                case 3:
                    imageID = R.drawable.history4;
                    break;
            }
            addEvent.add(new HistoryModel(imageID, String.valueOf(date += 5),
                    "Event " + String.valueOf(i+1),
                    "This is a description of an event that happened in history. " +
                            "Something happened during some time in the past in some place in " +
                            "some country in some continent on planet Earth. " +
                            "Something something something something something and something else.",
                    false));
        }
        return addEvent;
    }
}