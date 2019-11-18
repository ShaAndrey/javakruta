package com.example.gogot.model;

import java.util.ArrayList;
import java.util.List;

public class Players implements PlayersHand.PlayerListener {
    int amountOfPlayers = 1;            // TODO add multiplayer
    int currentPlayer = 0;
    ArrayList<PlayersHand> playersHands;

    Players() {
        playersHands = new ArrayList<>();
        for (int i = 0; i < amountOfPlayers; i++) {
            playersHands.add(new PlayersHand());
            playersHands.get(i).setPlayerListener(this);
        }
    }

    void addCardsToPlayer(PlayCard.State stateOfCardsToCollect, int amountOfCardsToCollect) {
        playersHands.get(currentPlayer).
                addNCardsToHand(stateOfCardsToCollect, amountOfCardsToCollect);
    }

    int getPlayerIndex() {
        return currentPlayer;
    }

    void nextPlayer() {
        currentPlayer = (currentPlayer + 1) % amountOfPlayers;
    }

    @Override
    public void checkIfPlayerDominatesState(PlayCard.State state) {
        PlayersHand maxHand = new PlayersHand();
        maxHand.initState(state);
        for (int i = 0; i < playersHands.size(); i++) {
            if (playersHands.get(i).getAmountForState(state) >
                    maxHand.getAmountForState(state)) {
                maxHand = playersHands.get(i);
            }
        }
        if (playersHands.get(currentPlayer).getAmountForState(state) >=
                maxHand.getAmountForState(state)) {
            maxHand = playersHands.get(currentPlayer);
        }
        if (!maxHand.checkDominateState(state.ordinal())) {
            int points = (state.ordinal() - state.ordinal() % 2) / 2 + 1;
            playersHands.forEach(playersHand -> {
                if (playersHand.checkDominateState(state.ordinal())) {
                    playersHand.setDominateState(state.ordinal(), false);
                    playersHand.addPoints(-points);
                }
            });
            maxHand.addPoints(points);
            maxHand.setDominateState(state.ordinal(), true);
        }
    }

    List<Integer> getPoints() {
        List<Integer> points = new ArrayList<>();
        playersHands.forEach(playersHand -> points.add(playersHand.getPoints()));
        return points;
    }

    List<boolean[]> getPlayersDominateStates() {
        List<boolean[]> playersDominateStates = new ArrayList<>();
        playersHands.forEach(playersHand ->
                playersDominateStates.add(playersHand.getDominateStates()));
        return playersDominateStates;
    }
}
