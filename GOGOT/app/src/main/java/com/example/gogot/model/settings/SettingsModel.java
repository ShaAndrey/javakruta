package com.example.gogot.model.settings;

import com.example.gogot.model.settings.gallery.PlayerPictures;
import com.example.gogot.relation.settings.SettingsMainContract;

public class SettingsModel implements
        SettingsMainContract.SettingsModel {
    PlayerPictures playerPictures;

    public SettingsModel() {
        playerPictures = new PlayerPictures();
    }

    @Override
    public int[] getPictures() {
        return playerPictures.getPictures();
    }

    @Override
    public int[] getPlayerPictures() {
        return playerPictures.getPlayerPictures();
    }
}
