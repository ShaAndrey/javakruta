package com.example.gogot.model;

import com.example.gogot.model.AI.AbstractBot;
import com.example.gogot.model.AI.Bot;
import com.example.gogot.model.entity.BoardCard;
import com.example.gogot.model.entity.InHandCard;
import com.example.gogot.model.entity.PlayCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Players implements PlayersHand.PlayerListener {

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
        }
    }

    public void addCardsToPlayer(PlayCard.State stateOfCardsToCollect, int amountOfCardsToCollect) {
        playersHands.get(currentPlayer).
                addNCardsToHand(stateOfCardsToCollect, amountOfCardsToCollect);
    }

    public int getPlayerIndex() {
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

    public boolean isPlayer() {
        return !playersHands.get(currentPlayer).checkDominateState(PlayCard.State.PLAYER);
    }

    BoardCard botPickPosition() {
        if (playersHands.get(currentPlayer) instanceof AbstractBot) {
            ((AbstractBot) playersHands.get(currentPlayer)).setGameModel(playersListener.getModel());
            BoardCard a =((AbstractBot) playersHands.get(currentPlayer)).pickBestTurn();
            if(a.getState().equals(PlayCard.State.NOTHING)) {
                throw new RuntimeException("botPickPosition");
            }
            return a;
        }
        return null;
    }

    public ArrayList<Integer> getPlayersPoints() {
        ArrayList<Integer> points = new ArrayList<>();
        playersHands.forEach(playersHand -> points.add(playersHand.getPoints()));
        return points;
    }

    public ArrayList<Integer> getPlayersAmountForState(PlayCard.State state) {
        ArrayList<Integer> points = new ArrayList<>();
        playersHands.forEach(playersHand -> points.add(playersHand.getAmountForState(state)));
        return points;
    }

    public void swapTwoPlayers() {
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
        GameModel getModel();
    }

    List<List<InHandCard>> getPlayersCards() {
        List<List<InHandCard>> playersCards = new ArrayList<>();
        playersHands.forEach(playersHand ->
                playersCards.add(playersHand.getInHandCards()));
        return playersCards;
    }

    List<List<InHandCard>> getPlayersCardsCopy() {
        List<List<InHandCard>> playersCards = new ArrayList<>();
        playersHands.forEach(playersHand ->
                playersCards.add(new ArrayList<>(playersHand.getInHandCards())));
        return playersCards;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setPlayersHandCards(List<List<InHandCard>> playersHandsCards) {
        for (int i = 0; i < playersHands.size(); i++) {
            playersHands.get(i).setInHandCards(playersHandsCards.get(i));
        }
    }

    PlayersSnapShot createSnapShot() {
        return new PlayersSnapShot();
    }

    class PlayersSnapShot {
        private Players players;
        private List<List<InHandCard>> playersCards;
        private int currentPlayer;

        PlayersSnapShot() {
            this.players = Players.this;
            this.playersCards = new ArrayList<>();
            playersHands.forEach(playersHand ->
                    this.playersCards.add(new ArrayList<>(playersHand.getInHandCardsCopy())));
            this.currentPlayer = Players.this.currentPlayer;
        }

        void restore() {
            players.setPlayersHandCards(this.playersCards);
            players.setCurrentPlayer(this.currentPlayer);
        }
    }

}
