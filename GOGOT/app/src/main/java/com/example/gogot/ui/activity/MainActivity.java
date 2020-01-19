package com.example.gogot.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.gogot.R;


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

    private void onSettings() {
        Intent intent = new Intent(MainActivity.this,
                SettingsActivity.class);
        startActivity(intent);
    }

    private void onNewGame() {
        Intent intent = new Intent(MainActivity.this,
                StartGameActivity.class);
        startActivity(intent);
    }

}
