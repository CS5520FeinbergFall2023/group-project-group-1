package edu.northeastern.numad23fa_groupproject1.Learn;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.HashMap;
import java.util.Map;

import edu.northeastern.numad23fa_groupproject1.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModuleContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModuleContentFragment extends Fragment {

    private static final String ITEM_PARAM;

    private static final Map<String, Integer> imageIds;

    static {
        ITEM_PARAM = "item";
        imageIds = new HashMap<>();
        imageIds.put("namaste", R.drawable.namaste);
        imageIds.put("man", R.drawable.man);
        imageIds.put("woman", R.drawable.woman);
        imageIds.put("greeting", R.drawable.greeting);
        imageIds.put("hi", R.drawable.hi);
    }

    private ModuleContentModel model;

    public ModuleContentFragment() {
        // Required empty public constructor
    }

    public static ModuleContentFragment newInstance(Parcelable content) {
        ModuleContentFragment fragment = new ModuleContentFragment();
        Bundle args = new Bundle();
        args.putParcelable(ITEM_PARAM, content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = getArguments().getParcelable(ITEM_PARAM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.module_content_fragment, container, false);
        TextView nativePhrase = view.findViewById(R.id.native_phrase);
        TextView translatedPhrase = view.findViewById(R.id.translated_phrase);
        nativePhrase.setText(model.getNativePhrase());
        translatedPhrase.setText(model.getTranslation());
        ImageView img = view.findViewById(R.id.supporting_image);
        img.setImageResource(imageIds.get(model.getIdentifier()));
        return view;
    }
}