package edu.northeastern.numad23fa_groupproject1.Learn;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class LearnModuleServiceImpl implements LearnModuleService {

    private FirebaseFirestore db;

    public LearnModuleServiceImpl() {
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public Runnable getAllModules() {
        return () -> db.collection("countries")
                .document("india")
                .collection("mod")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            List<ModuleModel> modules = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String title = document.get("title", String.class);
                                List<TranslationsModel> phrases = document.get("phrases", List.class);
                                modules.add(new ModuleModel(title, phrases));
                            }
                        }
                    }
                });
    }
}
