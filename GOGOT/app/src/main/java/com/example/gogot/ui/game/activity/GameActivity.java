package com.example.gogot.ui.game.activity;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gogot.model.game.Player;
import com.example.gogot.model.game.entity.BoardCard;
import com.example.gogot.model.game.entity.InHandCard;
import com.example.gogot.model.game.entity.PlayCard;
import com.example.gogot.relation.game.GamePresenter;
import com.example.gogot.relation.game.MainContract;
import com.example.gogot.R;
import com.example.gogot.ui.game.custom.GameBoardLayout;
import com.example.gogot.ui.game.custom.RVAdapterPlayerHand;
import com.example.gogot.ui.game.dialog.MenuDialog;
import com.example.gogot.ui.settings.activity.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.gogot.ui.game.activity.EndGameActivity.NEW_GAME;
import static com.example.gogot.ui.game.activity.EndGameActivity.RESTART_GAME;
import static com.example.gogot.ui.game.activity.EndGameActivity.TO_MAIN_MENU;
import static com.example.gogot.ui.game.activity.StartGameActivity.BOT_DIFFICULTY;


public class GameActivity extends AppCompatActivity
        implements MainContract.GameView,
        GameBoardLayout.ActivityListener,
        RVAdapterPlayerHand.RVAdapterListener,
        MenuDialog.MenuDialogListener {

    public static final String AMOUNT_OF_PLAYERS = "AMOUNT_OF_PLAYERS";
    public static final String ACTION_ON_END_GAME = "ACTION_ON_END_GAME";
    public static final String PLAYERS = "PLAYERS";
    public static final int DEFAULT_PLAYER_AMOUNT = 1;
    public static final int PADDING = 10;

    public static final int END_GAME = 1;

    private GameBoardLayout gameBoard;
    private GamePresenter presenter;
    private MenuDialog gameMenu;

    private List<RecyclerView> playerHandLayouts;
    private List<RVAdapterPlayerHand> adapters;
    private int amountOfPlayers;
    private StartGameActivity.BotDifficulty botDifficulty;
    private boolean userInteractionBlocked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        amountOfPlayers = getIntent().getIntExtra(AMOUNT_OF_PLAYERS, DEFAULT_PLAYER_AMOUNT);
        botDifficulty = (StartGameActivity.BotDifficulty)
                getIntent().getSerializableExtra(BOT_DIFFICULTY);
        presenter = new GamePresenter(this, amountOfPlayers, botDifficulty);

        setContentView(R.layout.game_activity_layout);

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
    public void initializePlayerHands() {
        playerHandLayouts = new ArrayList<>();
        if (amountOfPlayers < 3) {
            playerHandLayouts.add(findViewById(R.id.layout_player1_hand));
            playerHandLayouts.add(findViewById(R.id.layout_player2_hand));
        } else {
            playerHandLayouts.add(findViewById(R.id.layout_player1_hand));
            playerHandLayouts.add(findViewById(R.id.layout_player2_hand));
            playerHandLayouts.add(findViewById(R.id.layout_player3_hand));
        }
    }

    @Override
    public void drawPlayersHands(List<List<InHandCard>> playersCards) {
        adapters = new ArrayList<>();
        for (int i = 0; i < playerHandLayouts.size(); i++) {
            RecyclerView playerHandLayout = playerHandLayouts.get(i);
            playerHandLayouts.get(i).setPadding
                    (PADDING, PADDING, PADDING, PADDING);
            playerHandLayout.setHasFixedSize(true);
            GridLayoutManager gridLayoutManager =
                    new GridLayoutManager(this, 4) {
                        @Override
                        public boolean canScrollVertically() {
                            return false;
                        }
                    };
            playerHandLayout.setLayoutManager(gridLayoutManager);
            RVAdapterPlayerHand adapter = new RVAdapterPlayerHand
                    (playersCards.get(i), setPicIdToPlayer(i));
            adapter.setListener(this);
            playerHandLayout.setAdapter(adapter);
            adapters.add(adapter);
        }
        updatePlayersIllumination(amountOfPlayers - 1);
    }

    private int setPicIdToPlayer(int i) {
        switch (i) {
            case 0:
                return R.drawable.player_girl_frame;
            case 1:
                return R.drawable.player_mustashe_frame;
            case 2:
                return R.drawable.player_beard_frame;
            default:
                return R.drawable.star;
        }
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
                border.setStroke(PADDING, 0xFF0000FF);
            } else {
                border.setStroke(PADDING, 0x66000000);
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
    public void stopGame(ArrayList<Player> players) {
        Intent intent = new Intent(GameActivity.this,
                EndGameActivity.class);
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setPictureId(setPicIdToPlayer(i));
        }
        intent.putParcelableArrayListExtra(PLAYERS, players);
        startActivityForResult(intent, END_GAME);
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
        Intent intent = new Intent(GameActivity.this,
                SettingsActivity.class);
        startActivity(intent);
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

    public void onNewGame() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void restartGame() {
        restart();
    }

    void restart() {
        Intent intent = new Intent();
        intent.putExtra(StartGameActivity.NEED_TO_RESTART_GAME, true);
        intent.putExtra(AMOUNT_OF_PLAYERS, amountOfPlayers);
        intent.putExtra(BOT_DIFFICULTY, botDifficulty);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == END_GAME && data != null) {
            switch (data.getStringExtra(ACTION_ON_END_GAME)) {
                case NEW_GAME:
                    onNewGame();
                    break;
                case TO_MAIN_MENU:
                    exit();
                    break;
                case RESTART_GAME:
                    restart();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public int setImageToCard(PlayCard card) {
        switch (card.getState()) {
            case NOTHING:
                return R.drawable.star;
            case PLAYER:
                return R.drawable.hero_frame2;
            case DRAGON:
                return R.drawable.giant_frame;
            case OGRE:
                return R.drawable.ghost_frame;
            case MINOTAUR:
                return R.drawable.dog_frame;
            case ELF:
                return R.drawable.wizard_frame;
            case FAIRY:
                return R.drawable.skeleton_frame;
            case GNOME:
                return R.drawable.warrior_frame;
            case GOBLIN:
                return R.drawable.goblin_frame2;
        }
        return 0;
    }
}
