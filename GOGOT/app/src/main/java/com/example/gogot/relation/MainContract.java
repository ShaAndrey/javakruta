package com.example.gogot.relation;

import android.graphics.Point;

import com.example.gogot.model.Board;
import com.example.gogot.model.BoardCard;
import com.example.gogot.model.PlayCard;

import java.util.ArrayList;

public interface MainContract {
    interface View {
        void drawInitialBoard(BoardCard[][] gameBoard,
                              ArrayList<PlayCard> cardsToMove);

        void drawPlayersHands();

        void movePlayer(BoardCard playerCard, Point newPlayerPosition);

        void collectCards(ArrayList<BoardCard> cardsToCollect);

        void youCantMoveThere();

        void stopGame();

        void removeIllumination(BoardCard[][] boardCards);

        void refreshBoard(BoardCard[][] gameBoard,
                          ArrayList<PlayCard> cardsToMove);

        void addCardsToPlayer(PlayCard.State stateOfCardsToAdd,
                              int amountOfCardsToAdd, int playerInd);
    }

    interface Presenter {
        void createView();

        void handleTurn(BoardCard boardCard);

        void stopGame();

        void updateIlluminationAndCollectCards();

    }

    interface Model {
        ArrayList<BoardCard> handleTurn(BoardCard boardCard);

        boolean isMovePossible();

        boolean isMovePossible(BoardCard boardCard);

        Board getBoard();

        Point getPlayerPosition();

        BoardCard getPlayerCard();

        ArrayList<BoardCard> getCardsToCollect();

        int getPlayerIndex();

        void nextPlayer();


        int getAmountOfCardsToCollect();

        PlayCard.State getStateOfCardsToCollect();
    }
}
