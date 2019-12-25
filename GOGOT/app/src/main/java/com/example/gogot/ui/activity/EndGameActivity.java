package com.example.gogot.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gogot.R;
import com.example.gogot.model.Player;
import com.example.gogot.ui.custom.RVAdapterEndGameTable;

import java.util.List;

import static com.example.gogot.ui.activity.GameActivity.ACTION_ON_END_GAME;
import static com.example.gogot.ui.activity.GameActivity.PLAYERS;

public class EndGameActivity extends AppCompatActivity {
    public static final String NEW_GAME = "NEW_GAME";
    public static final String TO_MAIN_MENU = "TO_MAIN_MENU";
    public static final String RESTART_GAME = "RESTART_GAME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_end_game);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setResultTable();
    }

    private void setResultTable() {
        Intent intent = getIntent();
        RecyclerView resultsTable = findViewById(R.id.game_results_table);
        resultsTable.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this, 1) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                };
        resultsTable.setLayoutManager(gridLayoutManager);
        resultsTable.setAdapter(new RVAdapterEndGameTable
                (intent.getParcelableArrayListExtra(PLAYERS)));
    }

    public void onEndGameButtonClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.button_new_game:
                intent.putExtra(ACTION_ON_END_GAME, NEW_GAME);
                break;
            case R.id.backToMainMenuButton:
                intent.putExtra(ACTION_ON_END_GAME, TO_MAIN_MENU);
                break;
            case R.id.button_restart_game:
                intent.putExtra(ACTION_ON_END_GAME, RESTART_GAME);
        }
        setResult(RESULT_OK, intent);
        finish();
    }
}
