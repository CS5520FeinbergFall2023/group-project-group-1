package edu.northeastern.numad23fa_groupproject1.Learn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.AndroidEntryPoint;
import edu.northeastern.numad23fa_groupproject1.R;
import edu.northeastern.numad23fa_groupproject1.common.ItemClickListener;

@AndroidEntryPoint
public class ModuleSelectionActivity extends AppCompatActivity {

    private static final String TAG = "ModuleSelectionActivity";

    private List<ModuleModel> modules;

    private RVModulesAdapter adapter;

    private SharedPreferences sharedPreferences;

    @Inject
    @Named("LearnModuleServiceImpl")
    LearnModuleService learnService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_selection);

        RecyclerView rvModulesList = findViewById(R.id.rvModules);

        // Initialize contacts
        modules = new ArrayList<>();
        // Create adapter passing in the sample user data
        adapter = new RVModulesAdapter(modules, position -> {
            Intent intent = new Intent(new Intent(ModuleSelectionActivity.this, ModuleContentPagerActivity.class));
            Bundle bundle = new Bundle();
            bundle.putParcelable("content", modules.get(position));
            intent.putExtras(bundle);
            startActivity(intent);
        });
        // Attach the adapter to the recyclerview to populate items
        rvModulesList.setAdapter(adapter);
        // Set layout manager to position the items
        rvModulesList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                if(message.obj != null) {
                    modules.addAll((List<ModuleModel>) message.obj);
                    adapter.notifyDataSetChanged();
                }
            }
        };

        sharedPreferences = getSharedPreferences("admin1", MODE_PRIVATE);
        String country = sharedPreferences.getString("COUNTRY", "");

        learnService.getAllModules(country, handler);
    }
}