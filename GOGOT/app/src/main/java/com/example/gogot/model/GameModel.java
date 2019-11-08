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

    @Override
    public int getBoardSize() {
        return boardSize;
    }
}
