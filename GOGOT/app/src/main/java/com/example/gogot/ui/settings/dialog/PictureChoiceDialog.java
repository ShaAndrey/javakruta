package com.example.gogot.ui.settings.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gogot.R;
import com.example.gogot.ui.settings.custom.RVAdapterChoosePlayerPicture;
import com.example.gogot.ui.settings.custom.RVAdapterPlayers;

public class PictureChoiceDialog extends Dialog {
    private int[] pictures;
    private int[] playersPics;
    private Context context;
    private int player;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pictures_dialog);
        TextView dialogTextView = findViewById(R.id.choice_pic_textview);
        dialogTextView.setText(String.format(context.getString
                        (R.string.choose_picture_for_player),
                1 + player));
        createPicTable();
    }

    private void createPicTable() {
        RecyclerView resultsTable = findViewById(R.id.player_picture_choice);
        resultsTable.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(context, 4) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                };
        resultsTable.setLayoutManager(gridLayoutManager);
        resultsTable.setAdapter(new
                RVAdapterChoosePlayerPicture(pictures, playersPics));
    }

    public PictureChoiceDialog(@NonNull Context context,
                               int[] pictures,
                               int[] playerPics,
                               int player) {
        super(context);
        this.context = context;
        this.pictures = pictures;
        this.playersPics = playerPics;
        this.player = player;
    }
}
