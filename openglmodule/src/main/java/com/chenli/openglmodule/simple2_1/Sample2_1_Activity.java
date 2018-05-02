package com.chenli.openglmodule.simple2_1;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;

import com.chenli.commonlib.util.mainutil.LogUtils;
import com.chenli.openglmodule.R;

/**
 * Created by Lenovo on 2018/5/2.
 */

public class Sample2_1_Activity extends AppCompatActivity {

    private SoundPool soundPool;
    private SparseArray<Integer> soundsMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample2_1_activity);
        initSoundPool();

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound();
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopSound();
            }
        });
    }

    private void stopSound() {
        soundPool.pause(soundsMap.get(2));
    }

    private void playSound() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int streamMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        LogUtils.e("chenli","streamVolume = " + streamVolume + " , streamMaxVolume = " + streamMaxVolume);
        int streamId = soundPool.play(soundsMap.get(1), streamVolume, streamVolume, 1, 0, 1.0f);
        soundsMap.put(2,streamId);
    }

    private void initSoundPool() {
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC,0);
        int soundId = soundPool.load(this, R.raw.musictest, 1);
        soundsMap = new SparseArray(2);
        soundsMap.put(1,soundId);
    }
}
