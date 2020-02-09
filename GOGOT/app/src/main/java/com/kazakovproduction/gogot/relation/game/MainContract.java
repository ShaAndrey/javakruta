package com.kazakovproduction.gogot.relation.game;

import android.graphics.Point;

import com.kazakovproduction.gogot.model.game.Board;
import com.kazakovproduction.gogot.model.game.Player;
import com.kazakovproduction.gogot.model.game.Players;
import com.kazakovproduction.gogot.model.game.entity.BoardCard;
import com.kazakovproduction.gogot.model.game.entity.InHandCard;
import com.kazakovproduction.gogot.model.game.entity.PlayCard;

import java.util.ArrayList;
import java.util.List;

public interface MainContract {
    interface GameView {
        void drawInitialBoard(BoardCard[][] gameBoard,
                              ArrayList<PlayCard> cardsToMove);

        void drawPlayersHands(List<List<InHandCard>> playersCards);

        void movePlayer(BoardCard playerCard, Point newPlayerPosition);

        void collectCards(ArrayList<BoardCard> cardsToCollect);

        void youCantMoveThere();

        void stopGame(ArrayList<Player> players);

        void updatePlayersIllumination(int currentPlayer);

        void invalidateBoardCellsListeners();

        void revalidateBoardCellsListeners(BoardCard[][] boardCards);

        void initializePlayerHands();
    }

    interface GamePresenter {
        void createView(int amountOfPlayers);

        void handleTurn(BoardCard boardCard);

        void updateIlluminationAndCollectCards();
    }

    interface GameModel {
        ArrayList<BoardCard> handleTurn(BoardCard boardCard);

        boolean isMovePossible();

        boolean isMovePossible(BoardCard boardCard);

        Board getBoard();

        BoardCard getPlayerCard();

        ArrayList<BoardCard> getCardsToCollect();

        int getPlayerIndex();

        void nextPlayer();

        boolean isPlayer();

        BoardCard botPickPosition();

        List<List<InHandCard>> getPlayersCards();

        ArrayList<Player> getPlayersForEndGame();

        Players getPlayers();
    }
}
