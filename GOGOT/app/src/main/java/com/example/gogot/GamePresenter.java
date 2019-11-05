package com.example.gogot;

import android.graphics.Picture;
import android.graphics.Point;

public class GamePresenter implements MainContract.Presenter {
    private MainContract.Model model;
    private MainContract.View view;

    GamePresenter(MainContract.View view) {
        this.view = view;
//        this.model = new MainContract.Model();        // ошибка, пока не определилимодель
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
}
