package edu.northeastern.numad23fa_groupproject1.History;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.numad23fa_groupproject1.R;

public class RVViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageIV;
    public TextView dateTV;
    public TextView eventNameTV;
    public TextView descriptionTV;

    public RVViewHolder(@NonNull View itemView) {
        super(itemView);

        imageIV = itemView.findViewById(R.id.item_image);
        dateTV = itemView.findViewById(R.id.item_date);
        eventNameTV = itemView.findViewById(R.id.item_event_name);
        descriptionTV = itemView.findViewById(R.id.item_description);
    }
}
