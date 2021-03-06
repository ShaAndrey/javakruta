package com.kazakovproduction.gogot.model.game;

import android.graphics.Point;

import com.kazakovproduction.gogot.model.game.entity.BoardCard;
import com.kazakovproduction.gogot.model.game.entity.PlayCard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;


public class Board {

    private BoardCard[][] gameBoard;
    private int height, width;
    private Point playerPosition;
    private ArrayList<BoardCard> cardsToCollect;
    private BoardListener boardListener;
    private int amountOfCardsToCollect;
    private PlayCard.State stateOfCardsToCollect;

    Board(int n, int m) {
        gameBoard = new BoardCard[n][m];
        height = n;
        width = m;

        HashSet<BoardCard> generatedCells = generateCells(n, m);
        Iterator<BoardCard> generatedCellsIterator = generatedCells.iterator();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                BoardCard nextCard = generatedCellsIterator.next();
                gameBoard[i][j] = new BoardCard(nextCard.state, i, j);
                if (nextCard.getState() == BoardCard.State.PLAYER) {
                    playerPosition = new Point(i, j);
                }
            }
        }
    }

    Board(Board otherBoard) {
        height = otherBoard.height;
        width = otherBoard.width;
        gameBoard = new BoardCard[height][width];
        BoardCard[][] otherBoardCards = otherBoard.getBoardCards();
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                BoardCard nextCard = otherBoardCards[i][j];
                gameBoard[i][j] = new BoardCard(nextCard.state, i, j);
                if (nextCard.getState() == BoardCard.State.PLAYER) {
                    playerPosition = new Point(i, j);
                }
            }
        }
        cardsToCollect = new ArrayList<>(otherBoard.cardsToCollect);
        amountOfCardsToCollect = otherBoard.amountOfCardsToCollect;
        stateOfCardsToCollect = otherBoard.stateOfCardsToCollect;
    }

    private HashSet<BoardCard> generateCells(int n, int m) {
        BoardCard.State[] states = BoardCard.State.values();
        HashSet<BoardCard> generatedCells = new HashSet<>();
        int totalCells = 0;
        for (int i = 0; totalCells < n * m; ++i) {
            for (int j = 0; j <= i; ++j) {
                generatedCells.add(new BoardCard(states[i + 1], i, j));
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

    ArrayList<BoardCard> handleTurn(BoardCard newPosition) {
        movePlayer(newPosition);
        boardListener.addCardsToPlayer(stateOfCardsToCollect, amountOfCardsToCollect);
        return cardsToCollect;
    }

    public void movePlayer(BoardCard newPosition) {
        cardsToCollect = new ArrayList<>();
        amountOfCardsToCollect = 1;
        stateOfCardsToCollect = newPosition.getState();
        gameBoard[playerPosition.x][playerPosition.y].setState(BoardCard.State.NOTHING);
        int rowMoveDirection = (playerPosition.x < newPosition.getRow()) ? 1 : -1;
        int columnMoveDirection = (playerPosition.y < newPosition.getColumn()) ? 1 : -1;
        for (int i = playerPosition.x; i != newPosition.getRow(); i += rowMoveDirection) {
            collectCard(gameBoard[i][playerPosition.y], newPosition);
        }
        for (int j = playerPosition.y; j != newPosition.getColumn(); j += columnMoveDirection) {
            collectCard(gameBoard[playerPosition.x][j], newPosition);
        }
        cardsToCollect.add(gameBoard[newPosition.getRow()][newPosition.getColumn()]);
        playerPosition = new Point(newPosition.getRow(), newPosition.getColumn());
        gameBoard[playerPosition.x][playerPosition.y].setState(BoardCard.State.PLAYER);
    }

    private void collectCard(BoardCard cardToCollect, BoardCard newPosition) {
        if (cardToCollect.getState() == newPosition.getState()) {
            cardToCollect.setState(BoardCard.State.NOTHING);
            cardsToCollect.add(cardToCollect);
            ++amountOfCardsToCollect;
        }
    }

    interface BoardListener {
        void addCardsToPlayer(PlayCard.State stateOfCardsToCollect, int amountOfCardsToCollect);
    }

    public BoardCard getGameBoardCell(Point p) {
        return gameBoard[p.x][p.y];
    }

    Point getPlayerPosition() {
        return playerPosition;
    }

    public int getSize() {
        return height;
    }

    BoardCard getPlayerCard() {
        return gameBoard[playerPosition.x][playerPosition.y];
    }

    ArrayList<BoardCard> getCardsToCollect() {
        return cardsToCollect;
    }

    void setBoardListener(BoardListener listener) {
        boardListener = listener;
    }

    public int getAmountOfCardsToCollect() {
        return amountOfCardsToCollect;
    }

    public PlayCard.State getStateOfCardsToCollect() {
        return stateOfCardsToCollect;
    }

    BoardSnapshot createSnapShot() {
        return new BoardSnapshot(this, gameBoard);
    }

    private void setPlayerPosition(Point playerPosition) {
        this.playerPosition = playerPosition;
    }

    class BoardSnapshot {
        private Board board;
        private BoardCard[][] gameBoardCards;
        private Point playerPosition;

        BoardSnapshot(Board board,
                      BoardCard[][] gameBoardCards) {
            this.board = board;
            this.gameBoardCards = new BoardCard[height][width];
            for (int i = 0; i < height; ++i) {
                for (int j = 0; j < width; ++j) {
                    BoardCard nextCard = gameBoardCards[i][j];
                    this.gameBoardCards[i][j] = new BoardCard(nextCard.state, i, j);
                    if (nextCard.getState() == BoardCard.State.PLAYER) {
                        this.playerPosition = new Point(i, j);
                    }
                }
            }
        }

        void restore() {
            board.restoreGameBoard(gameBoardCards);
            board.setPlayerPosition(playerPosition);
        }
    }

    private void restoreGameBoard(BoardCard[][] gameBoardCards) {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                gameBoard[i][j].setState(gameBoardCards[i][j].getState());
            }
        }
    }
}
