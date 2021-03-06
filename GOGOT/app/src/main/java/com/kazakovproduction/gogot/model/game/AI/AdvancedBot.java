package com.kazakovproduction.gogot.model.game.AI;

import com.kazakovproduction.gogot.model.game.entity.BoardCard;

import java.util.List;

public class AdvancedBot extends AbstractBot {

    private int amountOfCalculatedSteps = 2;
    private double currentDifference = 0.0;

    public AdvancedBot() {
        super();
    }

    @Override
    public BoardCard pickBestTurn() {
        List<BoardCard> availableCells = board.getCellsAvailableToMove();
        if (availableCells.isEmpty()) {
            return null;
        }
        cellToGo = new BoardCard(availableCells.get(0));
        maxDifference = -100;
        availableCells.forEach(boardCard -> {
            gameModel.getSnapShots().addSnapShot();
            checkCell(boardCard);
            gameModel.getSnapShots().undo();
        });
        if (!players.isPlayer()) {
            checkIfDominationIsEnsured(cellToGo.getState());
        }
        return cellToGo;
    }

    @Override
    void checkCell(BoardCard boardCard) {
        BoardCard currentCellToGo = new BoardCard(boardCard);
        currentDifference = 0;
        if (amountOfCalculatedSteps == 2) {
            calculateNextStep(currentCellToGo);
            --amountOfCalculatedSteps;

            double savedCurrentDifference = currentDifference;
            BoardCard savedCellToGo = cellToGo;
            double savedMaxDifference = maxDifference;

            players.swapTwoPlayers();
            BoardCard nextBestTurn = pickBestTurn();
            currentDifference = 0;
            if(nextBestTurn != null) {
                calculateNextStep(nextBestTurn);
            }

            currentDifference = savedCurrentDifference - currentDifference;
            cellToGo = savedCellToGo;
            maxDifference = savedMaxDifference;

            players.swapTwoPlayers();
            amountOfCalculatedSteps = 2;
        } else if (amountOfCalculatedSteps == 1) {
            calculateNextStep(currentCellToGo);
        }
        if (maxDifference < currentDifference) {
            maxDifference = currentDifference;
            cellToGo = currentCellToGo;
        }
    }

    @Override
    void calculateNextStep(BoardCard boardCard) {
        List<Integer> playersPoints = players.getPlayersPoints();
        currentDifference += -playersPoints.get(1) + playersPoints.get(0);
        makeTurn(boardCard);
        playersPoints = players.getPlayersPoints();
        if (canEnsureDomination(boardCard.getState())) {
            currentDifference += boardCard.getState().ordinal();
        } else if (dominationEnsured[boardCard.getState().ordinal()]) {
            currentDifference -= 2;
        }
        currentDifference += playersPoints.get(1) - playersPoints.get(0);
    }
}
