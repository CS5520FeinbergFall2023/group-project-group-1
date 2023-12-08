package edu.northeastern.numad23fa_groupproject1.Learn;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LearnModuleServiceImpl implements LearnModuleService {

    private FirebaseFirestore db;

    public LearnModuleServiceImpl() {
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void getAllModules(Handler handler) {
        db.collection("countries")
                .document("india")
                .collection("modules")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            List<ModuleModel> modules = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String title = document.get("title", String.class);
                                ModuleModel module = new ModuleModel(title, new ArrayList<>());
                                Map<String, Object> map = document.getData();

                                List<HashMap<String, String>> phrases = (List<HashMap<String, String>>) map.get("phrases");
                                phrases.forEach(item -> {
                                    module.getTranslations().add(new ModuleContentModel(item.get("phrase"),
                                            item.get("translation")));
                                });
                                modules.add(module);
                            }
                            Message message = Message.obtain();
                            message.obj = modules;
                            handler.sendMessage(message);
                        }
                    }
                });
    }
}
