package com.example.gogot.ui;

import android.content.Context;
import android.util.AttributeSet;

import com.example.gogot.R;

public class PlayerHandLayout3x3 extends PlayerHandLayout {
    public PlayerHandLayout3x3(Context context) {
        super(context);
    }

    public PlayerHandLayout3x3(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayerHandLayout3x3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void setSize() {
        playerHandWidth = 3;
        playerHandHeight = 3;
    }

    @Override
    protected void initLayout() {
        inflate(getContext(), R.layout.layout_3x3_player_hand, this);
    }
}
