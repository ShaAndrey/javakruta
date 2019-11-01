package com.example.gogot;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.icu.text.Edits;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;


public class Board {
    private BoardCell[][] gameBoard;
    int height, width;
    private Point playerPosition;

    Board(int n, int m) {
        gameBoard = new BoardCell[n][m];
        height = n;
        width = m;

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
        for (int i = 0; totalCells < n * m; ++i) {
            for (int j = 0; j <= i; ++j) {
                generatedCells.add(new BoardCell(states[i + 1], i, j));
            }
            totalCells += i + 1;
        }
        return generatedCells;
    }

    BoardCell[][] getGameBoard() {
        return gameBoard;
    }

    ArrayList<BoardCell> getCellsAvailableToMove() {
        ArrayList<BoardCell> availableCells = new ArrayList<>();
        for (int i = 0; i < height; ++i) {
            if (i != playerPosition.x && gameBoard[i][playerPosition.y].getState() != BoardCell.State.NOTHING) {
                availableCells.add(gameBoard[i][playerPosition.y]);
            }
        }
        for (int j = 0; j < width; ++j) {
            if (j != playerPosition.y && gameBoard[playerPosition.x][j].getState() != BoardCell.State.NOTHING) {
                availableCells.add(gameBoard[j][playerPosition.y]);
            }
        }
        return availableCells;
    }

    void movePlayer(BoardCell newPosition) {
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

    public BoardCell getGameBoardCell(Point p) {
        return gameBoard[p.x][p.y];
    }

    public Point getPlayerPosition() {
        return playerPosition;
    }
}
