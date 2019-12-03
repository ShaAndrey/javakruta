package com.example.gogot.model;

import android.graphics.Point;

import com.example.gogot.model.entity.BoardCard;
import com.example.gogot.model.entity.InHandCard;
import com.example.gogot.model.entity.PlayCard;
import com.example.gogot.relation.MainContract;

import java.util.ArrayList;
import java.util.List;

public class GameModel implements MainContract.Model,
        Board.BoardListener, Players.PlayersListener {

    private Board board;
    private Players players;
    private int boardSize = 6;

    @Override
    public void nextPlayer() {
        players.nextPlayer();
    }

    public GameModel(int amountOfPlayers) {
        board = new Board(boardSize, boardSize);
        board.setBoardListener(this);
        players = new Players(amountOfPlayers);
        players.setPlayersListener(this);
    }


    GameModel(GameModel otherModel) {
        board = new Board(otherModel.board);
        board.setBoardListener(this);
        players = new Players(otherModel.players);
        players.setPlayersListener(this);
        boardSize = otherModel.boardSize;
    }

    @Override
    public ArrayList<BoardCard> handleTurn(BoardCard boardCard) {
        return board.handleTurn(boardCard);
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
    public boolean isPlayer() {
        return players.isPlayer();
    }

    @Override
    public BoardCard botPickPosition() {
        return players.botPickPosition();
    }

    @Override
    public List<List<InHandCard>> getPlayersCards() {
        return players.getPlayersCards();
    }

    @Override
    public List<Integer> getPlaces() {
        return players.getPlaces();
    }

    @Override
    public Players getPlayers() {
        return players;
    }

    @Override
    public List<Integer> getPoints() {
        return players.getPoints();
    }

    @Override
    public void addCardsToPlayer(PlayCard.State stateOfCardsToCollect, int amountOfCardsToCollect) {
        players.addCardsToPlayer(stateOfCardsToCollect, amountOfCardsToCollect);
    }

    @Override
    public GameModel getModel() {
        return this;
    }


}
