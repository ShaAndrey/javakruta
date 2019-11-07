package com.example.gogot;

import android.graphics.Picture;

public abstract class PlayCard {
    enum State {
        NOTHING, PLAYER, DRAGON, OGRE, MINOTAUR, ELF, FAIRY, GNOME, GOBLIN;
    }

    protected State state;
    protected Picture picture;


    State getState() {
        return state;
    }

    Picture getPicture() {
        return picture;
    }

    private void setPicture() {
        picture = new Picture();
        switch (state) {
            // TODO in each case setting the appropriate picture
            case NOTHING:
            case PLAYER:
            case DRAGON:
            case OGRE:
            case MINOTAUR:
            case ELF:
            case FAIRY:
            case GNOME:
            case GOBLIN:
        }
    }

    void setState(State newState) {
        state = newState;
        setPicture();
    }
}
