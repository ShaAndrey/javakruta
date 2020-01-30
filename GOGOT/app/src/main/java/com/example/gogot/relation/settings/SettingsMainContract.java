package com.example.gogot.relation.settings;

public interface SettingsMainContract {
    interface SettingsView {
        void setPlayersTable(int[] pictures, int[] playersPictures);

        void saveConfig(int[] playerPictures);
    }

    interface SettingsPresenter {
        void setPlayersTable();

        void setPictureToPlayer(int pic, int player);
    }

    interface SettingsModel {
        int[] getPictures();

        int[] getPlayerPictures();

        void setPictureToPlayer(int pic, int player);
    }
}
