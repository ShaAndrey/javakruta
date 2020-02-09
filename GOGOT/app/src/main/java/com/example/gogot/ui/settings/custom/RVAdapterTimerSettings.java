package com.example.gogot.ui.settings.custom;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gogot.R;
import com.example.gogot.ui.settings.dialog.TimePickDialog;

import java.sql.Time;

public class RVAdapterTimerSettings extends
        RecyclerView.Adapter<RVAdapterTimerSettings.PlayerTimerViewHolder>
        implements TimePickDialog.TimePickDialogListener {
    private long[] timers;
    private Boolean isEqual;
    private Boolean timersOn;

    public RVAdapterTimerSettings(long[] timers, Boolean timersOn, Boolean isEqual) {
        super();
        this.timers = timers;
        this.isEqual = isEqual;
        this.timersOn = timersOn;
    }

    @NonNull
    @Override
    public PlayerTimerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.layout_timer, parent, false);
        return new RVAdapterTimerSettings.PlayerTimerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerTimerViewHolder holder, int position) {
        holder.playerTextView.setText(String.format(holder.playerTextView.getContext().
                getString(R.string.st_player), (position + 1)));
        holder.playerTimer.setCountDown(true);
        holder.playerTimer.setBase(SystemClock.elapsedRealtime() + timers[position]);
        if (timersOn && (!isEqual || position == 0)) {
            holder.playerTimer.setOnClickListener(v -> {
                TimePickDialog timePickDialog = new TimePickDialog
                        (holder.playerTimer.getContext(),
                                position, timers[position]);
                timePickDialog.setTimePickDialogListener(this);
                timePickDialog.show();
            });
        }
    }

    @Override
    public int getItemCount() {
        return timers.length;
    }

    @Override
    public void setTimer(int player, long time) {
        if (isEqual) {
            for (long timer : timers) {
                timer = time;
            }
        } else {
            timers[player] = time;
        }
        notifyDataSetChanged();
    }


    class PlayerTimerViewHolder extends RecyclerView.ViewHolder {
        Chronometer playerTimer;
        TextView playerTextView;

        PlayerTimerViewHolder(View itemView) {
            super(itemView);
            playerTimer = itemView.findViewById(R.id.player_timer);
            playerTextView = itemView.findViewById(R.id.text_player_timer);
        }
    }
}
