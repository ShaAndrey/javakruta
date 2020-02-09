package com.example.gogot.ui.settings.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.gogot.R;


public class TimePickDialog extends Dialog {
    private int player;
    private long time;
    private Context context;
    private TimePickDialogListener listener;

    public TimePickDialog(@NonNull Context context, int player, long time) {
        super(context);
        this.context = context;
        this.player = player;
        this.time = time;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_timer_dialog);
        TextView dialogTextView = findViewById(R.id.choice_pic_textview);
        dialogTextView.setText(String.format(context.getString
                        (R.string.choose_picture_for_player),
                1 + player));
        initDialog();
    }

    private void initDialog() {
        NumberPicker minutes = findViewById(R.id.timer_min);
        minutes.setMaxValue(7);
        minutes.setMinValue(0);
        minutes.setValue((int) (time / 60000));
        NumberPicker seconds = findViewById(R.id.timer_sec);
        minutes.setMaxValue(59);
        minutes.setMinValue(0);
        minutes.setValue((int) (time % 60000));

        Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(v -> this.dismiss());

        Button okButton = findViewById(R.id.ok_button);
        okButton.setOnClickListener(v -> {
            time = ((long) (minutes.getValue()) * 60 +
                    (long) (seconds.getValue())) * 1000;
            listener.setTimer(player, time);
            this.dismiss();
        });
    }

    public void setTimePickDialogListener(TimePickDialogListener listener) {
        this.listener = listener;
    }


    public interface TimePickDialogListener {
        void setTimer(int player, long time);
    }
}
