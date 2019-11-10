package com.example.gogot.ui;

import android.graphics.Point;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gogot.model.BoardCard;
import com.example.gogot.model.PlayCard;
import com.example.gogot.relation.GamePresenter;
import com.example.gogot.relation.MainContract;
import com.example.gogot.R;

import java.util.ArrayList;


public class GameActivity extends AppCompatActivity
        implements MainContract.View, GameBoardLayout.ActivityListener {

    GameBoardLayout gameBoard;
    GamePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        presenter = new GamePresenter(this);
        setContentView(R.layout.game_activity_layout);
        presenter.drawInitialBoard();
    }

    @Override
    public int setImageToCard(PlayCard card) {
        switch (card.getState()) {
            // TODO in each case setting the appropriate picture
            case NOTHING:
                return R.drawable.star;
            case PLAYER:
                return R.drawable.player;
            case DRAGON:
                return R.drawable.dragon;
            case OGRE:
                return R.drawable.ogre;
            case MINOTAUR:
                return R.drawable.minotaur;
            case ELF:
                return R.drawable.thom;
            case FAIRY:
                return R.drawable.fairy;
            case GNOME:
                return R.drawable.gnome;
            case GOBLIN:
                return R.drawable.goblin;
        }
        return 0;
    }

    @Override
    public void drawInitialBoard(BoardCard[][] boardCards) {
        gameBoard = findViewById(R.id.layout_game_board);
        gameBoard.setListener(this);
        gameBoard.initBoard(6, boardCards);         // TODO: not 6 here
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