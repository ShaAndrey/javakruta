package com.example.gogot.model;


public class PlayCard {
    public static enum State {
        NOTHING, PLAYER, DRAGON, OGRE, MINOTAUR, ELF, FAIRY, GNOME, GOBLIN;
    }

    protected State state;

    public PlayCard(int index) {
        setState(State.values()[index]);
    }

    public PlayCard(State state) {
        setState(state);
    }

    PlayCard (PlayCard otherCard) {
        setState(otherCard.state);
    }

    public State getState() {
        return state;
    }


    void setState(State newState) {
        state = newState;
    }
}
