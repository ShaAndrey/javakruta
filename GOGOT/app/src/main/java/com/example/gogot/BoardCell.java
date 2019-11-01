package com.example.gogot;

import android.graphics.Picture;
import android.graphics.Point;

class BoardCell {
    enum State {
        NOTHING, PLAYER, DRAGON, OGRE, MINOTAUR, ELF, FAIRY, GNOME, GOBLIN;
    }

    private State state;
    private Picture picture;
    private Point position;

    BoardCell(State state, int row, int column) {
        this.state = state;
        this.position = new Point(row, column);
    }

    int getRow() {
        return position.x;
    }

    int getColumn() {
        return position.y;
    }

    Point getPosition() { return position; }

    State getState() {
        return state;
    }

    Picture getPicture() {
        return picture;
    }

    private void setPicture() {
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
