package com.example.gogot.ui.game.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.gogot.R;
import com.example.gogot.ui.settings.activity.SettingsActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button newGameButton = findViewById(R.id.button_new_game);
        newGameButton.setOnClickListener(v -> onNewGame());
        Button settingsButton = findViewById(R.id.buttonSettings);
        settingsButton.setOnClickListener(v -> onSettings());
        Button tutorialButton = findViewById(R.id.button_tutorial);
        tutorialButton.setOnClickListener(v -> onTutorial());
    }

    private void onTutorial() {
        Intent intent = new Intent(MainActivity.this,
                TutorialActivity.class);
        startActivity(intent);
    }

    public void onSettings() {
        Intent intent = new Intent(MainActivity.this,
                SettingsActivity.class);
//        presenter.onSettings(intent);
        startActivity(intent);
    }

//    private void putExtra(Intent intent) {
//
//        intent.putExtra(PLAYER_PICS, );
//        intent.putExtra(PICTURES, );
//    }

    private void onNewGame() {
        Intent intent = new Intent(MainActivity.this,
                StartGameActivity.class);
        startActivity(intent);
    }

}