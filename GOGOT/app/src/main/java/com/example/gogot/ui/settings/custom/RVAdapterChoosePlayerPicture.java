package com.example.gogot.ui.settings.custom;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gogot.R;

public class RVAdapterChoosePlayerPicture
        extends RecyclerView.Adapter<RVAdapterChoosePlayerPicture.PlayerPictureViewHolder> {


    @NonNull
    @Override
    public RVAdapterChoosePlayerPicture.PlayerPictureViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RVAdapterChoosePlayerPicture.PlayerPictureViewHolder holder,
                                 int position) {

    }

    private void initializeAmountView(TextView amountTextView, int position) {
//        amountTextView.setText(String.valueOf(cards.get(position).getAmount()));
        amountTextView.setTextSize(30);
    }



    @Override
    public int getItemCount() {
        return 0;
    }


    class PlayerPictureViewHolder extends RecyclerView.ViewHolder {
        ImageView cardImageView;
        TextView amountTextView;

        PlayerPictureViewHolder(View itemView) {
            super(itemView);
            cardImageView = itemView.findViewById(R.id.view);
            amountTextView = itemView.findViewById(R.id.text);
        }
    }
}
