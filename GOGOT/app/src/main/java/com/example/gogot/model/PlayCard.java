package com.example.gogot.model;

import android.graphics.Picture;

public abstract class PlayCard {
    public enum State {
        NOTHING, PLAYER, DRAGON, OGRE, MINOTAUR, ELF, FAIRY, GNOME, GOBLIN;
    }

    protected State state;
    protected Picture picture;


    public State getState() {
        return state;
    }


    void setState(State newState) {
        state = newState;
    }
}
