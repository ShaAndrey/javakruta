package com.example.gogot.ui;

import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gogot.model.BoardCard;
import com.example.gogot.model.PlayCard;
import com.example.gogot.relation.GamePresenter;
import com.example.gogot.relation.MainContract;
import com.example.gogot.R;

import java.util.ArrayList;
import java.util.List;


public class GameActivity extends AppCompatActivity
        implements MainContract.View, GameBoardLayout.ActivityListener,
        PlayerHandLayout.ActivityPlayerHandListener {

    GameBoardLayout gameBoard;
    GamePresenter presenter;
    List<PlayerHandLayout> playerHandLayouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        presenter = new GamePresenter(this);
        setContentView(R.layout.game_activity_layout);
        presenter.createView();
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
    public void startTurn(BoardCard boardCard) {
        presenter.handleTurn(boardCard);
    }

    @Override
    public void drawPlayersHands() {
        playerHandLayouts = new ArrayList<>();
        playerHandLayouts.add(findViewById(R.id.layout_player_hand));
        playerHandLayouts.get(0).setListener(this);
        playerHandLayouts.get(0).initHand();
    }

    @Override
    public void drawInitialBoard(BoardCard[][] boardCards, ArrayList<BoardCard> cardsToMove) {
        gameBoard = findViewById(R.id.layout_game_board);
        gameBoard.setListener(this);
        gameBoard.initBoard(6, boardCards, cardsToMove);         // TODO: not 6 here
    }

    @Override
    public void removeIllumination(BoardCard[][] boardCards) {
        gameBoard.removeIllumination(boardCards);
    }

    @Override
    public void movePlayer(BoardCard playerCard, Point newPlayerPosition) {
        gameBoard.movePlayer(playerCard, newPlayerPosition);
    }

    @Override
    public void refreshBoard(BoardCard[][] boardCards, ArrayList<BoardCard> cardsToMove) {
        gameBoard.refreshBoard(boardCards, cardsToMove);
    }

    @Override
    public void collectCards(ArrayList<BoardCard> cardsToCollect) {
        gameBoard.collectCards(cardsToCollect);
    }

    @Override
    public void youCantMoveThere() {

    }

    @Override
    public void stopGame() {

    }

    @Override
    public void updateIlluminationAndCollectCards() {
        presenter.updateIlluminationAndCollectCards();
    }

    @Override
    public int setImageToIndex(int index) {
        return setImageToCard(new PlayCard(index));
    }
}
