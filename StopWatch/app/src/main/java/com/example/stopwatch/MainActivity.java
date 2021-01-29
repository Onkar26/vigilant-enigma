package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private int sec = 0;

    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState != null){
            sec = savedInstanceState.getInt("sec");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }

    // Save the state of the stopwatch if it's about to be destroyed
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("sec", sec);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    // Activity will pause if it is not in foreground
    @Override
    protected void onPause(){
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(wasRunning) running = true;
    }

    public void start(View view){
        running = true;
    }

    public void stop(View view){
        running = false;
    }

    public void reset(View view){
        sec = 0;
        running = false;
    }

    private void runTimer(){
        TextView time = findViewById(R.id.textView);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int h = sec/3600;
                int m = (sec%3600)/60;
                int s = sec%60;
                String newText = String.format(Locale.getDefault(),"%d:%02d:%02d", h, m, s);
                time.setText(newText);
                if(running) sec++;
                handler.postDelayed(this, 1000);
            }
        });
    }

}