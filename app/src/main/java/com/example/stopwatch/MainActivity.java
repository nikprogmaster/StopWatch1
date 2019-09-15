
package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private int santiSeconds = 0;
    private boolean running = false;
    private boolean wasrunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
            setContentView(R.layout.activity_main);
        else
            setContentView(R.layout.landscape);
        if (savedInstanceState!=null) {
            santiSeconds = savedInstanceState.getInt("sSs");
            running = savedInstanceState.getBoolean("run");
            wasrunning = savedInstanceState.getBoolean("wasrun");
        }
        runTimer();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("sSs", santiSeconds);
        savedInstanceState.putBoolean("run", running);
        savedInstanceState.putBoolean("wasrun", wasrunning);
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasrunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasrunning)
        running = true;
    }

    public void onClickStart (View view) {
        running = true;
    }

    public void onClickStop (View view) {
        running = false;
    }

    public void onClickReset (View view) {
        running = false;
        santiSeconds = 0;
    }

    private void runTimer() {
        final TextView timeView = findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = santiSeconds/360000;
                int minutes = (santiSeconds%360000)/6000;
                int secs = ((santiSeconds%360000)%6000)/100;
                int santiSecs = ((santiSeconds%360000)%6000)%100;

                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", minutes, secs, santiSecs);
                timeView.setText(time);
                if (running)
                    santiSeconds++;
                handler.postDelayed(this, 10);
            }
        });

    }
}
