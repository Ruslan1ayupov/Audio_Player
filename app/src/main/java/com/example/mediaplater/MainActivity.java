package com.example.mediaplater;
import android.os.Handler;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    Button Start, Pause, Stop;
    SeekBar seekBar;
    private double startTime = 0;
    private double finalTime = 0;
    private TextView tx1, tx2, tx3;
    private Handler myHandler = new Handler();
    private int forwardTime = 5000;
    public static int oneTimeOnly = 0;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Start = (Button) findViewById(R.id.bStart);
            Pause = (Button) findViewById(R.id.bPause);
            Stop = (Button) findViewById(R.id.bStop);
            final MediaPlayer mp = MediaPlayer.create(MainActivity.this, R.raw.im_so_sorry);

            tx1 = (TextView) findViewById(R.id.text1);
            tx2 = (TextView) findViewById(R.id.text2);
            tx3 = (TextView) findViewById(R.id.text3);

            seekBar = (SeekBar) findViewById(R.id.sBarProgress);
            seekBar.setMax(mp.getDuration());
            seekBar.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    seekChange(v);
                    return false;
                }

                private void seekChange(View v) {
                    if (mp.isPlaying()) {
                        SeekBar sb = (SeekBar) v;
                        mp.seekTo(sb.getProgress());
                    }
                }
            });
            seekBar.setProgress(0);
            final Runnable UpdateSongTime = new Runnable() {
                public void run() {
                    startTime = mp.getCurrentPosition();
                    seekBar.setProgress((int) startTime);
                    myHandler.postDelayed(this, 100);

                }
            };
            Start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mp.start();
                    seekBar.setProgress((int) startTime);
                    myHandler.postDelayed(UpdateSongTime, 100);
                }
            });
            Pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        mp.pause();

                }
            });
            Stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mp.stop();
                    MainActivity.this.finish();
                }
            });
        }
}


