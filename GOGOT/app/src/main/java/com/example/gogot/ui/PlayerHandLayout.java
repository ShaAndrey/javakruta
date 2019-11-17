package com.example.gogot.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Pair;
import android.util.TypedValue;
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

import java.util.ArrayList;
import java.util.Objects;

import static androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.END;
import static androidx.constraintlayout.widget.ConstraintSet.BOTTOM;
import static androidx.constraintlayout.widget.ConstraintSet.START;
import static androidx.constraintlayout.widget.ConstraintSet.TOP;

public class PlayerHandLayout extends ConstraintLayout {
    private Guideline[] horizontalGuidelines;
    private Guideline[] verticalGuidelines;
    private int[][] cardsImageViewId;
    private int[][] cardsAmountTextViewId;
    private int playerHandWidth = 4;
    private int playerHandHeight = 2;
    ActivityPlayerHandListener activityPlayerHandListener;

    public PlayerHandLayout(Context context) {
        super(context);
        initLayout();
    }

    public PlayerHandLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout();
    }

    public PlayerHandLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    void initLayout() {
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
        imageView.setPadding(5, 5, 5, 5);

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
        if (i != 0 || j != 0) {
            amountView.setText("0");
        }
        amountView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50f);
        amountView.setTextColor(Color.parseColor("#CCFFFF00"));
        amountView.setPadding(5, 5, 5, 5);
        addView(amountView);
        placeViewInCell(amountView.getId(), i, j);
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
        PlayCard.State[] h = PlayCard.State.values();
        for (int i = 2; i < h.length; i++) {
            if (stateOfCardsToAdd.equals(h[i])) {
                Point position = getPointByStateId(i);
                int id = cardsAmountTextViewId[position.x][position.y];
                changeTextView(id, amountOfCardsToAdd);
                placeViewInCell(id, position.x, position.y);
                break;
            }
        }
    }

    void changeTextView(int id, int changeOn) {
        TextView view = findViewById(id);
        String text = view.getText().toString();
        int amount = Integer.parseInt(text);
        view.setText(String.valueOf(amount + changeOn));
    }

    Point getPointByStateId(int i) {
        Point point = new Point();
        point.x = (i - 1) / playerHandWidth;
        point.y = i - 4 * (point.x) - 1;
        return point;
    }
}
