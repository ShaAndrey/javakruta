package com.example.gogot;

import android.graphics.Picture;
import android.graphics.Point;

public class GamePresenter implements MainContract.Presenter {
    private MainContract.Model model;
    private MainContract.View view;

    @Override
    public void handleTurn(Point newPlayerPosition) {

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
