package com.example.gogot.model;


public abstract class PlayCard {
    public enum State {
        NOTHING, PLAYER, DRAGON, OGRE, MINOTAUR, ELF, FAIRY, GNOME, GOBLIN;
    }

    protected State state;


    public State getState() {
        return state;
    }


    void setState(State newState) {
        state = newState;
    }
}
