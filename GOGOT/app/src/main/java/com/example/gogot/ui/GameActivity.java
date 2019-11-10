package com.example.gogot.ui;

import android.graphics.Point;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gogot.relation.GamePresenter;
import com.example.gogot.relation.MainContract;
import com.example.gogot.R;

import java.util.ArrayList;


public class GameActivity extends AppCompatActivity
        implements MainContract.View {

    GameBoardLayout gameBoard;
    GamePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        presenter = new GamePresenter(this);
        setContentView(R.layout.game_activity_layout);
        drawInitialBoard();
    }

    @Override
    public void drawInitialBoard() {
        gameBoard = findViewById(R.id.layout_game_board);
        gameBoard.initBoard(6);
    }

    @Override
    public void movePlayer(Point newPlayerPosition) {
        
    }

    @Override
    public void collectCards(ArrayList<Point> cardsToCollect) {

    }

    @Override
    public void youCantMoveThere() {

    }

    @Override
    public void stopGame() {

    }
}
