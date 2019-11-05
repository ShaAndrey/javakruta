package com.example.gogot;

import android.graphics.Point;

class BoardCard extends PlayCard {

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
