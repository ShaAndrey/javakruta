package com.example.gogot.model;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;


public class Board {
    private BoardCard[][] gameBoard;
    private int height, width;
    private Point playerPosition;

    Board(int n, int m) {
        gameBoard = new BoardCard[n][m];
        height = n;
        width = m;

        HashSet<BoardCard.State> generatedCells = generateCells(n, m);
        Iterator<BoardCard.State> generatedCellsIterator = generatedCells.iterator();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                BoardCard.State nextState = generatedCellsIterator.next();
                gameBoard[i][j] = new BoardCard(nextState, i, j);
                if (nextState == BoardCard.State.PLAYER) {
                    playerPosition = new Point(i, j);
                }
            }
        }
    }

    private HashSet<BoardCard.State> generateCells(int n, int m) {
        BoardCard.State[] states = BoardCard.State.values();
        HashSet<BoardCard.State> generatedCells = new HashSet<>();
        int totalCells = 0;
        for (int i = 0; totalCells < n * m; ++i) {
            for (int j = 0; j <= i; ++j) {
                generatedCells.add(states[i + 1]);
            }
            totalCells += i + 1;
        }
        return generatedCells;
    }

    public BoardCard[][] getBoardCards() {
        return gameBoard;
    }

    public ArrayList<BoardCard> getCellsAvailableToMove() {
        ArrayList<BoardCard> availableCells = new ArrayList<>();
        for (int i = 0; i < height; ++i) {
            if (i != playerPosition.x && gameBoard[i][playerPosition.y].getState() != BoardCard.State.NOTHING) {
                availableCells.add(gameBoard[i][playerPosition.y]);
            }
        }
        for (int j = 0; j < width; ++j) {
            if (j != playerPosition.y && gameBoard[playerPosition.x][j].getState() != BoardCard.State.NOTHING) {
                availableCells.add(gameBoard[playerPosition.x][j]);
            }
        }
        return availableCells;
    }

    void movePlayer(BoardCard newPosition) {
        gameBoard[playerPosition.x][playerPosition.y].setState(BoardCard.State.NOTHING);
        int rowMoveDirection = (playerPosition.x < newPosition.getRow()) ? 1 : -1;
        int columnMoveDirection = (playerPosition.y < newPosition.getColumn()) ? 1 : -1;
        for (int i = playerPosition.x; i <= newPosition.getRow(); i += rowMoveDirection) {
            for (int j = playerPosition.y; j <= newPosition.getColumn(); j += columnMoveDirection) {
                if (gameBoard[i][j].getState() == newPosition.getState()) {
                    gameBoard[i][j].setState(BoardCard.State.NOTHING);
                    // TODO also ensure that a player earns points
                }
            }
        }
        playerPosition = new Point(newPosition.getRow(), newPosition.getColumn());
        gameBoard[playerPosition.x][playerPosition.y].setState(BoardCard.State.PLAYER);
    }

    public BoardCard getGameBoardCell(Point p) {
        return gameBoard[p.x][p.y];
    }

    public Point getPlayerPosition() {
        return playerPosition;
    }

    public int getSize() {
        return height;
    }
}
