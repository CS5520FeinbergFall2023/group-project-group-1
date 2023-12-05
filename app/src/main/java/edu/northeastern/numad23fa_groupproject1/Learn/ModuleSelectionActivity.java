package edu.northeastern.numad23fa_groupproject1.Learn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.AndroidEntryPoint;
import edu.northeastern.numad23fa_groupproject1.R;

@AndroidEntryPoint
public class ModuleSelectionActivity extends AppCompatActivity {

    @Inject
    @Named("LearnModuleServiceImpl")
    LearnModuleService learnService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_selection);
    }
}