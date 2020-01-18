package com.example.gogot.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gogot.R;
import com.example.gogot.ui.dialog.BotDifficultiesDialog;

import static com.example.gogot.ui.activity.GameActivity.*;

public class StartGameActivity extends AppCompatActivity {

    public static final int GAME_ACTIVITY_RESULT = 1001;
    public static final String NEED_TO_RESTART_GAME = "NEED_TO_RESTART";
    public static final String NEED_TO_QUIT_TO_MAIN_MENU = "NEED_TO_QUIT_TO_MAIN_MENU";
    public static final String BOT_DIFFICULTY = "BOT_DIFFICULTY";
    public enum BotDifficulties {
        PLAYER,
        EASY,
        HARD
    }
    BotDifficultiesDialog botDifficultiesDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        Button getBackButton = findViewById(R.id.backButton);
        getBackButton.setOnClickListener(v -> {
            finish();
        });
        botDifficultiesDialog = new BotDifficultiesDialog(this);
    }

    private void openGameActivity(int amountOfPlayers) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(AMOUNT_OF_PLAYERS, amountOfPlayers);
//        intent.putExtra(BOT_DIFFICULTY, amountOfPlayers);
        startActivityForResult(intent, GAME_ACTIVITY_RESULT);
    }

    public void onStartGameClick(View view) {
        openGameActivity(getAmountOfPlayers(view));
    }



    private int getAmountOfPlayers(View view) {
        switch (view.getId()) {
            case R.id.vsAIButton:
                return 1;
            case R.id.oneVSOneButton:
                return 2;
            case R.id.threePlayersButton:
                return 3;
        }
        return 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == GAME_ACTIVITY_RESULT && data != null) {
            if (data.getBooleanExtra(NEED_TO_RESTART_GAME, false)) {
                int playerAmount = data.getIntExtra(AMOUNT_OF_PLAYERS, DEFAULT_PLAYER_AMOUNT);
                openGameActivity(playerAmount);
            } else if (data.getBooleanExtra(NEED_TO_QUIT_TO_MAIN_MENU, false)) {
                finish();
            }
        }
    }

    public void onСhooseDifficultyClick(View view) {
        openGameActivity(getAmountOfPlayers(view));
    }
}
