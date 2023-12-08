package edu.northeastern.numad23fa_groupproject1.Learn;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.numad23fa_groupproject1.R;
import edu.northeastern.numad23fa_groupproject1.common.ItemClickListener;

public class RVModulesAdapter extends RecyclerView.Adapter<RVModulesAdapter.ViewHolder> {

    private List<ModuleModel> modules;

    private ItemClickListener listener;

    public RVModulesAdapter(List<ModuleModel> modules, ItemClickListener listener) {
        this.modules = modules;
        this.listener = listener;
    }

    @Override
    public RVModulesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View moduleView = inflater.inflate(R.layout.rv_module_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(moduleView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RVModulesAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        ModuleModel moduleModel = modules.get(position);

        // Set item views based on your views and data model
        Button moduleBtn = holder.moduleBtn;
        moduleBtn.setText(moduleModel.getTitle());
        moduleBtn.setOnClickListener((View view) -> {
            listener.onClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return modules.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public Button moduleBtn;

        public ViewHolder(View itemView) {
            super(itemView);

            moduleBtn = itemView.findViewById(R.id.module_btn);
        }
    }
}
