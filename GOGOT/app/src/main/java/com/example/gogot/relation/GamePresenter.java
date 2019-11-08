package com.example.gogot.relation;

import android.graphics.Picture;
import android.graphics.Point;

import com.example.gogot.model.Board;
import com.example.gogot.model.GameModel;

public class GamePresenter implements MainContract.Presenter {
    private MainContract.Model model;
    private MainContract.View view;


    @Override
    public Board getGameBoard() {
        return model.getGameBoard();
    }

    public GamePresenter(MainContract.View view) {
        this.view = view;
        this.model = new GameModel();
    }

    @Override
    public void handleTurn(Point newPlayerPosition) {
        model.handleTurn(newPlayerPosition);
        view.refreshBoard();
    }

    @Override
    public void getCards() {

    }

    @Override
    public void getPoints() {

    }

    @Override
    public Picture getBoardCellPicture(Point cellPosition) {
        return null;
    }

    @Override
    public int getBoardSize() {
        return model.getBoardSize();
    }
}
