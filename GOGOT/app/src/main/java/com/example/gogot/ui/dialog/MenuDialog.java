package com.example.gogot.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gogot.R;

public class MenuDialog extends Dialog {
    Context context;
    MenuDialogListener menuDialogListener;

    public MenuDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public MenuDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected MenuDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
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
    }


    public interface MenuDialogListener {
        void openSettings();
        void exitGame();
    }


    public void setListener(MenuDialogListener menuDialogListener) {
        this.menuDialogListener = menuDialogListener;
    }
}
