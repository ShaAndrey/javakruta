package com.example.gogot.relation;

import android.graphics.Point;

import com.example.gogot.model.Board;
import com.example.gogot.model.BoardCard;

import java.util.ArrayList;

public interface MainContract {
    interface View {
        void drawInitialBoard(BoardCard[][] gameBoard,
                              ArrayList<BoardCard> cardsToMove);

        void movePlayer(Point playerPosition, Point newPlayerPosition);

        void collectCards(ArrayList<BoardCard> cardsToCollect);

        void youCantMoveThere();

        void stopGame();

        void removeIllumination(BoardCard[][] boardCards);

        void refreshBoard(BoardCard[][] gameBoard,
                          ArrayList<BoardCard> cardsToMove);
    }

    interface Presenter {
        void createView();

        void handleTurn(BoardCard boardCard);

        void stopGame();
    }

    interface Model {
        ArrayList<BoardCard> handleTurn(BoardCard boardCard);

        boolean isMovePossible();

        boolean isMovePossible(BoardCard boardCard);

        Board getBoard();

        Point getPlayerPosition();
    }
}
