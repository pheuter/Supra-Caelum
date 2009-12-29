package com.space.spaceshooter;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

public class SoundPoolSoundManager {

     private boolean enabled = true;
     private Context context;
     private SoundPool soundPool;
     private HashMap<Integer, Integer> soundPoolMap;

     public SoundPoolSoundManager(Context context) {
             this.context = context;
     }

     public void reInit() {
             init();
     }

     public void init() {
             if (enabled) {
                     Log.d("SoundPool", "Initializing new SoundPool");
                     release();
                     soundPool = new SoundPool(5,AudioManager.STREAM_MUSIC, 100);
                     soundPoolMap = new HashMap<Integer, Integer>();
                     soundPoolMap.put(1, soundPool.load(context, R.raw.e_hit,1));
                     soundPoolMap.put(2, soundPool.load(context, R.raw.explod,2));
                     Log.d("SoundPool", "SoundPool initialized");                    
             }
     }

     public void release() {
             if (soundPool != null) {
                     Log.d("SoundPool", "Closing SoundPool");
                     soundPool.release();
                     soundPool = null;
                     Log.d("SoundPool", "SoundPool closed");
                     return;
             }
     }

     public void playSound(int sound) {
             if (soundPool != null) {
                     Log.d("SoundPool", "Playing Sound " + sound);
                     AudioManager mgr = (AudioManager)
                     context.getSystemService(Context.AUDIO_SERVICE);
                     int streamVolume = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
                     Integer soundId = soundPoolMap.get(sound);
                     if (soundId != null) {
                             soundPool.play(soundPoolMap.get(sound), streamVolume,streamVolume, 1, 0, 1f);
                     }
             }
     }

     public void setEnabled(boolean enabled) {
             this.enabled = enabled;
     }

}
