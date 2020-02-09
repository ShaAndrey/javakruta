package com.example.gogot.ui.settings.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.gogot.R;
import com.example.gogot.model.settings.FileReaderWriter;
import com.example.gogot.model.settings.Sounds;
import com.example.gogot.model.settings.gallery.PlayerPictures;
import com.example.gogot.relation.settings.SettingsMainContract;
import com.example.gogot.relation.settings.SettingsPresenter;
import com.example.gogot.ui.game.activity.MainActivity;
import com.example.gogot.ui.settings.custom.RVAdapterPlayers;
import com.example.gogot.ui.settings.custom.RVAdapterTimerSettings;

import java.util.Set;

public class SettingsActivity extends AppCompatActivity
        implements SettingsMainContract.SettingsView,
        RVAdapterPlayers.RVAdapterPlayersListener {

    public static final String FILE_NAME = "player_pics.txt";
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
        PlayerPictures.loadPictures(FileReaderWriter.
                readPlacesFile(getApplicationContext(),
                        FILE_NAME));

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
        musicCheckBox.setChecked(Sounds.getIsMusicOn());
        musicCheckBox.setOnClickListener(v ->
                presenter.switchMusic(musicCheckBox.isChecked()));
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
                FILE_NAME, playerPictures);
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