package com.example.gogot.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.widget.TextViewCompat;

import com.example.gogot.R;
import com.example.gogot.model.PlayCard;


import static androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.END;
import static androidx.constraintlayout.widget.ConstraintSet.BOTTOM;
import static androidx.constraintlayout.widget.ConstraintSet.START;
import static androidx.constraintlayout.widget.ConstraintSet.TOP;

abstract public class PlayerHandLayout extends ConstraintLayout {
    private Guideline[] horizontalGuidelines;
    private Guideline[] verticalGuidelines;
    private int[][] cardsImageViewId;
    private int[][] cardsAmountTextViewId;
    protected int playerHandWidth;
    protected int playerHandHeight;
    int padding = 10;
    ActivityPlayerHandListener activityPlayerHandListener;

    public PlayerHandLayout(Context context) {
        super(context);
        setSize();
        initLayout();
    }

    public PlayerHandLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setSize();
        initLayout();
    }

    public PlayerHandLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setSize();
        initLayout();
    }

    protected void initLayout() {
        inflate(getContext(), R.layout.layout_player_hand, this);
    }

    void initHand() {
        int horizontalGuidelinesCount = playerHandHeight + 1;
        int verticalGuidelinesCount = playerHandWidth + 1;
        horizontalGuidelines = new Guideline[horizontalGuidelinesCount];
        verticalGuidelines = new Guideline[verticalGuidelinesCount];

        for (int i = 0; i < horizontalGuidelinesCount; i++) {
            horizontalGuidelines[i] = findViewWithTag("horizontalGuidelinePlayer" + i);
        }
        for (int i = 0; i < verticalGuidelinesCount; i++) {
            verticalGuidelines[i] = findViewWithTag("verticalGuidelinePlayer" + i);
        }
        initializePlayerHand();
    }

    void initializePlayerHand() {
        cardsImageViewId = new int[playerHandHeight][playerHandWidth];
        cardsAmountTextViewId = new int[playerHandHeight][playerHandWidth];
        for (int i = 0; i < playerHandHeight; i++) {
            for (int j = 0; j < playerHandWidth; j++) {
                if (i * playerHandWidth + j >= 8) {                 // TODO add flexibility
                    return;
                }
                ImageView imageView = new ImageView(getContext());
                initializeImageView(imageView, i, j);
                TextView amountView = new TextView(getContext());
                initializeAmountView(amountView, i, j);
            }
        }
    }

    private void initializeImageView(ImageView imageView, int i, int j) {
        imageView.setId(View.generateViewId());
        cardsImageViewId[i][j] = imageView.getId();
        imageView.setImageResource(activityPlayerHandListener.
                setImageToIndex(i * playerHandWidth + j + 1));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setPadding(padding, padding, padding, padding);

        Constraints.LayoutParams params = new Constraints.LayoutParams(0, 0);

        GradientDrawable border = new GradientDrawable();
        border.setColor(0xffEB5D0D);
        border.setStroke(1, 0x66000000);
        imageView.setBackground(border);

        addView(imageView, params);
        placeViewInCell(imageView.getId(), i, j);
    }

    void initializeAmountView(TextView amountView, int i, int j) {
        amountView.setId(View.generateViewId());
        cardsAmountTextViewId[i][j] = amountView.getId();
        amountView.setTextColor(Color.parseColor("#CCFFFF00"));
        amountView.setPadding(padding, padding, padding, padding);
        addView(amountView);
        placeViewInCell(amountView.getId(), i, j);
        setTextView(amountView.getId(), 0);
    }

    interface ActivityPlayerHandListener {
        int setImageToIndex(int index);
    }

    void setListener(ActivityPlayerHandListener activityListener) {
        activityPlayerHandListener = activityListener;
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

    void addCardsAmount(PlayCard.State stateOfCardsToAdd,
                        int amountOfCardsToAdd) {
        Point position = getPointByStateId(stateOfCardsToAdd.ordinal());
        int id = cardsAmountTextViewId[position.x][position.y];
        changeTextView(id, amountOfCardsToAdd);
        placeViewInCell(id, position.x, position.y);
    }

    void updatePlayerPoints(int playerPoints) {
        int id = cardsAmountTextViewId[0][0];
        setTextView(id, playerPoints);
        placeViewInCell(id, 0, 0);
    }

    void setTextView(int id, int value) {
        TextView view = findViewById(id);
        view.setText(String.valueOf(value));
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(view, 1, 1, 1,
                TypedValue.COMPLEX_UNIT_DIP);
        view.setTextSize(40);                           // kostyl
    }

    void changeTextView(int id, int changeOn) {
        TextView view = findViewById(id);
        String text = view.getText().toString();
        int amount = Integer.parseInt(text);
        view.setText(String.valueOf(amount + changeOn));
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(view, 1, 1, 1,
                TypedValue.COMPLEX_UNIT_DIP);
        view.setTextSize(40);                           // kostyl
    }

    Point getPointByStateId(int i) {
        Point point = new Point();
        point.x = (i - 1) / playerHandWidth;
        point.y = i - playerHandWidth * (point.x) - 1;
        return point;
    }

    void updateIllumination(boolean[] playerDominateStates, boolean currentPlayer) {
        for (int i = 2; i < playerDominateStates.length; i++) {
            GradientDrawable border = new GradientDrawable();
            border.setColor(0xffEB5D1F);
            if (playerDominateStates[i]) {
                border.setStroke(padding, 0xFFffd700);
            } else {
                border.setStroke(1, 0x66000000);
            }
            Point point = getPointByStateId(i);
            ImageView imageView = findViewById(cardsImageViewId[point.x][point.y]);
            imageView.setBackground(border);
        }
        GradientDrawable border = new GradientDrawable();
        border.setColor(0xffEB5D1F);
        if (currentPlayer) {
            border.setStroke(padding, 0xFF0000FF);
        } else {
            border.setStroke(1, 0x66000000);
        }
        Point point = getPointByStateId(1);
        ImageView imageView = findViewById(cardsImageViewId[point.x][point.y]);
        imageView.setBackground(border);
    }

    protected void setSize() {
    }
}
