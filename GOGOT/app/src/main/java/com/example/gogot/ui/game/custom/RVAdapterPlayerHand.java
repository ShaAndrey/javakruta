package com.example.gogot.ui.game.custom;

import android.graphics.drawable.GradientDrawable;
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
    private int padding = 10;
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
        amountTextView.setTextSize(30);
    }

    private void initializeImageView(ImageView imageView, int position) {
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setPadding(padding, padding, padding, padding);
        GradientDrawable border = new GradientDrawable();
        border.setColor(0xffEB5D0D);

        if (cards.get(position).getDominatesState()) {
            border.setStroke(padding, 0xFFffd700);
        } else {
            border.setStroke(1, 0x66000000);
        }
        imageView.setBackground(border);
        if (position == 0) {
            imageView.setImageResource(setImageToPlayerCard());
        } else {
            imageView.setImageResource(setImageToCard(cards.get(position)));
        }
    }

    private int setImageToPlayerCard() {
        return playerPicId;
    }

    public void updatePlayerPoints() {
        notifyItemChanged(getIndexForState(PlayCard.State.PLAYER));
    }

    public void updateIllumination() {
        notifyDataSetChanged();
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