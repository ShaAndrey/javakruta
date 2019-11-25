package com.example.gogot.relation;

import android.graphics.Point;

import com.example.gogot.model.BoardCard;
import com.example.gogot.model.GameModel;
import com.example.gogot.model.PlayCard;

import java.util.ArrayList;

public class GamePresenter implements MainContract.Presenter {
    private MainContract.Model model;
    private MainContract.View view;

    public GamePresenter(MainContract.View view, int amountOfPlayers) {
        this.view = view;
        this.model = new GameModel(amountOfPlayers);
    }

    @Override
    public void stopGame() {
        view.stopGame();
    }

    @Override
    public void createView(int amountOfPlayers) {
        view.drawInitialBoard(model.getBoard().getBoardCards(),
                new ArrayList<>(model.getBoard().getCellsAvailableToMove()));
        view.drawPlayersHands();
    }


    @Override
    public void updateIlluminationAndCollectCards() {
        view.collectCards(model.getCardsToCollect());
        view.refreshBoard(model.getBoard().getBoardCards(),
                new ArrayList<>(model.getBoard().getCellsAvailableToMove()));
        view.addCardsToPlayer(model.getStateOfCardsToCollect(),
                model.getAmountOfCardsToCollect(), model.getPlayerIndex());
        view.updatePlayerPoints(model.getPoints());
        view.updatePlayersIllumination(model.getPlayersDominateStates(), model.getPlayerIndex());
        model.nextPlayer();
        if (model.isPlayer()) {
            view.revalidateBoardCellsListeners(model.getBoard().getBoardCards());
        } else {
            handleTurn(model.botPickPosition());
        }
    }

    @Override
    public void handleTurn(BoardCard boardCard) {
        if (model.isMovePossible(boardCard)) {
            view.invalidateBoardCellsListeners();
            view.removeIllumination(model.getBoard().getBoardCards());
            model.handleTurn(boardCard);
            view.movePlayer(model.getPlayerCard(),
                    new Point(boardCard.getRow(), boardCard.getColumn()));
            if (model.isMovePossible()) {
                stopGame();
            }
        } else {
            view.youCantMoveThere();
        }
    }
}
