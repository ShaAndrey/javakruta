package com.example.gogot.model;

import java.util.List;

abstract class AbstractBot extends PlayersHand {
    Board board;
    Players players;
    double maxDifference;
    private BotListener botListener;
    BoardCard cellToGo;
    boolean[] dominationEnsured;

    AbstractBot() {
        super();
        dominateStates[0] = true;
        dominationEnsured = new boolean[9];
    }

    void setBoard() {
        board = new Board(botListener.getBoard());
    }

    void setPlayers() {
        players = new Players(botListener.getPlayers());
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
        if ((playersAmountForState.get(players.getPlayerIndex()) > stateInd / 2 || (playersAmountForState.get(players.getPlayerIndex()) == stateInd / 2 + stateInd % 2 && sum == stateInd)) && !dominationEnsured[stateInd]) {
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

    interface BotListener {
        Board getBoard();

        Players getPlayers();
    }

    void setBotListener(Players players) {
        botListener = players;
    }
}
