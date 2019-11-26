package com.example.gogot.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gogot.R;

import static com.example.gogot.ui.MainActivity.AMOUNT_OF_PLAYERS;

public class StartGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        Button getBackButton = findViewById(R.id.backButton);
        getBackButton.setOnClickListener(v -> {
            Intent intent = new Intent(StartGameActivity.this,
                    MainActivity.class);
            startActivity(intent);
        });
    }

    public void onStartGameClick(View view) {
        Intent intent = new Intent(StartGameActivity.this,
                GameActivity.class);
        switch (view.getId()) {
            case R.id.vsAIButton:
                intent.putExtra(AMOUNT_OF_PLAYERS, "1");
                break;
            case R.id.oneVSOneButton:
                intent.putExtra(AMOUNT_OF_PLAYERS, "2");
                break;
            case R.id.threePlayersButton:
                intent.putExtra(AMOUNT_OF_PLAYERS, "3");
                break;
            default:
                intent.putExtra(AMOUNT_OF_PLAYERS, "0");
                break;
        }
        setResult(RESULT_OK, intent);
        finish();
    }
}
