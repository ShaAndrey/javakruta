package com.example.gogot.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlayersHand {
    private Map<PlayCard, Integer> inHandCards;

    PlayersHand() {
        inHandCards = new HashMap<PlayCard, Integer>();
    }

    void addCardToHand(PlayCard card) {
        int count = inHandCards.containsKey(card) ? inHandCards.get(card) : 0;
        inHandCards.put(card, count + 1);
    }

    void removeCardFromHand(PlayCard card) {
        int count = inHandCards.get(card);
        inHandCards.put(card, count - 1);
    }
}
