package com.example.pr12;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends Activity {

    MediaPlayer mediaPlayer;
    Button play, pause, stop;
    SeekBar volume;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer=MediaPlayer.create(this, R.raw.music);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlay();
            }
        });
        play=findViewById(R.id.play);
        pause=findViewById(R.id.pause);
        stop=findViewById(R.id.stop);

        audioManager=(AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume=audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curValue=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        volume=findViewById(R.id.volume);
        volume.setMax(maxVolume);
        volume.setProgress(curValue);
        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        pause.setEnabled(false);
        stop.setEnabled(false);
    }

    public void stopPlay() {
        mediaPlayer.stop();
        pause.setEnabled(false);
        stop.setEnabled(false);
        try {
            mediaPlayer.prepare();
            mediaPlayer.seekTo(0);
            play.setEnabled(true);
        }
        catch (Throwable throwable) {
            Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void play(View view) {
        mediaPlayer.start();
        play.setEnabled(false);
        pause.setEnabled(true);
        stop.setEnabled(true);
    }

    public void pause(View view) {
        mediaPlayer.pause();
        play.setEnabled(true);
        pause.setEnabled(false);
        stop.setEnabled(true);
    }

    public void stop(View view) {
        stopPlay();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer.isPlaying()){
            stopPlay();
        }
    }
}