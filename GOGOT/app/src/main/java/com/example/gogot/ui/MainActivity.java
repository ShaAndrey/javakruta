package com.example.gogot.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.gogot.R;

import static com.example.gogot.ui.GameActivity.amountOfPlayers;


public class MainActivity extends AppCompatActivity implements GameActivity.GameActivityListener {


    public static final String AMOUNT_OF_PLAYERS = "amountOfPlayers";
    public static final int PICK_GAME = 0;
    public static final int START_GAME = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button newGameButton = findViewById(R.id.buttonNewGame);
        newGameButton.setOnClickListener(v -> onNewGame());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == PICK_GAME) {
            super.onActivityResult(requestCode, resultCode, data);
            amountOfPlayers = Integer.valueOf(data.getStringExtra(AMOUNT_OF_PLAYERS));
            Intent intent = new Intent(MainActivity.this,
                    GameActivity.class);
            startActivityForResult(intent, START_GAME);
        }
        if (resultCode == RESULT_OK && requestCode == START_GAME) {
            onNewGame();
        }
    }

    @Override
    public void onNewGame() {
        Intent intent = new Intent(MainActivity.this,
                StartGameActivity.class);
        startActivityForResult(intent, PICK_GAME);
    }

}
