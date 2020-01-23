package com.example.gogot.model.settings;

import com.example.gogot.model.settings.gallery.PlayerPictures;
import com.example.gogot.relation.settings.SettingsMainContract;

public class SettingsModel implements
        SettingsMainContract.SettingsModel {
    @Override
    public int[] getPictures() {
        return PlayerPictures.getPictures();
    }

    @Override
    public int[] getPlayerPictures() {
        return PlayerPictures.getPlayerPictures();
    }

    @Override
    public void setPictureToPlayer(int pic, int player) {
        PlayerPictures.setPicToPlayer(pic, player);
    }
}
