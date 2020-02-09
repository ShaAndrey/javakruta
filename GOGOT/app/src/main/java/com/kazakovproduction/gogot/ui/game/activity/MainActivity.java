package com.kazakovproduction.gogot.ui.game.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import com.kazakovproduction.gogot.R;
import com.kazakovproduction.gogot.model.settings.FileReaderWriter;
import com.kazakovproduction.gogot.model.settings.Sounds;
import com.kazakovproduction.gogot.model.settings.gallery.PlayerPictures;
import com.kazakovproduction.gogot.ui.settings.activity.SettingsActivity;


public class MainActivity extends AppCompatActivity {


    public static final String PLAYER_PICS_TXT = "player_pics.txt";
    public static final String MUSIC_TXT = "music.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        Button newGameButton = findViewById(R.id.button_new_game);
        newGameButton.setOnClickListener(v -> onNewGame());
        Button settingsButton = findViewById(R.id.buttonSettings);
        settingsButton.setOnClickListener(v -> onSettings());
        Button tutorialButton = findViewById(R.id.button_tutorial);
        tutorialButton.setOnClickListener(v -> onTutorial());
        loadSettings();
    }

    private void loadSettings() {
        int[] def = {0, 1, 2};
        PlayerPictures.loadPictures(FileReaderWriter.
                readPlacesFile(getApplicationContext(),
                        PLAYER_PICS_TXT, def));

        int[] deff = {1};
        if (FileReaderWriter.
                readPlacesFile(getApplicationContext(),
                        MUSIC_TXT, deff)[0] == 1) {
            Sounds.setIsMusikOn(true);
        } else {
            Sounds.setIsMusikOn(false);
        }
    }

    private void onTutorial() {
        Intent intent = new Intent(MainActivity.this,
                TutorialActivity.class);
        startActivity(intent);
    }

    public void onSettings() {
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
