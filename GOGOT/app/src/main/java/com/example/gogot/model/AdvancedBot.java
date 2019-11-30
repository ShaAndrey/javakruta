package com.example.gogot.model;

import java.util.List;

public class AdvancedBot extends AbstractBot {

    private int amountOfCalculatedSteps = 2;
    private double currentDifference = 0.0;

    AdvancedBot() {
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
        currentDifference = 0.0;

        if (amountOfCalculatedSteps == 2) {
            CalculateNextStep(currentCellToGo, 1);
            --amountOfCalculatedSteps;
            double savedCurrentDifference = currentDifference;
            players.SwapTwoPlayers();
            BoardCard nextBestTurn = pickBestTurn();
            currentDifference = savedCurrentDifference;
            CalculateNextStep(nextBestTurn, 0);
            players.SwapTwoPlayers();
            amountOfCalculatedSteps = 2;
        } else if (amountOfCalculatedSteps == 1) {
            CalculateNextStep(currentCellToGo, 1);
        }

        if (maxDifference <= currentDifference) {
            maxDifference = currentDifference;
            cellToGo = currentCellToGo;
        }
    }

    @Override
    void CalculateNextStep(BoardCard boardCard, int playerIndex) {
        List<Integer> playersPoints = players.getPlayersPoints();
        currentDifference += -playersPoints.get(playerIndex) + playersPoints.get((playerIndex + 1) % playersPoints.size());
        makeTurn(boardCard);
        playersPoints = players.getPlayersPoints();
        if (canEnsureDomination(boardCard.getState())) {
            currentDifference += boardCard.getState().ordinal() / 2;
        }
        currentDifference += playersPoints.get(playerIndex) - playersPoints.get((playerIndex + 1) % playersPoints.size());
    }
}
