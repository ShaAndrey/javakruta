package com.example.gogot;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;



public class GameWindowActivity extends AppCompatActivity
        implements MainContract.View {
    BoardActivity gameBoard;
    GamePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new GamePresenter(this);
        setContentView(R.layout.activity_game_window);
        initializeBoard();
    }

    @Override
    public void initializeBoard() {
        gameBoard = new BoardActivity(this, presenter.getBoardSize());
    }

    @Override
    public void refreshBoard() {

    }

    @Override
    public void refreshPoints() {

    }

    @Override
    public void refreshCards() {

    }
}
