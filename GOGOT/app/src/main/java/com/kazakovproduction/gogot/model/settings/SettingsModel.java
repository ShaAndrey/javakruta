package com.kazakovproduction.gogot.model.settings;

import com.kazakovproduction.gogot.model.settings.gallery.PlayerPictures;
import com.kazakovproduction.gogot.relation.settings.SettingsMainContract;

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

    @Override
    public long[] getTimers() {
        return Timers.getTimers();
    }

    @Override
    public Boolean getTimersState() {
        return Timers.getTimersOn();
    }

    @Override
    public Boolean getIsTimersEqual() {
        return Timers.getIsEqual();
    }

    @Override
    public void setTimersOn(boolean checked) {
        Timers.setTimersOn(checked);
    }

    @Override
    public void setTimersEqual(boolean checked) {
        Timers.setIsEqual(checked);
    }

    @Override
    public void switchMusic(boolean isOn) {
        Sounds.setIsMusikOn(isOn);
    }
}
