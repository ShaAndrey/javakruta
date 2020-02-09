package com.example.gogot.relation.settings;

public interface SettingsMainContract {
    interface SettingsView {
        void setPlayersTable(int[] pictures, int[] playersPictures);

        void saveConfig(int[] playerPictures);

        void setTimers(long[] timers, Boolean timersOn, Boolean isEqual);
    }

    interface SettingsPresenter {
        void setPlayersTable();

        void setPictureToPlayer(int pic, int player);

        void setTimers();

        void setTimersOn(boolean checked);

        void setTimersEqual(boolean checked);
    }

    interface SettingsModel {
        int[] getPictures();

        int[] getPlayerPictures();

        void setPictureToPlayer(int pic, int player);

        long[] getTimers();

        Boolean getTimersState();

        Boolean getIsTimersEqual();

        void setTimersOn(boolean checked);

        void setTimersEqual(boolean checked);
    }
}
