package com.example.gogot.ui.settings.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import com.example.gogot.R;
import com.example.gogot.model.settings.FileReaderWriter;
import com.example.gogot.model.settings.gallery.PlayerPictures;
import com.example.gogot.relation.settings.SettingsMainContract;
import com.example.gogot.relation.settings.SettingsPresenter;
import com.example.gogot.ui.settings.custom.RVAdapterPlayers;

public class SettingsActivity extends AppCompatActivity
        implements SettingsMainContract.SettingsView,
        RVAdapterPlayers.RVAdapterPlayersListener {

    public static final String FILE_NAME = "player_pics.txt";
    private SettingsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Button donateButton = findViewById(R.id.buttonDonate);
        donateButton.setOnClickListener(v -> onDonateButton());
        presenter = new SettingsPresenter(this);
        presenter.setPlayersTable();
        PlayerPictures.loadPictures(FileReaderWriter.
                readFile(getApplicationContext(),
                        FILE_NAME));
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
        RecyclerView resultsTable = findViewById(R.id.players_pictures);
        resultsTable.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this, 3) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                };
        resultsTable.setLayoutManager(gridLayoutManager);
        RVAdapterPlayers adapter = new RVAdapterPlayers(pictures,
                playersPictures);
        adapter.setListener(this);
        resultsTable.setAdapter(adapter);
    }

    @Override
    public void saveConfig(int[] playerPictures) {
        FileReaderWriter.writeToFile(getApplicationContext(),
                FILE_NAME,
                playerPictures);
    }

    @Override
    public void setPictureToPlayer(int pic, int player) {
        presenter.setPictureToPlayer(pic, player);
    }

}