package com.kazakovproduction.gogot.ui.settings.custom;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kazakovproduction.gogot.R;
import com.kazakovproduction.gogot.ui.settings.dialog.PictureChoiceDialog;

public class RVAdapterPlayers extends
        RecyclerView.Adapter<RVAdapterPlayers.
                PlayerPictureViewHolder>
        implements PictureChoiceDialog.PictureChoiceDialogListener {
    private int[] pictures;
    private int[] playersPics;
    private RVAdapterPlayersListener listener;

    public RVAdapterPlayers(int[] pictures, int[] playersPics) {
        this.pictures = pictures;
        this.playersPics = playersPics;
    }

    @NonNull
    @Override
    public PlayerPictureViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.layout_player, parent, false);
        return new RVAdapterPlayers.PlayerPictureViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PlayerPictureViewHolder holder,
                                 int position) {
        holder.playerTextView.setText(String.format(holder.playerTextView.getContext().
                getString(R.string.st_player), (position + 1)));
        holder.playerPicture.setImageResource(pictures[playersPics[position]]);
        holder.playerPicture.setOnClickListener(v -> {
            PictureChoiceDialog pictureChoiceDialog =
                    new PictureChoiceDialog(holder.playerPicture.getContext(),
                            pictures, playersPics, position);
            pictureChoiceDialog.setListener(this);
            pictureChoiceDialog.show();
        });
    }


    @Override
    public int getItemCount() {
        return playersPics.length;
    }

    @Override
    public void setPictureToPlayer(int pic, int player) {
        listener.setPictureToPlayer(pic, player);
        notifyDataSetChanged();
    }

    class PlayerPictureViewHolder extends RecyclerView.ViewHolder {
        ImageView playerPicture;
        TextView playerTextView;

        PlayerPictureViewHolder(View itemView) {
            super(itemView);
            playerPicture = itemView.findViewById(R.id.player_image);
            playerTextView = itemView.findViewById(R.id.text_view_player);
        }
    }

    public interface RVAdapterPlayersListener {
        void setPictureToPlayer(int pic, int player);
    }

    public void setListener(RVAdapterPlayersListener listener) {
        this.listener = listener;
    }
}
