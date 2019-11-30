package com.example.gogot.relation;

import android.graphics.Point;

import com.example.gogot.model.entity.BoardCard;
import com.example.gogot.model.GameModel;

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
        view.drawPlayersHands(model.getPlayersCards());
    }


    @Override
    public void updateIlluminationAndCollectCards() {
        view.collectCards(model.getCardsToCollect());
        view.refreshBoard(model.getBoard().getBoardCards(),
                new ArrayList<>(model.getBoard().getCellsAvailableToMove()));
        view.addCardsToPlayer(model.getStateOfCardsToCollect(), model.getPlayerIndex());
        view.updatePlayerPoints();
        view.updatePlayersIllumination(model.getPlayerIndex());
        model.nextPlayer();
        if (model.isPlayer() && model.isMovePossible()) {
            view.revalidateBoardCellsListeners(model.getBoard().getBoardCards());
        } else if (model.isMovePossible()) {
            handleTurn(model.botPickPosition());
        } else {
            stopGame();
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
        } else {
            view.youCantMoveThere();
        }
    }
}
