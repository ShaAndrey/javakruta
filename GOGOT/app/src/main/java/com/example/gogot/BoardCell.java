package com.example.gogot;

import android.graphics.Picture;
import android.graphics.Point;

class BoardCell extends GameCell {

    private Point position;

    BoardCell(State state, int row, int column) {
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
