package com.kazakovproduction.gogot.ui.game.custom;

import android.content.Context;
import android.graphics.Point;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;
import androidx.constraintlayout.widget.Guideline;

import com.kazakovproduction.gogot.R;
import com.kazakovproduction.gogot.model.game.entity.BoardCard;
import com.kazakovproduction.gogot.model.game.entity.PlayCard;
import com.kazakovproduction.gogot.ui.game.listener.TransitionEndListener;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.END;
import static androidx.constraintlayout.widget.ConstraintSet.BOTTOM;
import static androidx.constraintlayout.widget.ConstraintSet.START;
import static androidx.constraintlayout.widget.ConstraintSet.TOP;

public class GameBoardLayout extends ConstraintLayout {

    private static final int CARD_TRANSITION_DURATION = 1500;

    private Guideline[] horizontalGuidelines;
    private Guideline[] verticalGuidelines;
    private ImageView playerView;

    private ActivityListener activityBoardListener;
    private Point playerPosition;
    private Point newPlayerPosition;
    private int boardSize;
    private int[][] viewId;


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

    public void initBoard(int boardSize, BoardCard[][] boardCards, ArrayList<PlayCard> cardsToMove) {
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
                                     ArrayList<PlayCard> cardsToMove) {
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
    }

    private void initializeImageView(ImageView imageView,
                                     BoardCard[][] boardCards, int i, int j) {
        imageView.setId(View.generateViewId());
        viewId[i][j] = imageView.getId();
        imageView.setImageResource(activityBoardListener.setImageToCard(boardCards[i][j]));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setListenerToView(imageView, boardCards, i, j);
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


    public interface ActivityListener {
        int setImageToCard(PlayCard card);

        void startTurn(BoardCard boardCard);

        void updateIlluminationAndCollectCards();
    }

    public void setListener(ActivityListener activityListener) {
        this.activityBoardListener = activityListener;
    }

    public void movePlayer(BoardCard playerCard, Point newPlayerPosition) {
        this.newPlayerPosition = newPlayerPosition;
        ConstraintSet set = new ConstraintSet();
        set.clone(this);
        set.connect(playerView.getId(), TOP, horizontalGuidelines[newPlayerPosition.x].getId(), TOP);
        set.connect(playerView.getId(), BOTTOM, horizontalGuidelines[newPlayerPosition.x + 1].getId(), BOTTOM);
        set.connect(playerView.getId(), START, verticalGuidelines[newPlayerPosition.y].getId(), START);
        set.connect(playerView.getId(), END, verticalGuidelines[newPlayerPosition.y + 1].getId(), END);
        ChangeBounds transition = new ChangeBounds();
        transition.setInterpolator(new AnticipateOvershootInterpolator(1.0f));
        transition.setDuration(CARD_TRANSITION_DURATION);
        transition.addListener(new TransitionEndListener() {
            @Override
            public void onTransitionEnd(Transition transition) {
                activityBoardListener.updateIlluminationAndCollectCards();
            }
        });
        TransitionManager.beginDelayedTransition(this, transition);
        set.applyTo(this);
    }


    public void collectCards(ArrayList<BoardCard> cardsToCollect) {
        cardsToCollect.forEach(boardCard -> {
            ImageView imageView = findViewById(viewId[boardCard.getRow()][boardCard.getColumn()]);
            imageView.setVisibility(View.INVISIBLE);
        });
        int curId = viewId[newPlayerPosition.x][newPlayerPosition.y];
        viewId[newPlayerPosition.x][newPlayerPosition.y] =
                viewId[playerPosition.x][playerPosition.y];
        viewId[playerPosition.x][playerPosition.y] = curId;
        playerPosition = newPlayerPosition;
    }

    void setListenerToView(ImageView imageView, BoardCard[][] boardCards, int i, int j) {
        imageView.setOnClickListener(v -> activityBoardListener.startTurn(boardCards[i][j]));
    }

    public void invalidateBoardCellsListeners() {
        for (int i = 0; i < viewId.length; i++) {
            for (int j = 0; j < viewId[0].length; j++) {
                ImageView imageView = findViewById(viewId[i][j]);
                imageView.setOnClickListener(null);
            }
        }
    }

    public void revalidateBoardCellsListeners(BoardCard[][] boardCards) {
        for (int i = 0; i < viewId.length; i++) {
            for (int j = 0; j < viewId[0].length; j++) {
                ImageView imageView = findViewById(viewId[i][j]);
                setListenerToView(imageView, boardCards, i, j);
            }
        }
    }

}
