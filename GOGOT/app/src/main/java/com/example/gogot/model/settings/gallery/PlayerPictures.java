package com.example.gogot.model.settings.gallery;

import com.example.gogot.R;

import java.util.Arrays;

public class PlayerPictures {
    private static int[] pictures = {R.drawable.player_mustashe,
            R.drawable.player_beard,
            R.drawable.player_bald,
            R.drawable.player_girl,
            R.drawable.player_bangs,
            R.drawable.player_curvy};
    private static int[] playersPics = {0, 1, 2};

    public static void loadPictures(int[] players) {
        for (int i = 0; i < playersPics.length; i++) {
            playersPics[i] = players[i];
        }
    }

    public static int getPicForPlayer(int player) {
        return pictures[playersPics[player]];
    }

    public static void setPicToPlayer(int pic, int player) {
        for (int player2 = 0; player2 < playersPics.length; player2++) {
            int ind = playersPics[player2];
            if (ind == pic) {
                playersPics[player2] = playersPics[player];
                break;
            }
        }
        playersPics[player] = pic;
    }

    public static int[] getPictures() {
        return pictures;
    }

    public static int[] getPlayerPictures() {
        return playersPics;
    }
}
