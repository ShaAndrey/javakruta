package com.example.gogot.relation.settings;

import com.example.gogot.model.settings.SettingsModel;

public class SettingsPresenter implements
        SettingsMainContract.SettingsPresenter {
    private SettingsMainContract.SettingsView view;
    private SettingsMainContract.SettingsModel model;

    public SettingsPresenter(SettingsMainContract.SettingsView view) {
        this.view = view;
        model = new SettingsModel();
    }

    @Override
    public void setPlayersTable() {
        view.setPlayersTable(model.getPictures(), model.getPlayerPictures());

    }

    @Override
    public void setPictureToPlayer(int pic, int player) {
        model.setPictureToPlayer(pic, player);
        view.saveConfig(model.getPlayerPictures());
    }

    public void setTimers() {
        view.setTimers(model.getTimers(), model.getTimersState(), model.getIsTimersEqual());
    }

    @Override
    public void setTimersOn(boolean checked) {
        model.setTimersOn(checked);
    }

    @Override
    public void setTimersEqual(boolean checked) {
        model.setTimersEqual(checked);
    }

    @Override
    public void switchMusic(boolean isOn) {
        model.switchMusic(isOn);
    }
}
