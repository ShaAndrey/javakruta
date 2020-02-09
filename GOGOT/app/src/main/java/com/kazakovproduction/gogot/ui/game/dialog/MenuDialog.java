package com.kazakovproduction.gogot.ui.game.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.kazakovproduction.gogot.R;

public class MenuDialog extends Dialog {
    Context context;
    MenuDialogListener menuDialogListener;

    public MenuDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_menu_dialog);
        Button resumeGameButton = findViewById(R.id.resumeGameButton);
        resumeGameButton.setOnClickListener(v -> {
            this.dismiss();
        });
        Button backToMainMenuButton = findViewById(R.id.backToMainMenuButton);
        backToMainMenuButton.setOnClickListener(v -> {
            this.dismiss();
            menuDialogListener.exitGame();
        });
        Button settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(v -> {
            this.dismiss();
            menuDialogListener.openSettings();
        });
        Button restartGameButton = findViewById(R.id.restartGameButton);
        restartGameButton.setOnClickListener(v->{
            this.dismiss();
            menuDialogListener.restartGame();
        });
    }

    public interface MenuDialogListener {
        void openSettings();
        void exitGame();
        void restartGame();
    }


    public void setListener(MenuDialogListener menuDialogListener) {
        this.menuDialogListener = menuDialogListener;
    }
}
