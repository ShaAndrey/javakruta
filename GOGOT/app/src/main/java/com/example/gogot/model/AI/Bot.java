package com.example.gogot.model.AI;

import com.example.gogot.model.AI.AbstractBot;
import com.example.gogot.model.entity.BoardCard;
import com.example.gogot.model.entity.PlayCard;

import java.util.List;

public class Bot extends AbstractBot {

    public Bot() {
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
        if(cellToGo.getState().equals(PlayCard.State.NOTHING)) {
            throw new RuntimeException("pickBestTurn");
        }
        return cellToGo;
    }

    @Override
    void checkCell(BoardCard boardCard) {
        calculateNextStep(boardCard);
    }

    @Override
    void calculateNextStep(BoardCard boardCard) {
        BoardCard currentCellToGo = new BoardCard(boardCard);
        List<Integer> playersPoints = players.getPlayersPoints();
        double currentDifference = -playersPoints.get(1) + playersPoints.get((1 + 1) % playersPoints.size());
        makeTurn(boardCard);
        playersPoints = players.getPlayersPoints();
        if (canEnsureDomination(boardCard.getState())) {
            currentDifference += boardCard.getState().ordinal();
        }
        currentDifference += playersPoints.get(1) - playersPoints.get((1 + 1) % playersPoints.size());
        if (maxDifference <= currentDifference) {
            maxDifference = currentDifference;
            cellToGo = currentCellToGo;
        }
    }

}
