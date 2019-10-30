package com.example.gogot;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.Collections;


public class Board {
    private ArrayList<ArrayList<BoardCell>> gameBoard;
    private int playerRow, playerColumn;

    Board(int n, int m) { // n = m = 6
        gameBoard = new ArrayList<>(n);

        State[] states = State.values();
        ArrayList<BoardCell> cellsForShuffle = new ArrayList<>();
        // adding exactly i characters of type i
        for (int i = 1; i * (i + 1) / 2 <= n * m; ++i) { // oh, sorry, but this is awesome
            for (int j = 1; j <= i; ++j) {
                cellsForShuffle.add(new BoardCell(states[i], i, j));
            }
        }
        Collections.shuffle(cellsForShuffle);

        for (int i = 0; i < n; ++i) {
            gameBoard.add(new ArrayList<BoardCell>(m));
            for (int j = 0; j < m; ++j) {
                gameBoard.get(i).add(cellsForShuffle.get(i * n + j));
                if (cellsForShuffle.get(i * n + j).getState() == State.PLAYER) {
                    playerRow = i;
                    playerColumn = j;
                }
            }
        }
    }

    ArrayList<ArrayList<BoardCell>> getGameBoard() {
        return gameBoard;
    }

    ArrayList<BoardCell> getAvailableNeighbours() {
        ArrayList<BoardCell> result = new ArrayList<>();
        for (int i = 0; i < gameBoard.size(); ++i) {
            if (i != playerRow && gameBoard.get(i).get(playerColumn).getState() != State.NOTHING) {
                result.add(gameBoard.get(i).get(playerColumn));
            }
        }
        for (int j = 0; j < gameBoard.get(playerRow).size(); ++j) {
            if (j != playerColumn && gameBoard.get(playerRow).get(j).getState() != State.NOTHING) {
                result.add(gameBoard.get(j).get(playerColumn));
            }
        }
        return result;
    }

    @SuppressLint("Assert")
    void MovePlayer(BoardCell newPosition) {
        assert newPosition.getRow() == playerRow || newPosition.getColumn() == playerColumn;
        gameBoard.get(playerRow).get(playerColumn).setState(State.NOTHING);
        for (int i = Math.min(newPosition.getRow(), playerRow);
             i <= Math.max(newPosition.getRow(), playerRow); ++i) {
            for (int j = Math.min(newPosition.getColumn(), playerColumn);
                 j <= Math.max(newPosition.getColumn(), playerColumn); ++j) {
                if (gameBoard.get(i).get(j).getState() == newPosition.getState()) {
                    gameBoard.get(i).get(j).setState(State.NOTHING);
                    // also ensure that a player earns points TBD
                }
            }
        }
        playerRow = newPosition.getRow();
        playerColumn = newPosition.getColumn();
        gameBoard.get(playerRow).get(playerColumn).setState(State.PLAYER);
    }
}
