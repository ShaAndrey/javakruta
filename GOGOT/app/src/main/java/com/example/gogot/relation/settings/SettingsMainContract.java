package com.example.gogot.relation.settings;

public interface SettingsMainContract {
    interface SettingsView {
        void setPlayersTable(int[] pictures, int[] playersPictures);
    }

    interface SettingsPresenter {
        void setPlayersTable();
    }

    interface SettingsModel {
        int[] getPictures();

        int[] getPlayerPictures();
    }
}
