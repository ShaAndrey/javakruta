package com.kazakovproduction.gogot.ui.settings.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;

import com.kazakovproduction.gogot.R;
import com.kazakovproduction.gogot.model.settings.FileReaderWriter;
import com.kazakovproduction.gogot.model.settings.Sounds;
import com.kazakovproduction.gogot.model.settings.gallery.PlayerPictures;
import com.kazakovproduction.gogot.relation.settings.SettingsMainContract;
import com.kazakovproduction.gogot.relation.settings.SettingsPresenter;
import com.kazakovproduction.gogot.ui.settings.custom.RVAdapterPlayers;
import com.kazakovproduction.gogot.ui.settings.custom.RVAdapterTimerSettings;

public class SettingsActivity extends AppCompatActivity
        implements SettingsMainContract.SettingsView,
        RVAdapterPlayers.RVAdapterPlayersListener {

    public static final String PLAYER_PICS_TXT = "player_pics.txt";
    public static final String MUSIC_TXT = "music.txt";
    private SettingsPresenter presenter;
    private RVAdapterTimerSettings rvAdapterTimerSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_settings);

        Button donateButton = findViewById(R.id.buttonDonate);
        donateButton.setOnClickListener(v -> onDonateButton());
        Button okButton = findViewById(R.id.ok_button);
        okButton.setOnClickListener(v -> onBackPressed());

        presenter = new SettingsPresenter(this);
        presenter.setPlayersTable();
        int[] def = {0, 1, 2};
        PlayerPictures.loadPictures(FileReaderWriter.
                readPlacesFile(getApplicationContext(),
                        PLAYER_PICS_TXT, def));
        presenter.setTimers();
        setCheckBoxes();
    }

    void setCheckBoxes() {
        CheckBox enableTimers = findViewById(R.id.check_box_is_timer_set);
        enableTimers.setOnClickListener(v -> {
            presenter.setTimersOn(enableTimers.isChecked());
            rvAdapterTimerSettings.notifyDataSetChanged();
        });
        CheckBox isEqual = findViewById(R.id.check_box_equal_time);
        isEqual.setOnClickListener(v -> {
            presenter.setTimersEqual(isEqual.isChecked());
            rvAdapterTimerSettings.notifyDataSetChanged();
        });
        CheckBox musicCheckBox = findViewById(R.id.check_box_music);
        int[] def = {1};
        if (FileReaderWriter.
                readPlacesFile(getApplicationContext(),
                        MUSIC_TXT, def)[0] == 1) {
            Sounds.setIsMusikOn(true);
        } else {
            Sounds.setIsMusikOn(false);
        }
        musicCheckBox.setChecked(Sounds.getIsMusicOn());
        musicCheckBox.setOnClickListener(v -> {
            presenter.switchMusic(musicCheckBox.isChecked());
            if(musicCheckBox.isChecked()) {
                final int[] deff = {1};
                FileReaderWriter.writeToFile(SettingsActivity.this, MUSIC_TXT, deff);
            } else {
                final int[] deff = {0};
                FileReaderWriter.writeToFile(SettingsActivity.this, MUSIC_TXT, deff);
            }
        });
    }

    void onDonateButton() {
        goToUrl("https://money.yandex.ru/quickpay/shop-widget?writer=seller&targets=%D0%9F%D0%BE%D0%BC%D0%BE%D1%89%D1%8C%20%D1%80%D0%B0%D0%B7%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D1%87%D0%B8%D0%BA%D0%B0%D0%BC%20GOGOT%20%D0%B2%20%D0%BF%D1%80%D0%BE%D0%B4%D0%B2%D0%B8%D0%B6%D0%B5%D0%BD%D0%B8%D0%B8%20%D1%81%D0%B2%D0%BE%D0%B5%D0%B3%D0%BE%20%D0%BF%D1%80%D0%BE%D0%B5%D0%BA%D1%82%D0%B0&targets-hint=&default-sum=&button-text=11&payment-type-choice=on&hint=&successURL=&quickpay=shop&account=4100110379954126");
    }

    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    @Override
    public void setPlayersTable(int[] pictures, int[] playersPictures) {
        RecyclerView playersTable = findViewById(R.id.players_pictures);
        playersTable.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this, 3) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                };
        playersTable.setLayoutManager(gridLayoutManager);
        RVAdapterPlayers adapter = new RVAdapterPlayers(pictures,
                playersPictures);
        adapter.setListener(this);
        playersTable.setAdapter(adapter);
    }

    @Override
    public void saveConfig(int[] playerPictures) {
        FileReaderWriter.writeToFile(getApplicationContext(),
                PLAYER_PICS_TXT, playerPictures);
    }

    @Override
    public void setTimers(long[] timers, Boolean timersOn, Boolean isEqual) {
        RecyclerView playerTimers = findViewById(R.id.players_timers);
        playerTimers.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this, 3) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                };
        playerTimers.setLayoutManager(gridLayoutManager);
        rvAdapterTimerSettings = new
                RVAdapterTimerSettings(timers, timersOn, isEqual);
        playerTimers.setAdapter(rvAdapterTimerSettings);
    }

    @Override
    public void setPictureToPlayer(int pic, int player) {
        presenter.setPictureToPlayer(pic, player);
    }

}