package com.example.gogot.model;

abstract class AbstractBot extends PlayersHand {
    Board board;
    Players players;
    double maxDifference;
    BotListener botListener;
    BoardCard cellToGo;
    boolean[] dominationEnsured;

    AbstractBot() {
        super();
        dominateStates[0] = true;
        dominationEnsured = new boolean[9];
    }

    void setBoard() { board = new Board(botListener.getBoard()); }

    void setPlayers() {
        players = new Players(botListener.getPlayers());
    }

    abstract BoardCard pickBestTurn();

    abstract void checkCell(BoardCard boardCard);

    abstract boolean canEnsureDomination(PlayCard.State state);

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
