package com.example.gogot.ui.game.custom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gogot.R;
import com.example.gogot.model.game.entity.InHandCard;
import com.example.gogot.model.game.entity.PlayCard;

import java.util.List;

public class RVAdapterPlayerHand extends RecyclerView.Adapter<RVAdapterPlayerHand.CardViewHolder> {
    private List<InHandCard> cards;
    private RVAdapterListener listener;
    private int playerPicId;

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card, parent, false);
        return new CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        initializeImageView(holder.cardImageView, position);
        initializeAmountView(holder.amountTextView, position);
    }

    private void initializeAmountView(TextView amountTextView, int position) {
        amountTextView.setText(String.valueOf(cards.get(position).getAmount()));
    }

    private void initializeImageView(ImageView imageView, int position) {
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        if (cards.get(position).getDominatesState()) {
            imageView.setBackgroundResource(R.drawable.cell_background_domination);
        } else {
            imageView.setBackgroundResource(R.drawable.background_usual);
        }
        if (position == 0) {
            imageView.setImageResource(setImageToPlayerCard());
        } else {
            imageView.setImageResource(setImageToCard(cards.get(position)));
        }
    }

    private int setImageToPlayerCard() {
        return playerPicId;
    }

    public void updateIllumination() {
        notifyDataSetChanged();
    }

    public void updatePlayersPic(int picId) {
        playerPicId = picId;
        notifyItemChanged(0);
    }

    public RVAdapterPlayerHand(List<InHandCard> playersCards, int playerPicId) {
        cards = playersCards;
        this.playerPicId = playerPicId;
    }


    private int setImageToCard(PlayCard card) {
        return listener.setImageToCard(card);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    private int getIndexForState(PlayCard.State state) {
        return state.ordinal();
    }


    class CardViewHolder extends RecyclerView.ViewHolder {
        ImageView cardImageView;
        TextView amountTextView;

        CardViewHolder(View itemView) {
            super(itemView);
            cardImageView = itemView.findViewById(R.id.view);
            amountTextView = itemView.findViewById(R.id.text);
        }
    }

    public void setListener(RVAdapterListener listener) {
        this.listener = listener;
    }

    public interface RVAdapterListener {
        int setImageToCard(PlayCard card);
    }
}