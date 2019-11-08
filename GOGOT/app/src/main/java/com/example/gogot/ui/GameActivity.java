package com.example.gogot.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gogot.relation.GamePresenter;
import com.example.gogot.relation.MainContract;
import com.example.gogot.R;


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
        initializeBoard();
    }

    @Override
    public void initializeBoard() {
        gameBoard = findViewById(R.id.layout_game_board);
        gameBoard.initBoard(6);
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
