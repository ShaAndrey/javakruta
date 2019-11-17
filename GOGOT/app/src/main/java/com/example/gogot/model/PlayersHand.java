package com.example.gogot.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlayersHand {
    private Map<PlayCard.State, Integer> inHandCards;

    PlayersHand() {
        inHandCards = new HashMap<PlayCard.State, Integer>();
    }

    void addNCardsToHand(PlayCard.State state, int n) {
        int count = inHandCards.containsKey(state) ? inHandCards.get(state) : 0;
        inHandCards.put(state, count + n);
    }

    void removeCardFromHand(PlayCard.State state) {
        int count = inHandCards.get(state);
        inHandCards.put(state, count - 1);
    }
}
