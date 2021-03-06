package com.kazakovproduction.gogot.ui.game.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.kazakovproduction.gogot.R;
import com.kazakovproduction.gogot.ui.game.activity.StartGameActivity;

public class BotDifficultiesDialog extends Dialog {
    BotDifficultiesDialogListener listener;

    public BotDifficultiesDialog(@NonNull Context context) {
        super(context);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bot_difficulties_dialog);
        Button resumeGameButton = findViewById(R.id.easyButton);
        resumeGameButton.setOnClickListener(v -> {
            this.dismiss();
            listener.setBotDifficulty(StartGameActivity.BotDifficulty.EASY);
        });
        Button backToMainMenuButton = findViewById(R.id.hardButton);
        backToMainMenuButton.setOnClickListener(v -> {
            this.dismiss();
            listener.setBotDifficulty(StartGameActivity.BotDifficulty.HARD);
        });
    }

    public interface BotDifficultiesDialogListener {
        void setBotDifficulty(StartGameActivity.BotDifficulty bd);
    }

    public void setListener(BotDifficultiesDialogListener listener) {
        this.listener = listener;
    }
}
