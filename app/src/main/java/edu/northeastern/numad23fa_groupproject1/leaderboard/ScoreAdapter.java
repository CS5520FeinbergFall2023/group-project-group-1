package edu.northeastern.numad23fa_groupproject1.leaderboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.auth.User;

import java.util.List;

import edu.northeastern.numad23fa_groupproject1.R;
import edu.northeastern.numad23fa_groupproject1.UserModel;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewAdapter> {

    List<UserModel> list;
    Context context;
    int i = 1;

    public ScoreAdapter(List<UserModel> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ScoreViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.score_list_item, parent, false);
        return new ScoreViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewAdapter holder, int position) {
        UserModel currentItem = list.get(position);
        holder.name.setText(currentItem.getUsername());
        holder.score.setText("score: " + String.valueOf(currentItem.getScore()));
//        holder.rank.setText(String.valueOf(i));
        holder.rank.setText("#" + String.valueOf(getItemCount() - position));
        //Glide.with(context).load(currentItem.getImage()).into(holder.imageView);
        i++;

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ScoreViewAdapter extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, score, rank;

        public ScoreViewAdapter(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.score_user_img);
            name = itemView.findViewById(R.id.score_user_name);
            score = itemView.findViewById(R.id.static_score_user_result);
            rank = itemView.findViewById(R.id.static_score_user_rank);
        }
    }
}
