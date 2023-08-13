package com.example.mediaplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaExtractor;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.os.Build;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
private Button play;
private Button pause;
private SeekBar SeekBar;
private SurfaceView surfaceview;
private Button vidplay; private MediaPlayer mp1;
    private MediaPlayer mp2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        MediaPlayer mp=MediaPlayer.create(this,R.raw.nashemusic);
        MediaPlayer mp2=MediaPlayer.create(this,R.raw.vid);
        play=findViewById(R.id.play);
        pause=findViewById(R.id.pause);
        SeekBar=findViewById(R.id.SeekBar);
        surfaceview=findViewById(R.id.surfaceview);
        vidplay=findViewById(R.id.vidplay);
        surfaceview.setKeepScreenOn(true);
        SurfaceHolder surfaceholder= surfaceview.getHolder();
        surfaceholder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                mp2.setDisplay(surfaceholder);
                mp2.start();

//                mp2.start();
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

            }
        });


        vidplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mp2.isPlaying()){
                    mp2.pause();
                    vidplay.setText("Play");
                }
                else {
                    mp2.start();
                    vidplay.setText("Pause");
                }
            }
        });
//        mp2.prepareAsync();
        MediaPlayer mp1 = new MediaPlayer();
        try {
            mp1.setDataSource("https://ai.stanford.edu/~bangpeng/download/music/rmn/LinkonPark%20-%20InTheEnd.mp3");
            mp1.prepare(); // prepare the media player
            mp1.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mp1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                // add any other code that needs to be executed after the media player is prepared
            }
        });

//        mp1.prepareAsync();
        mp2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
              mp.start();
                mp2.setLooping(true);
                mp2.setVolume(0.4f,0.4f);
                SeekBar.setMax(mp2.getDuration());
                int length=mp2.getCurrentPosition();
                
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mp2.setPlaybackParams(new PlaybackParams().setSpeed(1.1f));
                }
                SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(android.widget.SeekBar seekBar, int progress, boolean fromUser) {
                        if(fromUser) {
                            mp2.seekTo(progress);
                            mp2.start();

                        }
                    }

                    @Override
                    public void onStartTrackingTouch(android.widget.SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(android.widget.SeekBar seekBar) {

                    }
                });
            }
        });
//mp2.prepareAsync();
        mp1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp1.start();

            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp1.start();
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp1.pause();
            }
        });

    }
}