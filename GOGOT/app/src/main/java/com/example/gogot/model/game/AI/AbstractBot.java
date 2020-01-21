package com.example.gogot.model.game.AI;

import com.example.gogot.model.game.Board;
import com.example.gogot.model.game.GameModel;
import com.example.gogot.model.game.Players;
import com.example.gogot.model.game.PlayersHand;
import com.example.gogot.model.game.entity.BoardCard;
import com.example.gogot.model.game.entity.PlayCard;

import java.util.List;

abstract public class AbstractBot extends PlayersHand {
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

    public abstract BoardCard pickBestTurn();

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

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
        setBoard();
        setPlayers();
    }
}
