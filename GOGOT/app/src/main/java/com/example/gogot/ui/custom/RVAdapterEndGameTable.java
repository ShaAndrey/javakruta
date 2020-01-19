package com.example.gogot.ui.custom;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gogot.R;
import com.example.gogot.model.Player;

import java.util.List;

public class RVAdapterEndGameTable extends
        RecyclerView.Adapter<RVAdapterEndGameTable.PlayerViewHolder> {
    private List<Player> players;

    public RVAdapterEndGameTable(List<Player> players) {
        this.players = players;
    }

    @NonNull
    @Override
    public RVAdapterEndGameTable.PlayerViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.layout_player_cell, parent, false);
        return new PlayerViewHolder(v);
    }

    @Override
    public void onBindViewHolder
            (@NonNull RVAdapterEndGameTable.PlayerViewHolder holder,
             int position) {
        initPicture(holder.picture, position);
        initPlace(holder.place, position);
        initPoints(holder.points, position);
    }

    @SuppressLint("SetTextI18n")
    private void initPoints(TextView points, int position) {
        points.setText(players.get(position).getPoints() + " "
                + points.getContext().getString(R.string.points));
    }

    @SuppressLint("SetTextI18n")
    private void initPlace(TextView place, int position) {
        place.setText((players.get(position).getPlace() + 1)
                + " " + place.getContext().getString(R.string.place));
    }

    private void initPicture(ImageView picture, int position) {
        picture.setScaleType(ImageView.ScaleType.FIT_CENTER);
        picture.setImageResource(players.get(position).getPictureId());
    }


    class PlayerViewHolder extends RecyclerView.ViewHolder {
        TextView place;
        ImageView picture;
        TextView points;

        PlayerViewHolder(View itemView) {
            super(itemView);
            place = itemView.findViewById(R.id.text_place);
            picture = itemView.findViewById(R.id.player_picture);
            points = itemView.findViewById(R.id.text_points);
        }
    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}
