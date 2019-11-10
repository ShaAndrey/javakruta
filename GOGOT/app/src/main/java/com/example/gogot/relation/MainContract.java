package com.example.gogot.relation;

import android.graphics.Picture;
import android.graphics.Point;

import com.example.gogot.model.Board;
import com.example.gogot.model.BoardCard;

import java.util.ArrayList;

public interface MainContract {
    interface View {
        void drawInitialBoard();
        void movePlayer(Point newPlayerPosition);
        void collectCards(ArrayList<Point> cardsToCollect);
        void youCantMoveThere();
        void stopGame();
    }

    interface Presenter {
        void handleTurn(Point newPlayerPosition);
        void stopGame();
    }

    interface Model {
        ArrayList<Point> handleTurn(Point newPlayerPosition);
        boolean isMovePossible();
        boolean isMovePossible(Point newPlayerPosition);
    }
}
