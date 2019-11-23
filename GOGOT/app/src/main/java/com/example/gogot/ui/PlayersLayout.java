package com.example.gogot.ui;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gogot.R;

import java.util.List;

public class PlayersLayout extends ConstraintLayout {

    List<PlayerHandLayout> playerHandLayouts;

    public PlayersLayout(Context context) {
        super(context);
    }

    public PlayersLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayersLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void initLayout() {
        inflate(getContext(), R.layout.la, this);
    }



}
