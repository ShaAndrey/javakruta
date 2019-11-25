package com.example.gogot.model;

import java.util.List;


public class Bot extends PlayersHand {
    Board board;
    Players players;
    double maxDifference;
    BotListener botListener;
    BoardCard cellToGo;
    BoardCard currentCellToGo;
    boolean[] dominationEnsured;

    Bot() {
        super();
        dominateStates[0] = true;
        dominationEnsured = new boolean[9];
    }

    private void setBoard() {
        board = new Board(botListener.getBoard());
    }

    private void setPlayers() {
        players = new Players(botListener.getPlayers());
    }

    BoardCard pickBestTurn() {
        setBoard();
        setPlayers();
        List<BoardCard> availableCells = board.getCellsAvailableToMove();
        if (availableCells.isEmpty()) {
            return null;
        }
        cellToGo = new BoardCard(availableCells.get(0));
        maxDifference = -100;
        availableCells.forEach(boardCard -> {
            Board board = new Board(this.board);
            Players players = new Players(this.players);
            checkCell(boardCard);
            this.board = board;
            this.players = players;
        });
        return cellToGo;
    }

    private void checkCell(BoardCard boardCard) {
        currentCellToGo = new BoardCard(boardCard);
        List<Integer> pointsDifferences = players.getPlayersPoints();
        double currentDifference = -pointsDifferences.get(1) + pointsDifferences.get(0);  // TODO: add flexibility
        makeTurn(boardCard);
        pointsDifferences = players.getPlayersPoints();
        if (canInsureDomination(boardCard.getState())) {
            currentDifference += boardCard.getState().ordinal();
        }
        currentDifference += pointsDifferences.get(1) - pointsDifferences.get(0);  // TODO: add flexibility
        if (maxDifference <= currentDifference) {
            maxDifference = currentDifference;
            cellToGo = currentCellToGo;
        }
    }

    boolean canInsureDomination(PlayCard.State state) {
        List<Integer> playersAmountForState = players.getPlayersAmountForState(state);
        int sum = 0;
        for (int i = 0; i < playersAmountForState.size(); i++) {
            sum += playersAmountForState.get(i);
        }
        int stateInd = state.ordinal();
        if (playersAmountForState.get(0) > stateInd / 2 ||
                (playersAmountForState.get(0) >= (stateInd + 1) / 2 &&
                        sum == stateInd) && !dominationEnsured[stateInd]) {
            dominationEnsured[stateInd] = true;
            return true;
        }
        return false;
    }

    private void makeTurn(BoardCard boardCard) {
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
