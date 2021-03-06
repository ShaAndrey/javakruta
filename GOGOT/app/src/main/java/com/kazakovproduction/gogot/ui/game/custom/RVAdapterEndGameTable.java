package com.kazakovproduction.gogot.ui.game.custom;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kazakovproduction.gogot.R;
import com.kazakovproduction.gogot.model.game.Player;

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
    private void initPoints(TextView pointsText, int position) {
        int points = players.get(position).getPoints();
        if (points >= 5 && points <= 20) {
            pointsText.setText(
                    String.format(pointsText.getContext().
                            getString(R.string.pointss), points));
        } else if (points % 10 == 1) {
            pointsText.setText(
                    String.format(pointsText.getContext().
                            getString(R.string.point), points));
        } else if (points % 10 >= 2 && points % 10 <= 4) {
            pointsText.setText(
                    String.format(pointsText.getContext().
                            getString(R.string.points), points));
        } else {
            pointsText.setText(
                    String.format(pointsText.getContext().
                            getString(R.string.pointss), points));
        }
    }

    @SuppressLint("SetTextI18n")
    private void initPlace(TextView place, int position) {
        place.setText(
                String.format(place.getContext().getString(R.string.place),
                        (players.get(position).getPlace())));
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
