package com.example.gogot.ui;

import android.content.Context;
import android.content.Intent;
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
        Button newGameButton = findViewById(R.id.buttonNewGame);
        newGameButton.setOnClickListener(v -> endGameLayoutListener.onNewGame());
        Button backToMainMenuButton = findViewById(R.id.backToMainMenuButton);
        backToMainMenuButton.setOnClickListener(v -> endGameLayoutListener.onExit());
    }

    interface EndGameLayoutListener {
        void onNewGame();
        void onExit();
    }

    void setListener(EndGameLayoutListener listener) {
        endGameLayoutListener = listener;
    }
}
