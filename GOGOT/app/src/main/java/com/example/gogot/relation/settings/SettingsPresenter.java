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
    }
}
