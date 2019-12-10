package com.example.gogot.model;

import com.example.gogot.model.entity.BoardCard;
import com.example.gogot.model.entity.PlayCard;

import java.util.List;

abstract class AbstractBot extends PlayersHand {
    GameModel gameModel;
    Board board;
    Players players;
    double maxDifference;
    BoardCard cellToGo;
    boolean[] dominationEnsured;

    AbstractBot() {
        super();
        setDominateState(PlayCard.State.PLAYER, true);
        dominationEnsured = new boolean[9];                         // fix required?
    }

    void setBoard() {
        board = gameModel.getBoard();
    }

    void setPlayers() {
        players = gameModel.getPlayers();
    }

    abstract BoardCard pickBestTurn();

    abstract void checkCell(BoardCard boardCard);

    abstract void calculateNextStep(BoardCard boardCard);

    boolean canEnsureDomination(PlayCard.State state) {
        List<Integer> playersAmountForState = players.getPlayersAmountForState(state);
        int sum = 0;
        for (int i = 0; i < playersAmountForState.size(); i++) {
            sum += playersAmountForState.get(i);
        }
        int stateInd = state.ordinal();
        if ((playersAmountForState.get(players.getPlayerIndex()) > stateInd / 2
                || (playersAmountForState.get(players.getPlayerIndex())
                == stateInd / 2 + stateInd % 2 &&
                sum == stateInd)) && !dominationEnsured[stateInd]) {
            return true;
        }
        return false;
    }

    void checkIfDominationIsEnsured(PlayCard.State state) {
        if (canEnsureDomination(state)) {
            dominationEnsured[state.ordinal()] = true;
        }
    }

    void makeTurn(BoardCard boardCard) {
        board.movePlayer(boardCard);
        players.addCardsToPlayer(board.getStateOfCardsToCollect(),
                board.getAmountOfCardsToCollect());
    }

    void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
        setBoard();
        setPlayers();
    }
}
