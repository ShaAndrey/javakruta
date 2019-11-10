package com.example.gogot.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;
import androidx.constraintlayout.widget.Guideline;

import com.example.gogot.R;
import com.example.gogot.model.BoardCard;
import com.example.gogot.model.PlayCard;

import static androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.END;
import static androidx.constraintlayout.widget.ConstraintSet.BOTTOM;
import static androidx.constraintlayout.widget.ConstraintSet.START;
import static androidx.constraintlayout.widget.ConstraintSet.TOP;

public class GameBoardLayout extends ConstraintLayout {
    private Guideline[] horizontalGuidelines;
    private Guideline[] verticalGuidelines;
    private TextView playerView;
    private int boardSize;
    private ActivityListener activityListener;

    public GameBoardLayout(Context context) {
        super(context);
        initLayout();
    }

    public GameBoardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout();
    }

    public GameBoardLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    void initLayout() {
        inflate(getContext(), R.layout.layout_game_board, this);
    }

    void initBoard(int boardSize, BoardCard[][] boardCards) {
        this.boardSize = boardSize;
        int guidelinesCount = boardSize + 1;
        horizontalGuidelines = new Guideline[guidelinesCount];
        verticalGuidelines = new Guideline[guidelinesCount];

        for (int i = 0; i < guidelinesCount; i++) {
            horizontalGuidelines[i] = findViewWithTag("horizontalGuideline" + i);
            verticalGuidelines[i] = findViewWithTag("verticalGuideline" + i);
        }
        initializeGameBoard(boardCards);
//        initializePlayerView();
    }

    private void initializeGameBoard(BoardCard[][] boardCards) {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                ImageView imageView = new ImageView(getContext());      // ??
                imageView.setId(View.generateViewId());
//                imageView.setImageResource(R.drawable.star);
                imageView.setImageResource(activityListener.setImageToCard(boardCards[i][j]));
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setPadding(1, 1, 1, 1);
                imageView.setOnClickListener(v -> {
                });

                GradientDrawable border = new GradientDrawable();
                border.setColor(0xffEB5D0D);
                border.setStroke(1, 0x66000000);
                imageView.setBackground(border);

                Constraints.LayoutParams params = new Constraints.LayoutParams(0, 0);
                addView(imageView, params);
                placeViewInCell(imageView.getId(), i, j);
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
        placeViewInCell(playerView.getId(), 2, 2);

    }

    private void placeViewInCell(int viewId, int i, int j) {
        ConstraintSet set = new ConstraintSet();
        set.clone(this);
        set.connect(viewId, TOP, horizontalGuidelines[i].getId(), TOP);
        set.connect(viewId, BOTTOM, horizontalGuidelines[i + 1].getId(), BOTTOM);
        set.connect(viewId, START, verticalGuidelines[j].getId(), START);
        set.connect(viewId, END, verticalGuidelines[j + 1].getId(), END);
        set.applyTo(this);
    }


    interface ActivityListener {
        int setImageToCard(PlayCard card);
    }

    void setListener(ActivityListener activityListener) {
        this.activityListener = activityListener;
    }
}
