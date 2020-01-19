package com.example.gogot.model;

import com.example.gogot.model.entity.InHandCard;
import com.example.gogot.model.entity.PlayCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayersHand {
    protected List<InHandCard> inHandCards;
    private PlayerListener playerListener;

    public PlayersHand() {
        inHandCards = new ArrayList<>();
        PlayCard.State[] values = PlayCard.State.values();
        for (int i = 1; i < values.length; ++i) {
            PlayCard.State state = values[i];
            inHandCards.add(new InHandCard(state));
        }
    }

    PlayersHand(PlayersHand otherHand) {
        inHandCards = new ArrayList<>(otherHand.inHandCards);
    }

    void addNCardsToHand(PlayCard.State state, int n) {
        getCardByState(state).addToAmount(n);
        getPlayerCard().addToAmount(n);
        playerListener.checkIfPlayerDominatesState(state);
    }

    interface PlayerListener {
        void checkIfPlayerDominatesState(PlayCard.State state);
    }

    int getAmountForState(PlayCard.State state) {
        return getCardByState(state).getAmount();
    }

    int getPoints() {
        return getPlayerCard().getAmount();
    }

    void addPoints(int toAdd) {
        getPlayerCard().addToAmount(toAdd);
    }

    void setPlayerListener(Players players) {
        playerListener = players;
    }

    boolean checkDominateState(PlayCard.State state) {
        return getCardByState(state).getDominatesState();
    }

    public void setDominateState(PlayCard.State state, boolean domination) {
        getCardByState(state).setDominatesState(domination);
    }

    private int getIndByState(PlayCard.State state) {
        return state.ordinal() - 1;
    }

    private InHandCard getCardByState(PlayCard.State state) {
        System.out.println(getIndByState(state));
        System.out.println(state);
        return inHandCards.get(getIndByState(state));
    }

    private InHandCard getPlayerCard() {
        return inHandCards.get(0);
    }

    public List<InHandCard> getInHandCards() {
        return inHandCards;
    }

    public List<InHandCard> getInHandCardsCopy() {
        List<InHandCard> inHandCards = new ArrayList<>();
        this.inHandCards.forEach(inHandCard -> {
            inHandCards.add(new InHandCard(inHandCard));
        });
        return inHandCards;
    }

    public void setInHandCards(List<InHandCard> inHandCards) {
        List<InHandCard> handCards = this.inHandCards;
        for (int i = 0, handCardsSize = handCards.size(); i < handCardsSize; i++) {
            handCards.get(i).setInHandCard(inHandCards.get(i));
        }
    }
}
