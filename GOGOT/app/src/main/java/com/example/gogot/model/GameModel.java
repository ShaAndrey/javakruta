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
    public ArrayList<Point> handleTurn(BoardCard boardCard) {
        return null;
    }



    @Override
    public boolean isMovePossible() {
        return !board.getCellsAvailableToMove().isEmpty();
    }

    @Override
    public boolean isMovePossible(BoardCard boardCard) {
        return board.getCellsAvailableToMove().contains(boardCard);
    }

    @Override
    public Board getBoard() {
        return board;
    }
}
