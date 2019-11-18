package com.example.gogot.model;

import android.graphics.Point;

import com.example.gogot.relation.MainContract;

import java.util.ArrayList;
import java.util.List;

public class GameModel implements MainContract.Model, Board.BoardListener {
    private Board board;
    private Players players;
    private int boardSize = 6;


    @Override
    public void nextPlayer() {
        players.nextPlayer();
    }

    public GameModel() {
        board = new Board(boardSize, boardSize);
        board.setBoardListener(this);
        players = new Players();
    }

    @Override
    public ArrayList<BoardCard> handleTurn(BoardCard boardCard) {
        return board.movePlayer(boardCard);
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

    @Override
    public ArrayList<BoardCard> getCardsToCollect() {
        return board.getCardsToCollect();
    }

    @Override
    public int getPlayerIndex() {
        return players.getPlayerIndex();
    }

    @Override
    public BoardCard getPlayerCard() {
        return board.getPlayerCard();
    }

    @Override
    public Point getPlayerPosition() {
        return board.getPlayerPosition();
    }

    @Override
    public int getAmountOfCardsToCollect() {
        return board.getAmountOfCardsToCollect();
    }

    @Override
    public PlayCard.State getStateOfCardsToCollect() {
        return board.getStateOfCardsToCollect();
    }

    @Override
    public List<boolean[]> getPlayersDominateStates() {
        return players.getPlayersDominateStates();
    }

    @Override
    public List<Integer> getPoints() {
        List<Integer> points = players.getPoints();
        return points;
    }

    @Override
    public void addCardsToPlayer(PlayCard.State stateOfCardsToCollect, int amountOfCardsToCollect) {
        players.addCardsToPlayer(stateOfCardsToCollect, amountOfCardsToCollect);
    }

}
