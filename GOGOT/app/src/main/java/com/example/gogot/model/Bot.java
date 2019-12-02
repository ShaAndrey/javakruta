package com.example.gogot.model;

import com.example.gogot.model.entity.BoardCard;

import java.util.List;

class Bot extends AbstractBot {

    Bot() {
        super();
    }

    @Override
    BoardCard pickBestTurn() {
        setGameModel();
        List<BoardCard> availableCells = board.getCellsAvailableToMove();
        if (availableCells.isEmpty()) {
            return null;
        }
        cellToGo = new BoardCard(availableCells.get(0));
        maxDifference = -100;
        availableCells.forEach(boardCard -> {
            GameModel model = new GameModel(gameModel);
            checkCell(boardCard);
            gameModel = model;
        });
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
