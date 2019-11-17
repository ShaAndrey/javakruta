package com.example.gogot.model;

import java.util.ArrayList;

public class Players {
    int amountOfPlayers = 2;            // TODO add multiplayer
    int currentPlayer = 0;
    ArrayList<PlayersHand> playersHands;

    Players() {
        playersHands = new ArrayList<>();
        for (int i = 0; i < amountOfPlayers; i++) {
            playersHands.add(new PlayersHand());
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
}
