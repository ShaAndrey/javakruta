package com.example.gogot.ui.activity;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gogot.model.entity.BoardCard;
import com.example.gogot.model.entity.InHandCard;
import com.example.gogot.model.entity.PlayCard;
import com.example.gogot.relation.GamePresenter;
import com.example.gogot.relation.MainContract;
import com.example.gogot.R;
import com.example.gogot.ui.custom.EndGameLayout;
import com.example.gogot.ui.custom.GameBoardLayout;
import com.example.gogot.ui.custom.RVAdapter;
import com.example.gogot.ui.dialog.MenuDialog;

import java.util.ArrayList;
import java.util.List;


public class GameActivity extends AppCompatActivity
        implements MainContract.View,
        GameBoardLayout.ActivityListener,
        RVAdapter.RVAdapterListener,
        MenuDialog.MenuDialogListener,
        EndGameLayout.EndGameLayoutListener {

    public static final String AMOUNT_OF_PLAYERS = "AMOUNT_OF_PLAYERS";
    public static final int DEFAULT_PLAYER_AMOUNT = 1;

    private ConstraintLayout gameActivityLayout;
    private GameBoardLayout gameBoard;
    private GamePresenter presenter;
    private MenuDialog gameMenu;

    private List<RecyclerView> playerHandLayouts;
    private List<RVAdapter> adapters = new ArrayList<>();
    private int amountOfPlayers;
    private boolean userInteractionBlocked;
    private int padding = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        amountOfPlayers = getIntent().getIntExtra(AMOUNT_OF_PLAYERS, DEFAULT_PLAYER_AMOUNT);

        presenter = new GamePresenter(this, amountOfPlayers);
        if (amountOfPlayers == 3) {
            setContentView(R.layout.game_3players_activity_layout);
            gameActivityLayout = findViewById(R.id.game_3_players_activity_layout);
        } else {
            setContentView(R.layout.game_activity_layout);
            gameActivityLayout = findViewById(R.id.game_activity_layout);
        }
        presenter.createView(amountOfPlayers);
        gameMenu = new MenuDialog(this);
        gameMenu.setListener(this);
        userInteractionBlocked = false;
    }

    @Override
    public void startTurn(BoardCard boardCard) {
        presenter.handleTurn(boardCard);
    }

    @Override
    public void drawPlayersHands(List<List<InHandCard>> playersCards) {
        playerHandLayouts = new ArrayList<>();
        if (amountOfPlayers < 3) {
            playerHandLayouts.add(findViewById(R.id.layout_player1_hand));
            playerHandLayouts.add(findViewById(R.id.layout_player2_hand));
        } else {
            playerHandLayouts.add(findViewById(R.id.layout_player1_hand));
            playerHandLayouts.add(findViewById(R.id.layout_player2_hand));
            playerHandLayouts.add(findViewById(R.id.layout_player3_hand));
        }
        for (int i = 0; i < playerHandLayouts.size(); i++) {
            RecyclerView playerHandLayout = playerHandLayouts.get(i);
            playerHandLayouts.get(i).setPadding(padding, padding, padding, padding);
            playerHandLayout.setHasFixedSize(true);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                    (amountOfPlayers == 3 && i > 0) ? 3 : 4) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            playerHandLayout.setLayoutManager(gridLayoutManager);
            RVAdapter adapter = new RVAdapter(playersCards.get(i));
            adapter.setListener(this);
            playerHandLayout.setAdapter(adapter);
            adapters.add(adapter);
        }
        updatePlayersIllumination(0);
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
    public void addCardsToPlayer(PlayCard.State stateOfCardsToAdd, int playerInd) {
        adapters.get(playerInd).addCardsAmount(stateOfCardsToAdd);
    }

    @Override
    public void updatePlayerPoints() {
        for (int i = 0; i < adapters.size(); i++) {
            adapters.get(i).updatePlayerPoints();
        }
    }

    @Override
    public void updatePlayersIllumination(int currentPlayer) {
        for (int i = 0; i < adapters.size(); i++) {
            adapters.get(i).updateIllumination();
            GradientDrawable border = new GradientDrawable();
            if ((currentPlayer + 1) % adapters.size() == i) {
                border.setStroke(padding, 0xFF0000FF);
            } else {
                border.setStroke(padding, 0x66000000);
            }
            playerHandLayouts.get(i).setBackground(border);
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
        Toast toast = Toast.makeText(getApplicationContext(),
                R.string.you_cant_move_there, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void stopGame() {
        userInteractionBlocked = true;
        EndGameLayout endGameLayout = findViewById(R.id.layout_end_game);
        endGameLayout.setListener(this);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(gameActivityLayout);

        int id = playerHandLayouts.get(0).getId();
        constraintSet.connect(id, ConstraintSet.TOP,
                R.id.horizontalGuidelinePlayers3, ConstraintSet.TOP);
        constraintSet.connect(id, ConstraintSet.END,
                ConstraintSet.PARENT_ID, ConstraintSet.END);

        if (amountOfPlayers == 3) {
            id = playerHandLayouts.get(1).getId();
            constraintSet.connect(id, ConstraintSet.BOTTOM,
                    R.id.horizontalGuidelinePlayers2, ConstraintSet.BOTTOM);

            id = playerHandLayouts.get(2).getId();
            constraintSet.connect(id, ConstraintSet.BOTTOM,
                    R.id.horizontalGuidelinePlayers2, ConstraintSet.BOTTOM);
        } else {
            id = playerHandLayouts.get(1).getId();
            constraintSet.connect(id, ConstraintSet.BOTTOM,
                    R.id.horizontalGuidelinePlayers2, ConstraintSet.BOTTOM);
            constraintSet.connect(id, ConstraintSet.START,
                    ConstraintSet.PARENT_ID, ConstraintSet.START);
        }

        id = R.id.layout_end_game;
        constraintSet.connect(id, ConstraintSet.TOP,
                R.id.horizontalGuidelinePlayers2, ConstraintSet.TOP);
        constraintSet.connect(id, ConstraintSet.BOTTOM,
                R.id.horizontalGuidelinePlayers3, ConstraintSet.BOTTOM);

        ChangeBounds transition = new ChangeBounds();
        transition.setInterpolator(new LinearInterpolator());
        int animationDuration = 1500;
        transition.setDuration(animationDuration);
        TransitionManager.beginDelayedTransition(gameActivityLayout, transition);
        constraintSet.applyTo(gameActivityLayout);

    }

    @Override
    public void updateIlluminationAndCollectCards() {
        presenter.updateIlluminationAndCollectCards();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (userInteractionBlocked && keyCode == KeyEvent.KEYCODE_BACK) {
            return super.onKeyDown(keyCode, event);
        }
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            gameMenu.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void openSettings() {
        // TODO
    }

    @Override
    public void exitGame() {
        exit();
    }

    void exit() {
        Intent intent = new Intent();
        intent.putExtra(StartGameActivity.NEED_TO_QUIT_TO_MAIN_MENU, true);
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    public void onNewGame() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onExit() {
        exit();
    }

    @Override
    public void onRestartGame() {
        restart();
    }

    @Override
    public void restartGame() {
        restart();
    }

    void restart() {
        Intent intent = new Intent();
        intent.putExtra(StartGameActivity.NEED_TO_RESTART_GAME, true);
        intent.putExtra(AMOUNT_OF_PLAYERS, amountOfPlayers);
        setResult(RESULT_OK, intent);
        finish();
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
}
