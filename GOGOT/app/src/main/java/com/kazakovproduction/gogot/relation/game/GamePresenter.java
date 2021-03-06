package com.kazakovproduction.gogot.relation.game;

import android.graphics.Point;

import com.kazakovproduction.gogot.model.game.GameModel;
import com.kazakovproduction.gogot.model.game.entity.BoardCard;
import com.kazakovproduction.gogot.ui.game.activity.StartGameActivity;

import java.util.ArrayList;

public class GamePresenter implements MainContract.GamePresenter {
    private MainContract.GameModel model;
    private MainContract.GameView view;

    public GamePresenter(MainContract.GameView view, int amountOfPlayers,
                         StartGameActivity.BotDifficulty botDifficulty) {
        this.view = view;
        this.model = new GameModel(amountOfPlayers, botDifficulty);
    }

    @Override
    public void createView(int amountOfPlayers) {
        view.drawInitialBoard(model.getBoard().getBoardCards(),
                new ArrayList<>(model.getBoard().getCellsAvailableToMove()));
        view.initializePlayerHands();
        view.drawPlayersHands(model.getPlayersCards());
    }

    @Override
    public void updateIlluminationAndCollectCards() {
        view.collectCards(model.getCardsToCollect());
        view.updatePlayersIllumination(model.getPlayerIndex());
        model.nextPlayer();
        if (model.isPlayer() && model.isMovePossible()) {
            view.revalidateBoardCellsListeners(model.getBoard().getBoardCards());
        } else if (model.isMovePossible()) {
            handleTurn(model.botPickPosition());
        } else {
            view.drawPlayersHands(model.getPlayersCards());
            view.stopGame(model.getPlayersForEndGame());
        }
    }

    @Override
    public void handleTurn(BoardCard boardCard) {
        if (model.isMovePossible(boardCard)) {
            view.invalidateBoardCellsListeners();
            model.handleTurn(boardCard);
            view.movePlayer(model.getPlayerCard(),
                    new Point(boardCard.getRow(), boardCard.getColumn()));
        } else {
            view.youCantMoveThere();
        }
    }

}
