package com.example.gogot;

import android.graphics.Picture;
import android.graphics.Point;

public interface MainContract {
    interface View {
        void refreshBoard();
        void refreshPoints();
        void refreshCards();
    }

    interface Presenter {
        void handleTurn(Point newPlayerPosition);

        void getCards();
        void getPoints(); // будет не void, просто пока непонятно как мы будем хранить счет и карты
        Picture getBoardCellPicture(Point cellPosition);
    }

    interface Model {
        void handleTurn(Point newPlayerPosition);
        // в реализации этого метода в классе model будет вызываться movePlayer();

        Point getPlayerPosition();
        BoardCard.State getBoardCellState();
        Board getGameBoard();
        // методы, через которые презентер и вьюшка могут получать конкретную информацию о доске

        void getPoints();
        void getCards();
    }
}
