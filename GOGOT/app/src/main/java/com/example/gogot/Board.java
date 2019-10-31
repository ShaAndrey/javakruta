package com.example.gogot;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.icu.text.Edits;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;


public class Board {
    private BoardCell[][] gameBoard;
    private Point playerPosition;

    Board(int n, int m) {
        gameBoard = new BoardCell[n][m];

        HashSet<BoardCell> generatedCells = generateCells(n, m);
        Iterator<BoardCell> generatedCellsIterator = generatedCells.iterator();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                gameBoard[i][j] = generatedCellsIterator.next();
                if (generatedCellsIterator.next().getState() == BoardCell.State.PLAYER) {
                    playerPosition = new Point(i, j);
                }
            }
        }
    }

    private HashSet<BoardCell> generateCells(int n, int m) {
        BoardCell.State[] states = BoardCell.State.values();
        HashSet<BoardCell> generatedCells = new HashSet<>();
        int totalCells = 0;
        for (int i = 1; totalCells <= n * m; ++i) {
            for (int j = 1; j <= i; ++j) {
                generatedCells.add(new BoardCell(states[i], i, j));
            }
            totalCells += i;
        }
        return generatedCells;
    }

    BoardCell[][] getGameBoard() {
        return gameBoard;
    }

    ArrayList<BoardCell> getCellsAvailableToMove() {
        ArrayList<BoardCell> result = new ArrayList<>();
        for (int i = 0; i < gameBoard.length; ++i) {
            if (i != playerPosition.x && gameBoard[i][playerPosition.y].getState() != BoardCell.State.NOTHING) {
                result.add(gameBoard[i][playerPosition.y]);
            }
        }
        for (int j = 0; j < gameBoard[playerPosition.x].length; ++j) {
            if (j != playerPosition.y && gameBoard[playerPosition.x][j].getState() != BoardCell.State.NOTHING) {
                result.add(gameBoard[j][playerPosition.y]);
            }
        }
        return result;
    }

    @SuppressLint("Assert")
    void MovePlayer(BoardCell newPosition) {
        assert newPosition.getRow() == playerPosition.x || newPosition.getColumn() == playerPosition.y;
        gameBoard[playerPosition.x][playerPosition.y].setState(BoardCell.State.NOTHING);
        int rowMoveDirection = (playerPosition.x < newPosition.getRow()) ? 1 : -1;
        int columnMoveDirection = (playerPosition.y < newPosition.getColumn()) ? 1 : -1;
        for (int i = playerPosition.x; i <= newPosition.getRow(); i += rowMoveDirection) {
            for (int j = playerPosition.y; j <= newPosition.getColumn(); j += columnMoveDirection) {
                if (gameBoard[i][j].getState() == newPosition.getState()) {
                    gameBoard[i][j].setState(BoardCell.State.NOTHING);
                    // TODO also ensure that a player earns points
                }
            }
        }
        playerPosition = new Point(newPosition.getRow(), newPosition.getColumn());
        gameBoard[playerPosition.x][playerPosition.y].setState(BoardCell.State.PLAYER);
    }
}
