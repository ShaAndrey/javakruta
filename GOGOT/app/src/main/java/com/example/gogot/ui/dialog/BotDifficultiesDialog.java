package com.example.gogot.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.gogot.R;

public class BotDifficultiesDialog extends Dialog {
    Context context;

    public BotDifficultiesDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bot_difficulties_dialog);
        Button resumeGameButton = findViewById(R.id.easyButton);
        resumeGameButton.setOnClickListener(v -> {
            this.dismiss();
        });
        Button backToMainMenuButton = findViewById(R.id.hardButton);
        backToMainMenuButton.setOnClickListener(v -> {
            this.dismiss();
        });
    }
}
