//testing subversion

package com.space.spaceshooter;


import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.hardware.SensorManager; 

import com.space.spaceshooter.SpaceView.SpaceThread;


public class SpaceShooter extends Activity {

	private static SpaceView mSpaceView;	
	private static SensorManager mSensorManager;
	private static SoundPoolSoundManager mySound;
    private SpaceThread mSpaceThread;   
	private PowerManager powerManager;
	private PowerManager.WakeLock w1;
    
    private static final int MENU_START = Menu.FIRST;
    private static final int MENU_RESUME = Menu.FIRST+1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_START, 0, R.string.menu_start);
        menu.add(0, MENU_RESUME, 0, R.string.menu_resume);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case MENU_START:
            mSpaceThread.doStart();
            return true;
        case MENU_RESUME:
            mSpaceThread.unpause();
            return true;
        }

        return false;
    }
    public void onConfigurationChanged(Configuration newConfig) //dont mess up when keyboard slides open
	{
		super.onConfigurationChanged(newConfig);
	}
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);      
        mSpaceView = (SpaceView) findViewById(R.id.space);
        mSpaceThread = mSpaceView.getThread();
        
        mySound = new SoundPoolSoundManager(getApplicationContext());
        mySound.init();
       
      	powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
      	w1 = powerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "Partial Power");
      	w1.acquire();
        
      	mSpaceThread.setState(SpaceThread.STATE_READY);
          
      	if (savedInstanceState != null)
      	{
      		mSpaceThread.restoreState(savedInstanceState);
      		Log.w("State","restoring state from mSpaceThread... restoring volatile values");
      	}
        
    }
    
    public static SoundPoolSoundManager getSound()
    {
    	return mySound;
    }
    
    public static SensorManager getManager()
    {
    	return mSensorManager;
    }
    
    public static SpaceView getSpaceView()
    {
    	return mSpaceView;
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        mSpaceView.getThread().pause();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // just have the View's thread save its state into our Bundle
        super.onSaveInstanceState(outState);
    	//mSpaceThread.saveState(outState);
        //Log.w(this.getClass().getName(), "save instance state called...saving volatile values");
        w1.release();
        mySound.release();
    }
}