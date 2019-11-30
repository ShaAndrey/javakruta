package com.example.gogot.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gogot.R;

public class EndGameLayout extends ConstraintLayout {
    EndGameLayoutListener endGameLayoutListener;

    public EndGameLayout(Context context) {
        super(context);
        initLayout();
    }

    public EndGameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout();
    }

    public EndGameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    void initLayout() {
        inflate(getContext(), R.layout.layout_end_game, this);
        Button newGameButton = findViewById(R.id.button_new_game);
        newGameButton.setOnClickListener(v -> endGameLayoutListener.onNewGame());
        Button backToMainMenuButton = findViewById(R.id.backToMainMenuButton);
        backToMainMenuButton.setOnClickListener(v -> endGameLayoutListener.onExit());
        Button restartGameButton = findViewById(R.id.button_restart_game);
        restartGameButton.setOnClickListener(v -> endGameLayoutListener.onRestartGame());
    }

    public interface EndGameLayoutListener {
        void onNewGame();
        void onExit();
        void onRestartGame();
    }

    public void setListener(EndGameLayoutListener listener) {
        endGameLayoutListener = listener;
    }
}
