package com.example.gogot.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class EventManager {
    private HashMap<String, List<Bot>> eventsToListeners;

    void subscribe(String eventType, Bot player) {
        List<Bot> bots = (eventsToListeners.containsKey(eventType)) ?
                eventsToListeners.get(eventType) : new ArrayList<Bot>();
        bots.add(player);
        eventsToListeners.put(eventType, bots);

    }

    void unsubscribe(String eventType, List<Bot> player) {
        eventsToListeners.remove(eventType, player);
    }

//    void notify(String eventType, Board data) {
//        eventsToListeners.get(eventType).forEach(bot -> bot.update(data));
//    }
}
