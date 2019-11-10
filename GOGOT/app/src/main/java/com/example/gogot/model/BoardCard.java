package com.example.gogot.model;

import android.graphics.Point;

public class BoardCard extends PlayCard {

    private Point position;

    BoardCard(State state, int row, int column) {
        super.state = state;
        this.position = new Point(row, column);
    }

    int getRow() {
        return position.x;
    }

    int getColumn() {
        return position.y;
    }

    Point getPosition() { return position; }

}
