package edu.northeastern.numad23fa_groupproject1.History;

import android.content.Context;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.northeastern.numad23fa_groupproject1.R;

public class RVAdapter extends RecyclerView.Adapter<RVViewHolder> {

    private final ArrayList<HistoryModel> eventList;
    private final Context context;

    public RVAdapter(ArrayList<HistoryModel> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
    }

    @NonNull
    @Override
    public RVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RVViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_card_history, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RVViewHolder holder, int position) {
        HistoryModel currentItem = eventList.get(position);
        holder.imageIV.setImageResource(currentItem.getImageId());
        holder.dateTV.setText(currentItem.getDate());
        holder.eventNameTV.setText(currentItem.getEventName());

        // part of expandable recycler view
        String more = currentItem.getDescription();
        Spanned spanned = HtmlCompat.fromHtml(more, HtmlCompat.FROM_HTML_MODE_COMPACT);
        holder.descriptionTV.setText(currentItem.getDescription());

        // when an item is clicked, it will expand and the description will show
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                HistoryModel event = eventList.get(pos);
                event.setVisibility(!event.isVisibility());
                notifyItemChanged(pos);
            }
        });

        // change the visibility of the description
        boolean isVisible = currentItem.isVisibility();
        holder.itemView.findViewById(R.id.expandedLayout)
                .setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
