package com.example.gogot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button newGameButton = findViewById(R.id.buttonNewGame);
        newGameButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,
                    GameActivity.class);
            startActivity(intent);
        });
    }
}
