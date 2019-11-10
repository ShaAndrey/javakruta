package com.example.gogot.model;

import android.graphics.Point;

import com.example.gogot.relation.MainContract;

import java.util.ArrayList;

public class GameModel implements MainContract.Model {
    private Board board;
    private ArrayList<PlayersHand> playersHandArrayList;
    private int boardSize = 6;

    public GameModel() {
        board = new Board(boardSize, boardSize);
    }

    @Override
    public ArrayList<Point> handleTurn(Point newPlayerPosition) {
        return null;
    }

    @Override
    public boolean isMovePossible() {
        return !board.getCellsAvailableToMove().isEmpty();
    }

    @Override
    public boolean isMovePossible(Point newPlayerPosition) {
        return false;
    }
}
