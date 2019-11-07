package com.example.gogot;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;
import androidx.constraintlayout.widget.Guideline;

import static androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.END;
import static androidx.constraintlayout.widget.ConstraintSet.BOTTOM;
import static androidx.constraintlayout.widget.ConstraintSet.START;
import static androidx.constraintlayout.widget.ConstraintSet.TOP;


public class BoardActivity extends ConstraintLayout {
    private Guideline[] horizontalGuidelines;
    private Guideline[] verticalGuidelines;
    private TextView playerView;
//    private Board gameBoard;
    private int boardSize;

    BoardActivity(Context context, int size) {
        super(context);
        boardSize = size;
        inflate(getContext(), R.layout.layout_game_board, this);
        int guidelinesCount = boardSize + 1;
        horizontalGuidelines = new Guideline[guidelinesCount];
        verticalGuidelines = new Guideline[guidelinesCount];

        for (int i = 0; i < guidelinesCount; i++) {
            horizontalGuidelines[i] = findViewWithTag("horizontalGuideline" + i);
            verticalGuidelines[i] = findViewWithTag("verticalGuideline" + i);
        }
        initializeGameBoard();
        initializePlayerView();
    }


    private void initializeGameBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                ImageView imageView = new ImageView(getContext());      // ??
                imageView.setId(View.generateViewId());
                imageView.setImageResource(R.drawable.star);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                GradientDrawable border = new GradientDrawable();
                border.setColor(0xffEB5D0D);
                border.setStroke(1, 0x66000000);
                imageView.setBackground(border);

                Constraints.LayoutParams params = new Constraints.LayoutParams(0, 0);
                addView(imageView, params);

                ConstraintSet set = new ConstraintSet();
                set.clone(this);
                set.connect(imageView.getId(), TOP, horizontalGuidelines[i].getId(), TOP);
                set.connect(imageView.getId(), BOTTOM, horizontalGuidelines[i + 1].getId(), BOTTOM);
                set.connect(imageView.getId(), START, verticalGuidelines[j].getId(), START);
                set.connect(imageView.getId(), END, verticalGuidelines[j + 1].getId(), END);
                set.applyTo(this);
            }
        }
    }

    private void initializePlayerView() {
        playerView = new TextView(getContext());            // ??
        playerView.setId(View.generateViewId());
        playerView.setText("Player");
        playerView.setGravity(Gravity.CENTER);
        playerView.setBackgroundColor(Color.parseColor("#FFD500"));
        Constraints.LayoutParams params = new Constraints.LayoutParams(0, 0);
        addView(playerView, params);

        ConstraintSet set = new ConstraintSet();
        set.clone(this);
        set.connect(playerView.getId(), TOP, horizontalGuidelines[2].getId(), TOP);
        set.connect(playerView.getId(), BOTTOM, horizontalGuidelines[2 + 1].getId(), BOTTOM);
        set.connect(playerView.getId(), START, verticalGuidelines[2].getId(), START);
        set.connect(playerView.getId(), END, verticalGuidelines[2 + 1].getId(), END);
        set.applyTo(this);
    }
}
