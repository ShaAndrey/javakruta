package com.example.gogot.model;

import java.util.List;


class Bot extends AbstractBot {

    Bot() {
        super();
    }

    @Override
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

    @Override
    void checkCell(BoardCard boardCard) {
        BoardCard currentCellToGo = new BoardCard(boardCard);
        List<Integer> playersPoints = players.getPlayersPoints();
        double currentDifference = -playersPoints.get(1) + playersPoints.get(0);  // TODO: add flexibility
        makeTurn(boardCard);
        playersPoints = players.getPlayersPoints();
        if (canEnsureDomination(boardCard.getState())) {
            currentDifference += boardCard.getState().ordinal();
        }
        currentDifference += playersPoints.get(1) - playersPoints.get(0);  // TODO: add flexibility
        if (maxDifference <= currentDifference) {
            maxDifference = currentDifference;
            cellToGo = currentCellToGo;
        }
    }

    @Override
    boolean canEnsureDomination(PlayCard.State state) {
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


}
