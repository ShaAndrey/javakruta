package com.example.gogot.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gogot.R;

import static com.example.gogot.ui.GameActivity.amountOfPlayers;


public class MainActivity extends AppCompatActivity {


    public static final String AMOUNT_OF_PLAYERS = "amountOfPlayers";
    public static final int START_GAME = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button newGameButton = findViewById(R.id.buttonNewGame);
        newGameButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,
                    StartGameActivity.class);
            startActivityForResult(intent, START_GAME);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        amountOfPlayers = Integer.valueOf(data.getStringExtra(AMOUNT_OF_PLAYERS));
        Intent intent = new Intent(MainActivity.this,
                GameActivity.class);
        startActivity(intent);
    }

}
