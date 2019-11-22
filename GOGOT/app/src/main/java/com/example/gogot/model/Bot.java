package com.example.gogot.model;

import java.util.List;


public class Bot extends PlayersHand {
    Board board;
    Players players;
    List<Integer> pointsDifferences;
    int maxDifference;
    BotListener botListener;
    BoardCard cellToGo;
    BoardCard currentCellToGo;

    Bot() {
        super();
        dominateStates[0] = true;
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
        makeTurn(boardCard);
        List<BoardCard> availableCells = board.getCellsAvailableToMove();
        availableCells.forEach(availableBoardCard -> {
            Board board = new Board(this.board);
            Players players = new Players(this.players);
            checkCellForEnemy(availableBoardCard);
            this.board = board;
            this.players = players;
        });
    }

    private void checkCellForEnemy(BoardCard boardCard) {
        pointsDifferences = players.getPlayersPoints();
        int currentDifference = -pointsDifferences.get(1) + pointsDifferences.get(0);  // TODO: add flexibility
        makeTurn(boardCard);
        pointsDifferences = players.getPlayersPoints();
        currentDifference += pointsDifferences.get(1) - pointsDifferences.get(0);  // TODO: add flexibility
        if (maxDifference <= currentDifference) {
            maxDifference = currentDifference;
            cellToGo = currentCellToGo;
        }
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

//    @Override
//    public void update(Board board) {
//        this.board = board;
//    }
}
