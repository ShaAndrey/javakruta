package com.example.gogot;

import android.graphics.Picture;
import android.graphics.Point;

class BoardCell {
    enum State {
        NOTHING(0), PLAYER(1), DRAGON(2), OGRE(3), MINOTAUR(4), ELF(5), FAIRY(6), GNOME(7), GOBLIN(8);

        private int code;

        State(int state) {
            this.code = state;
        }
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
