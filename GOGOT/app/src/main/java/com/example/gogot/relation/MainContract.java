package com.example.gogot.relation;

import android.graphics.Point;

import com.example.gogot.model.Board;
import com.example.gogot.model.entity.BoardCard;
import com.example.gogot.model.entity.InHandCard;
import com.example.gogot.model.entity.PlayCard;

import java.util.ArrayList;
import java.util.List;

public interface MainContract {
    interface View {
        void drawInitialBoard(BoardCard[][] gameBoard,
                              ArrayList<PlayCard> cardsToMove);

        void drawPlayersHands(List<List<InHandCard>> playersCards);

        void movePlayer(BoardCard playerCard, Point newPlayerPosition);

        void collectCards(ArrayList<BoardCard> cardsToCollect);

        void youCantMoveThere();

        void stopGame();

        void removeIllumination(BoardCard[][] boardCards);

        void refreshBoard(BoardCard[][] gameBoard,
                          ArrayList<PlayCard> cardsToMove);

        void addCardsToPlayer(PlayCard.State stateOfCardsToAdd, int playerInd);

        void updatePlayerPoints();

        void updatePlayersIllumination(int currentPlayer);

        void invalidateBoardCellsListeners();

        void revalidateBoardCellsListeners(BoardCard[][] boardCards);

        void initializePlayerHands();

        void setEndGameIllumination(List<Integer> places);
    }

    interface Presenter {
        void createView(int amountOfPlayers);

        void handleTurn(BoardCard boardCard);

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

        List<Integer> getPoints();

        boolean isPlayer();

        BoardCard botPickPosition();

        List<List<InHandCard>> getPlayersCards();

        List<Integer> getPlaces();
    }
}
