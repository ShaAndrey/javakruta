package com.example.gogot.ui.settings.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gogot.R;
import com.example.gogot.ui.settings.custom.RVAdapterChoosePlayerPicture;

public class PictureChoiceDialog extends Dialog implements
        RVAdapterChoosePlayerPicture.RVAdapterChoosePlayerPictureListener {
    private int[] pictures;
    private int[] playersPics;
    private Context context;
    private int player;
    private PictureChoiceDialogListener listener;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pictures_dialog);
        TextView dialogTextView = findViewById(R.id.choice_pic_textview);
        dialogTextView.setText(String.format(context.getString
                        (R.string.choose_picture_for_player),
                1 + player));
        Button okButton = findViewById(R.id.ok_button);
        okButton.setOnClickListener(v -> this.dismiss());
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
        RVAdapterChoosePlayerPicture adapter = new
                RVAdapterChoosePlayerPicture(pictures, playersPics);
        adapter.setListener(this);
        resultsTable.setAdapter(adapter);
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

    @Override
    public void setPictureToPlayer(int pos) {
        listener.setPictureToPlayer(pos, player);
    }

    public interface PictureChoiceDialogListener {
        void setPictureToPlayer(int pic, int player);
    }

    public void setListener(PictureChoiceDialogListener listener) {
        this.listener = listener;
    }
}
