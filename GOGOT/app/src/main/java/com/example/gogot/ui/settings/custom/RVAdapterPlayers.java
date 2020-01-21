package com.example.gogot.ui.settings.custom;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gogot.R;

public class RVAdapterPlayers extends
        RecyclerView.Adapter<RVAdapterPlayers.PlayerPictureViewHolder> {
    private int[] pictures;
    private int[] playersPics;

    public RVAdapterPlayers(int[] pictures, int[] playersPics) {
        this.pictures = pictures;
        this.playersPics = playersPics;
    }

    @NonNull
    @Override
    public PlayerPictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.layout_player, parent, false);
        return new RVAdapterPlayers.PlayerPictureViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PlayerPictureViewHolder holder, int position) {
        holder.playerTextView.setText(String.valueOf(position + 1));
        holder.playerPicture.setImageResource(pictures[playersPics[position]]);
    }


    @Override
    public int getItemCount() {
        return playersPics.length;
    }

    class PlayerPictureViewHolder extends RecyclerView.ViewHolder {
        ImageView playerPicture;
        //        ImageView subsidiaryPicture;
        TextView playerTextView;

        PlayerPictureViewHolder(View itemView) {
            super(itemView);
            playerPicture = itemView.findViewById(R.id.player_image);
            playerTextView = itemView.findViewById(R.id.text_view_player);
        }
    }
}
