package com.example.gogot.model;

import android.graphics.Point;

import java.util.Objects;

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

    Point getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardCard boardCard = (BoardCard) o;
        return Objects.equals(position, boardCard.position)
                && Objects.equals(state, boardCard.state);
    }
}
