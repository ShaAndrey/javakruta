package com.example.gogot.model.settings.gallery;

import com.example.gogot.R;

public class PlayerPictures {
    int[] pictures = {R.drawable.player_mustashe,
            R.drawable.player_beard,
            R.drawable.player_girl,
            R.drawable.player_bangs};
    int[] playersPics = {0, 1, 2};

    int getPicForPlayer(int player) {
        return pictures[playersPics[player]];
    }

    public void setPicToPlayer(int pic, int player) {
//        for (int i : playersPics) {
//            if(i == pic) {
//                return;
//            }
//        }
        playersPics[player] = pic;
    }

    public int[] getPictures() {
        return pictures;
    }

    public int[] getPlayerPictures() {
        return playersPics;
    }
}
