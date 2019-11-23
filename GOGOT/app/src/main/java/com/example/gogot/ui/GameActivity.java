package com.example.gogot.ui;

import android.graphics.Point;
import android.os.Bundle;
import android.view.KeyEvent;
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
        PlayerHandLayout.ActivityPlayerHandListener, MenuDialog.MenuDialogListener {
    GameBoardLayout gameBoard;
    GamePresenter presenter;
    PlayerHandLayout handLayout;
    public static int amountOfPlayers;                             // is it OK?
    MenuDialog gameMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        presenter = new GamePresenter(this, amountOfPlayers);
        if (amountOfPlayers == 3) {
            setContentView(R.layout.game_3players_activity_layout);
        } else {
            setContentView(R.layout.game_activity_layout);
        }
        presenter.createView(amountOfPlayers);
        gameMenu = new MenuDialog(this);
        gameMenu.setListener(this);
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
        if (amountOfPlayers < 3) {
            PlayersLayout playersLayout = new PlayersLayout(this);
            playerHandLayouts.add(findViewById(R.id.layout_player1_hand));
            playerHandLayouts.add(findViewById(R.id.layout_player2_hand));
        } else {
            playerHandLayouts.add(findViewById(R.id.layout_player1_hand));
            playerHandLayouts.add(findViewById(R.id.layout_player2_hand));
            playerHandLayouts.add(findViewById(R.id.layout_player3_hand));
        }
        playerHandLayouts.forEach(playerHandLayout -> {
            playerHandLayout.setListener(this);
            playerHandLayout.initHand();
        });
    }

    @Override
    public void drawInitialBoard(BoardCard[][] boardCards, ArrayList<PlayCard> cardsToMove) {
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
    public void refreshBoard(BoardCard[][] boardCards, ArrayList<PlayCard> cardsToMove) {
        gameBoard.refreshBoard(boardCards, cardsToMove);
    }

    @Override
    public void addCardsToPlayer(PlayCard.State stateOfCardsToAdd,
                                 int amountOfCardsToAdd, int playerInd) {
        playerHandLayouts.get(playerInd).addCardsAmount(stateOfCardsToAdd, amountOfCardsToAdd);
    }

    @Override
    public void updatePlayerPoints(List<Integer> points) {
        for (int i = 0; i < playerHandLayouts.size(); i++) {
            playerHandLayouts.get(i).updatePlayerPoints(points.get(i));
        }
    }

    @Override
    public void updatePlayersIllumination(List<boolean[]> playersDominateStates,
                                          int currentPlayer) {
        for (int i = 0; i < playerHandLayouts.size(); i++) {
            if ((currentPlayer + 1) % playerHandLayouts.size() == i) {
                playerHandLayouts.get(i).updateIllumination
                        (playersDominateStates.get(i), true);
            } else {
                playerHandLayouts.get(i).updateIllumination
                        (playersDominateStates.get(i), false);
            }
        }
    }

    @Override
    public void invalidateBoardCellsListeners() {
        gameBoard.invalidateBoardCellsListeners();
    }

    @Override
    public void revalidateBoardCellsListeners(BoardCard[][] boardCards) {
        gameBoard.revalidateBoardCellsListeners(boardCards);
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
        if (amountOfPlayers == 3) {
            setContentView(R.layout.game_3players_activity_layout);
        } else {
//            setContentView(R.layout.activity_end_game);
//            ConstraintSet constraintSet = new ConstraintSet();
//            constraintSet.clone(this);

//            playerHandLayouts.set(0, findViewById(R.id.layout_player1_end_game_hand));
//            playerHandLayouts.set(1, findViewById(R.id.layout_player2_end_game_hand));
        }

    }

    @Override
    public void updateIlluminationAndCollectCards() {
        presenter.updateIlluminationAndCollectCards();
    }

    @Override
    public int setImageToIndex(int index) {
        return setImageToCard(new PlayCard(index));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            gameMenu.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void openSettings() {

    }

    @Override
    public void exitGame() {
        onBackPressed();
    }

}
