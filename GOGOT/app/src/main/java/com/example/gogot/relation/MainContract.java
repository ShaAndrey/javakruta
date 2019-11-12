package com.example.gogot.relation;

import android.graphics.Picture;
import android.graphics.Point;

import com.example.gogot.model.Board;
import com.example.gogot.model.BoardCard;
import com.example.gogot.model.PlayCard;

import java.util.ArrayList;

public interface MainContract {
    interface View {
        void drawInitialBoard(BoardCard[][] gameBoard,
                              ArrayList<BoardCard> cardsToMove);

        void movePlayer(Point newPlayerPosition);

        void collectCards(ArrayList<Point> cardsToCollect);

        void youCantMoveThere();

        void stopGame();
    }

    interface Presenter {
        void createView();

        void handleTurn(BoardCard boardCard);

        void stopGame();
    }

    interface Model {
        ArrayList<Point> handleTurn(BoardCard boardCard);

        boolean isMovePossible();

        boolean isMovePossible(BoardCard boardCard);

        Board getBoard();
    }
}
