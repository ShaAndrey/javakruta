package com.example.gogot.model.settings.gallery;

import com.example.gogot.R;

public class PlayerPictures {
    int[] pictures = {R.drawable.player_mustashe,
            R.drawable.player_beard,
            R.drawable.player_bald,
            R.drawable.player_girl,
            R.drawable.player_bangs,
            R.drawable.player_curvy};
    int[] playersPics = {0, 1, 2};

    int getPicForPlayer(int player) {
        return pictures[playersPics[player]];
    }

    public void setPicToPlayer(int pic, int player) {
        for (int player2 = 0; player2 < playersPics.length; player2++) {
            int ind = playersPics[player2];
            if (ind == pic) {
                playersPics[player2] = playersPics[player];
                break;
            }
        }
        playersPics[player] = pic;
    }

    public int[] getPictures() {
        return pictures;
    }

    public int[] getPlayerPictures() {
        return playersPics;
    }
}
