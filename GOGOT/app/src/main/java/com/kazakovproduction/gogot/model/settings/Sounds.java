package com.kazakovproduction.gogot.model.settings;

import android.content.Context;
import android.media.MediaPlayer;

import com.kazakovproduction.gogot.R;

public class Sounds {
    private static boolean isMusicOn = true;
    private MediaPlayer mp;

    public static boolean getIsMusicOn() {
        return isMusicOn;
    }

    public static void setIsMusikOn(boolean isMusikOn) {
        Sounds.isMusicOn = isMusikOn;
    }

    public void playGameMusik(Context context) {
        mp = MediaPlayer.create(context,
                R.raw.background_music_game);
        mp.setLooping(true);
        if (!isMusicOn) {
            return;
        }
        mp.start();
    }

    public void onPause() {
        if (!isMusicOn) {
            return;
        }
        mp.pause();
    }

    public void onResume() {
        if (!isMusicOn) {
            return;
        }
        mp.start();
    }

    public void playSettingsMusic(Context context) {
        mp = MediaPlayer.create(context,
                R.raw.settings_music);
        mp.setLooping(true);
        if (!isMusicOn) {
            return;
        }
        mp.start();
    }
}
