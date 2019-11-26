package com.example.gogot.ui;

import android.content.Context;
import android.util.AttributeSet;

import com.example.gogot.R;

public class PlayerHandLayout4x2 extends PlayerHandLayout {
    public PlayerHandLayout4x2(Context context) {
        super(context);
    }

    public PlayerHandLayout4x2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayerHandLayout4x2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void setSize() {
        playerHandWidth = 4;
        playerHandHeight = 2;
    }

    @Override
    protected void initLayout() {
        inflate(getContext(), R.layout.layout_2x4_player_hand, this);
    }
}
