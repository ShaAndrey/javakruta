package com.example.gogot.ui.settings.custom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gogot.R;

public class RVAdapterChoosePlayerPicture
        extends RecyclerView.Adapter<RVAdapterChoosePlayerPicture.PlayerPictureViewHolder> {
    private int[] pictures;
    private int[] playersPics;
    private RVAdapterChoosePlayerPictureListener listener;


    public RVAdapterChoosePlayerPicture(int[] pictures, int[] playersPics) {
        this.pictures = pictures;
        this.playersPics = playersPics;
    }

    @NonNull
    @Override
    public RVAdapterChoosePlayerPicture.PlayerPictureViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.layout_player_pic, parent, false);
        return new RVAdapterChoosePlayerPicture.PlayerPictureViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull RVAdapterChoosePlayerPicture.PlayerPictureViewHolder holder,
                                 int position) {
        holder.playerPicture.setImageResource(pictures[position]);
        holder.playerPicture.setOnClickListener(v -> {
            listener.setPictureToPlayer(position);
            notifyDataSetChanged();
        });
        boolean flag = false;
        for (int i = 0; i < playersPics.length; i++) {
            int ind = playersPics[i];
            if (ind == position) {
                holder.playerTextView.setText(String.valueOf(i + 1));
                holder.subsidiaryPicture.setImageResource(R.drawable.subsiduary_pic);
                flag = true;
                break;
            }
        }
        if (!flag) {
            holder.playerTextView.setText("");
            holder.subsidiaryPicture.setImageResource(R.drawable.transparent);
        }
    }


    @Override
    public int getItemCount() {
        return pictures.length;
    }


    class PlayerPictureViewHolder extends RecyclerView.ViewHolder {
        ImageView playerPicture;
        ImageView subsidiaryPicture;
        TextView playerTextView;

        PlayerPictureViewHolder(View itemView) {
            super(itemView);
            playerPicture = itemView.findViewById(R.id.player_image);
            playerTextView = itemView.findViewById(R.id.text_view_player);
            subsidiaryPicture = itemView.findViewById(R.id.subsidiary_image);
        }
    }

    public interface RVAdapterChoosePlayerPictureListener {
        void setPictureToPlayer(int pos);
    }

    public void setListener(RVAdapterChoosePlayerPictureListener listener) {
        this.listener = listener;
    }
}
