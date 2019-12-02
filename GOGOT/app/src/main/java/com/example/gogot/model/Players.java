package com.example.gogot.model;

import com.example.gogot.model.entity.BoardCard;
import com.example.gogot.model.entity.InHandCard;
import com.example.gogot.model.entity.PlayCard;

import java.util.ArrayList;
import java.util.Collections;
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
            playersHands.add(new AdvancedBot());
            playersHands.get(1).setPlayerListener(this);
            ((AdvancedBot) playersHands.get(1)).setBotListener(this);
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
        if (!maxHand.checkDominateState(state)) {
            int points = (state.ordinal() - state.ordinal() % 2) / 2 + 1;
            playersHands.forEach(playersHand -> {
                if (playersHand.checkDominateState(state)) {
                    playersHand.setDominateState(state, false);
                    playersHand.addPoints(-points);
                }
            });
            maxHand.addPoints(points);
            maxHand.setDominateState(state, true);
        }
    }

    List<Integer> getPoints() {
        List<Integer> points = new ArrayList<>();
        playersHands.forEach(playersHand -> points.add(playersHand.getPoints()));
        return points;
    }

    boolean isPlayer() {
        return !playersHands.get(currentPlayer).checkDominateState(PlayCard.State.PLAYER);
    }

    BoardCard botPickPosition() {
        if (playersHands.get(currentPlayer) instanceof AdvancedBot) {
            return ((AdvancedBot) playersHands.get(currentPlayer)).pickBestTurn();
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

    void swapTwoPlayers() {
        PlayersHand current = playersHands.get(currentPlayer);
        playersHands.set(currentPlayer, playersHands.get((currentPlayer + 1) % playersHands.size()));
        playersHands.set((currentPlayer + 1) % playersHands.size(), current);
    }


    void setPlayersListener(GameModel model) {
        playersListener = model;
    }

    public List<Integer> getPlaces() {
        List<Integer> points = new ArrayList<>(getPoints());
        List<Integer> places = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            places.add(i);
        }
        for (int i = 0; i < points.size() - 1; ++i) {
            for (int j = 0; j < points.size() - i - 1; ++j) {
                if (points.get(j) < points.get(j + 1)) {
                    Collections.swap(points, j, j + 1);
                    Collections.swap(places, j, j + 1);
                }
            }
        }
        return places;
    }

    interface PlayersListener {
        Board getGameBoard();
    }

    List<List<InHandCard>> getPlayersCards() {
        List<List<InHandCard>> playersCards = new ArrayList<>();
        playersHands.forEach(playersHand ->
                playersCards.add(new ArrayList<>(playersHand.getInHandCards())));
        return playersCards;
    }
}
