package com.example.gogot.ui;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.util.TimeUtils;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;
import androidx.constraintlayout.widget.Guideline;

import com.example.gogot.R;
import com.example.gogot.model.BoardCard;
import com.example.gogot.model.PlayCard;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.END;
import static androidx.constraintlayout.widget.ConstraintSet.BOTTOM;
import static androidx.constraintlayout.widget.ConstraintSet.START;
import static androidx.constraintlayout.widget.ConstraintSet.TOP;

public class GameBoardLayout extends ConstraintLayout {
    private Guideline[] horizontalGuidelines;
    private Guideline[] verticalGuidelines;
    private ImageView playerView;
    private int boardSize;
    private ActivityListener activityListener;
    private int[][] viewId;
    private Point playerPosition;


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

    void initBoard(int boardSize, BoardCard[][] boardCards, ArrayList<BoardCard> cardsToMove) {
        this.boardSize = boardSize;
        int guidelinesCount = boardSize + 1;
        horizontalGuidelines = new Guideline[guidelinesCount];
        verticalGuidelines = new Guideline[guidelinesCount];

        for (int i = 0; i < guidelinesCount; i++) {
            horizontalGuidelines[i] = findViewWithTag("horizontalGuideline" + i);
            verticalGuidelines[i] = findViewWithTag("verticalGuideline" + i);
        }
        initializeGameBoard(boardCards, cardsToMove);
    }

    private void initializeGameBoard(BoardCard[][] boardCards,
                                     ArrayList<BoardCard> cardsToMove) {
        viewId = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (boardCards[i][j].getState().equals(PlayCard.State.PLAYER)) {
                    playerPosition = new Point(i, j);
                } else {
                    ImageView imageView = new ImageView(getContext());
                    initializeImageView(imageView, boardCards, i, j);
                }
            }
        }
        playerView = new ImageView(getContext());
        initializeImageView(playerView, boardCards, playerPosition.x, playerPosition.y);
        setIllumination(boardCards, cardsToMove);
    }

    private void initializeImageView(ImageView imageView,
                                     BoardCard[][] boardCards, int i, int j) {
        imageView.setId(View.generateViewId());
        viewId[i][j] = imageView.getId();
        imageView.setImageResource(activityListener.setImageToCard(boardCards[i][j]));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setPadding(5, 5, 5, 5);
        imageView.setOnClickListener(v -> activityListener.startTurn(boardCards[i][j]));

        Constraints.LayoutParams params = new Constraints.LayoutParams(0, 0);
        addView(imageView, params);
        placeViewInCell(imageView.getId(), i, j);
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

        void startTurn(BoardCard boardCard);
    }

    void setListener(ActivityListener activityListener) {
        this.activityListener = activityListener;
    }

    void movePlayer(Point playerPosition, Point newPlayerPosition) {
        int curId = viewId[newPlayerPosition.x][newPlayerPosition.y];
        viewId[newPlayerPosition.x][newPlayerPosition.y] = viewId[playerPosition.x][playerPosition.y];
        viewId[playerPosition.x][playerPosition.y] = curId;
        ConstraintSet set = new ConstraintSet();
        set.clone(this);
        set.connect(playerView.getId(), TOP, horizontalGuidelines[newPlayerPosition.x].getId(), TOP);
        set.connect(playerView.getId(), BOTTOM, horizontalGuidelines[newPlayerPosition.x + 1].getId(), BOTTOM);
        set.connect(playerView.getId(), START, verticalGuidelines[newPlayerPosition.y].getId(), START);
        set.connect(playerView.getId(), END, verticalGuidelines[newPlayerPosition.y + 1].getId(), END);
        ChangeBounds transition = new ChangeBounds();
        transition.setInterpolator(new AnticipateOvershootInterpolator(1.0f));
        int animationDuration = 1500;
        transition.setDuration(animationDuration);
        TransitionManager.beginDelayedTransition(this, transition);
        set.applyTo(this);
    }

    void removeIllumination(BoardCard[][] boardCards) {
        setIllumination(boardCards, null);
    }

    void collectCards(ArrayList<BoardCard> cardsToCollect) {
        // TODO
    }

    void setIllumination(BoardCard[][] boardCards,
                         ArrayList<BoardCard> cardsToMove) {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                GradientDrawable border = new GradientDrawable();
                border.setColor(0xffEB5D0D);
                if (cardsToMove != null && cardsToMove.contains(boardCards[i][j])) {
                    border.setStroke(5, 0xFF00FF00);
                } else if (boardCards[i][j].getState() == PlayCard.State.PLAYER) {
                    border.setStroke(5, 0xFF0000FF);

                } else {
                    border.setStroke(1, 0x66000000);
                }
                ImageView imageView = findViewById(viewId[i][j]);
                imageView.setBackground(border);
            }
        }
    }

    void refreshBoard(BoardCard[][] boardCards,
                      ArrayList<BoardCard> cardsToMove) {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                ImageView imageView = findViewById(viewId[i][j]);
                if (boardCards[i][j].getState() == PlayCard.State.NOTHING) {
                    imageView.setVisibility(View.INVISIBLE);
                } else {
                    imageView.setImageResource(activityListener.setImageToCard(boardCards[i][j]));
                }
            }
        }
        setIllumination(boardCards, cardsToMove);
    }

}
