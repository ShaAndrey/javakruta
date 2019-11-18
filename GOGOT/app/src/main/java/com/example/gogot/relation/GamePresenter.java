package com.example.gogot.relation;

import android.graphics.Point;

import com.example.gogot.model.BoardCard;
import com.example.gogot.model.GameModel;

public class GamePresenter implements MainContract.Presenter {
    private MainContract.Model model;
    private MainContract.View view;

    public GamePresenter(MainContract.View view) {
        this.view = view;
        this.model = new GameModel();
    }

    @Override
    public void stopGame() {
        view.stopGame();
    }

    @Override
    public void createView() {
        view.drawInitialBoard(model.getBoard().getBoardCards(),
                model.getBoard().getCellsAvailableToMove());
        view.drawPlayersHands();
    }


    @Override
    public void updateIlluminationAndCollectCards() {
        view.collectCards(model.getCardsToCollect());
        view.refreshBoard(model.getBoard().getBoardCards(),
                model.getBoard().getCellsAvailableToMove());
        view.addCardsToPlayer(model.getStateOfCardsToCollect(),
                model.getAmountOfCardsToCollect(), model.getPlayerIndex());
        view.updatePlayerPoints(model.getPoints());
        view.updatePlayersIllumination(model.getPlayersDominateStates());
        model.nextPlayer();
    }

    @Override
    public void handleTurn(BoardCard boardCard) {
        if (model.isMovePossible(boardCard)) {
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
