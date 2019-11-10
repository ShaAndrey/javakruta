package com.example.gogot.relation;

import android.graphics.Point;

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
        view.drawInitialBoard(model.getBoard().getBoardCards());
    }

    @Override
    public void handleTurn(Point newPlayerPosition) {
        if (model.isMovePossible(newPlayerPosition)) {
            view.movePlayer(newPlayerPosition);
            view.collectCards(model.handleTurn(newPlayerPosition));
            if(model.isMovePossible()) {
                stopGame();
            }
        } else {
            view.youCantMoveThere();
        }
    }
}
