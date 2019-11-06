package com.example.gogot;

import android.graphics.Point;

import java.util.ArrayList;

public class GameModel implements MainContract.Model {
    private Board board;
    private ArrayList<PlayersHand> playersHandArrayList;

    GameModel (int n, int m) {
        board = new Board(n, m);
    }

    @Override
    public void handleTurn(Point newPlayerPosition) {

    }

    @Override
    public Point getPlayerPosition() {
        return null;
    }

    @Override
    public BoardCard.State getBoardCellState() {
        return null;
    }

    @Override
    public Board getGameBoard() {
        return board;
    }

    @Override
    public void getPoints() {

    }

    @Override
    public void getCards() {

    }
}
