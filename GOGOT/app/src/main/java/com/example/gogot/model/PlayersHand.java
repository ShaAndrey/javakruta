package com.example.gogot.model;

import java.util.ArrayList;

abstract public class PlayersHand {
    protected ArrayList<PlayCard> inHandCards;

    PlayersHand() {
        inHandCards = new ArrayList<PlayCard>();
    }

    void addCardToHand(PlayCard card) {
        inHandCards.add(card);
    }

    void removeCardFromHand(PlayCard card) {
        inHandCards.remove(card);
    }
}
