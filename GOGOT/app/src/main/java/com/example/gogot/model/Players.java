package com.example.gogot.model;

import java.util.ArrayList;
import java.util.List;

public class Players implements PlayersHand.PlayerListener, Bot.BotListener {
    private int amountOfPlayers;
    private int currentPlayer = 0;
    private ArrayList<PlayersHand> playersHands;
    private PlayersListener playersListener;

    Players(Players otherPlayers) {
        amountOfPlayers = otherPlayers.amountOfPlayers;
        currentPlayer = otherPlayers.currentPlayer;
        playersHands = new ArrayList<>();
        for (int i = 0; i < amountOfPlayers; i++) {
            playersHands.add(new PlayersHand(otherPlayers.playersHands.get(i)));
            playersHands.get(i).setPlayerListener(this);
        }
    }

    Players(int amountOfPlayers) {
        this.amountOfPlayers = amountOfPlayers;
        playersHands = new ArrayList<>();
        for (int i = 0; i < amountOfPlayers; i++) {
            playersHands.add(new PlayersHand());
            playersHands.get(i).setPlayerListener(this);
        }
        if (amountOfPlayers == 1) {
            ++this.amountOfPlayers;
            playersHands.add(new Bot());
            playersHands.get(1).setPlayerListener(this);
            ((Bot) playersHands.get(1)).setBotListener(this);
        }
    }

    void addCardsToPlayer(PlayCard.State stateOfCardsToCollect, int amountOfCardsToCollect) {
        playersHands.get(currentPlayer).
                addNCardsToHand(stateOfCardsToCollect, amountOfCardsToCollect);
    }

    int getPlayerIndex() {
        return currentPlayer;
    }

    void nextPlayer() {
        currentPlayer = (currentPlayer + 1) % amountOfPlayers;
    }

    @Override
    public void checkIfPlayerDominatesState(PlayCard.State state) {
        PlayersHand maxHand = new PlayersHand();
        maxHand.initState(state);
        for (int i = 0; i < playersHands.size(); i++) {
            if (playersHands.get(i).getAmountForState(state) >
                    maxHand.getAmountForState(state)) {
                maxHand = playersHands.get(i);
            }
        }
        if (playersHands.get(currentPlayer).getAmountForState(state) >=
                maxHand.getAmountForState(state)) {
            maxHand = playersHands.get(currentPlayer);
        }
        if (!maxHand.checkDominateState(state.ordinal())) {
            int points = (state.ordinal() - state.ordinal() % 2) / 2 + 1;
            playersHands.forEach(playersHand -> {
                if (playersHand.checkDominateState(state.ordinal())) {
                    playersHand.setDominateState(state.ordinal(), false);
                    playersHand.addPoints(-points);
                }
            });
            maxHand.addPoints(points);
            maxHand.setDominateState(state.ordinal(), true);
        }
    }

    List<Integer> getPoints() {
        List<Integer> points = new ArrayList<>();
        playersHands.forEach(playersHand -> points.add(playersHand.getPoints()));
        return points;
    }

    List<boolean[]> getPlayersDominateStates() {
        List<boolean[]> playersDominateStates = new ArrayList<>();
        playersHands.forEach(playersHand ->
                playersDominateStates.add(playersHand.getDominateStates()));
        return playersDominateStates;
    }

    boolean isPlayer() {
        return !playersHands.get(currentPlayer).checkDominateState(0);
    }

    BoardCard botPickPosition() {
        if (playersHands.get(currentPlayer) instanceof Bot) {
            return ((Bot) playersHands.get(currentPlayer)).pickBestTurn();
        }
        return null;
    }

    @Override
    public Board getBoard() {
        return playersListener.getGameBoard();
    }

    ArrayList<Integer> getPlayersPoints() {
        ArrayList<Integer> points = new ArrayList<>();
        playersHands.forEach(playersHand -> points.add(playersHand.getPoints()));
        return points;
    }

    ArrayList<Integer> getPlayersAmountForState(PlayCard.State state) {
        ArrayList<Integer> points = new ArrayList<>();
        playersHands.forEach(playersHand -> points.add(playersHand.getAmountForState(state)));
        return points;
    }

    @Override
    public Players getPlayers() {
        return this;
    }


    void setPlayersListener(GameModel model) {
        playersListener = model;
    }

    interface PlayersListener {
        Board getGameBoard();
    }
}
