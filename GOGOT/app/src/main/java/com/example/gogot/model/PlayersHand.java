package com.example.gogot.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PlayersHand {
    private Map<PlayCard.State, Integer> inHandCards;
    private int points;
    private PlayerListener playerListener;
    protected boolean[] dominateStates;

    PlayersHand() {
        inHandCards = new HashMap<>();
        ArrayList<PlayCard.State> states =
                new ArrayList<>(Arrays.asList(PlayCard.State.values()));
        states.forEach(state -> inHandCards.put(state, 0));
        dominateStates = new boolean[9];
        points = 0;
    }

    PlayersHand(PlayersHand otherHand) {
        inHandCards = new HashMap<>();
        ArrayList<PlayCard.State> states =
                new ArrayList<>(Arrays.asList(PlayCard.State.values()));
        states.forEach(state -> inHandCards.put(state,
                otherHand.inHandCards.get(state)));
        dominateStates = new boolean[9];
        for (int i = 0; i < dominateStates.length; i++) {
            dominateStates[i] = otherHand.dominateStates[i];
        }
        points = otherHand.points;
    }

    void addNCardsToHand(PlayCard.State state, int n) {
        int count = inHandCards.containsKey(state) ? inHandCards.get(state) : 0;
        inHandCards.put(state, count + n);
        points += n;
        playerListener.checkIfPlayerDominatesState(state);
    }

    void removeCardFromHand(PlayCard.State state) {
        int count = inHandCards.get(state);
        inHandCards.put(state, count - 1);
        --points;
        playerListener.checkIfPlayerDominatesState(state);
    }

    interface PlayerListener {
        void checkIfPlayerDominatesState(PlayCard.State state);
    }

    int getAmountForState(PlayCard.State state) {
        return inHandCards.get(state);
    }

    int getPoints() {
        return points;
    }

    void setPoints(int points) {
        this.points = points;
    }

    void addPoints(int toAdd) {
        this.points += toAdd;
    }

    void setPlayerListener(Players players) {
        playerListener = players;
    }

    boolean checkDominateState(int i) {
        return dominateStates[i];
    }

    void setDominateState(int index, boolean value) {
        dominateStates[index] = value;
    }

    void initState(PlayCard.State state) {
        inHandCards.put(state, 0);
    }

    boolean[] getDominateStates() {
        return dominateStates;
    }
}
