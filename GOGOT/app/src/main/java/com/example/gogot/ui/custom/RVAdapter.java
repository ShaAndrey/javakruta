package com.example.gogot.ui.custom;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gogot.R;
import com.example.gogot.model.entity.InHandCard;
import com.example.gogot.model.entity.PlayCard;

import java.util.ArrayList;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.CardViewHolder> {
    private List<InHandCard> cards;
    private RVAdapterListener listener;
    private int padding = 10;

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
        amountTextView.setTextColor(Color.parseColor("#CCFFFF00"));
        amountTextView.setPadding(padding, padding, padding, padding);
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
        imageView.setImageResource(setImageToCard(cards.get(position)));
    }

    public void addCardsAmount(PlayCard.State stateOfCardsToAdd) {
        int ind = getIndexForState(stateOfCardsToAdd);
        notifyItemChanged(ind);
    }

    public void updatePlayerPoints() {
        int ind = getIndexForState(PlayCard.State.PLAYER);
        notifyItemChanged(ind);
    }

    public void updateIllumination() {
        notifyDataSetChanged();
    }

    public RVAdapter(List<InHandCard> playersCards) {
        cards = playersCards;
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