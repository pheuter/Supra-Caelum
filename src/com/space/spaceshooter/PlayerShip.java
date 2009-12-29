//testing from mark
package com.space.spaceshooter;


import android.view.KeyEvent;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;


class PlayerShip extends Ship
{
	public enum FireControls
	{
		Trackball,
		Tap
	}
	
	public enum MovementControls
	{
		Trackball,
		Accelerometer
	}
	
	private SensorManager mySensorManager;
	private MovementControls m_moveControls;
	public FireControls m_fireControls;
	private Bar m_overheatBar;
	private Bar m_healthBar;
	private Bar m_scoreBar;
	private float[] mOrientation = new float[3];
	public static int m_level = 1;
	public static int m_killsNeeded = 5;
	public static int shots_fired;
	
	
	private SensorEventListener sensorEventListener = new SensorEventListener() {
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
			
		}
		public void onSensorChanged(SensorEvent event) {
	        for (int i = 0; i < 3; i++)
	        {
	        	if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
	        		mOrientation[i] = -event.values[i];
	        }
			
		}		
	};
	
	public PlayerShip(int x, int y)
	{
		super(x,y);
		SetBorderBehavior(BorderBehavior.Stop);
				
		m_moveControls = MovementControls.Accelerometer; // m_moveControls = MoveMentControls.Accelerometer;
		m_fireControls = FireControls.Trackball; //m_fireControls = FireControls.Tap;
		
		mySensorManager = SpaceShooter.getManager();;
        if(m_moveControls == MovementControls.Accelerometer)
        	mySensorManager.registerListener(sensorEventListener, mySensorManager.getDefaultSensor(1), SensorManager.SENSOR_DELAY_GAME);
        else
        	Log.w("Sensor", "No need to initialize");

		m_overheatBar = new Bar();
		m_overheatBar.setVal(getOverheat(), maxOverheat);
		m_overheatBar.setRect(10, 25, 100, 10);
		m_overheatBar.setFrontColor(255, 255, 0);
		m_overheatBar.setBackColor(80, 80, 80);
		
		m_healthBar = new Bar();
		m_healthBar.setVal(getHealth(), getMaxHealth());
		m_healthBar.setRect(10, 10, 100, 10);
		m_healthBar.setFrontColor(255, 0, 0);
		m_healthBar.setBackColor(80, 80, 80);
		
		m_scoreBar = new Bar();
		m_scoreBar.setVal(ShipHandler.getKills(),5); //Start out of 5 kills
		m_scoreBar.setRect(getScreenSize().right-107,10,100,10);
		m_scoreBar.setFrontColor(0, 255, 255);
		m_scoreBar.setBackColor(80, 80, 80);
		
		shots_fired = 0;
	}
	
	@Override
	public void Draw()
	{
		super.Draw();
		m_overheatBar.draw();
		m_healthBar.draw();
		m_scoreBar.draw();
		
		Paint healthPaint = new Paint();
		Paint xPaint = new Paint();
		healthPaint.setARGB(255, 0, 255, 0);
		healthPaint.setTextSize(14);
		xPaint.setARGB(255, 255, 0, 0);
		xPaint.setTextSize(14);
		
		getCanvas().drawText(getHealth()+" / "+getMaxHealth(), getScreenSize().left+33, getScreenSize().top+20, healthPaint);
		getCanvas().drawText(ShipHandler.getKills()+" / "+m_killsNeeded, getScreenSize().right-69, getScreenSize().top+20, xPaint);
	}
	
	public void killSensor() //Call whenever sensor is no longer needed
	{
		if (m_moveControls == MovementControls.Accelerometer)
		{
			mySensorManager.unregisterListener(sensorEventListener);
			Log.w("Sensor", "Sensor Unregistered");
		}
		else
		{
			Log.w("Sensor", "Sensor isnt active!");
		}
	}
	
	@Override
	public void ProcInput(Input in)
	{
		//probably used only for debugging now that we have accelerometer
		if(m_moveControls == MovementControls.Trackball)
		{
			if(in.buttonDown(KeyEvent.KEYCODE_DPAD_LEFT))
				setMoveX(-10);
			else if(in.buttonDown(KeyEvent.KEYCODE_DPAD_RIGHT))
				setMoveX(10);
			else
				setMoveX(0);
		}
		if(m_moveControls == MovementControls.Accelerometer)
		{
			setMoveX((int)(mOrientation[0]*3));
		}
		
		if(m_fireControls == FireControls.Trackball)
		{
			if(in.buttonDown(KeyEvent.KEYCODE_DPAD_CENTER))
			{
				requestShot(-20);
			}
		}
		
		/*if(in.buttonDown(KeyEvent.KEYCODE_DPAD_DOWN)) //temporary method for killing sensor
		{
			this.killSensor();
		}*/
		
	}
	
	@Override
	public void Update()
	{
		super.Update();
		m_overheatBar.setVal(getOverheat());
		m_healthBar.setVal(getHealth());
		m_scoreBar.setVal(ShipHandler.getKills());
		//if(ShipHandler.getKills() == m_killsNeeded)
		//	levelUp();
		/*
		switch (m_level) //Very very simple implementation of leveling
		{
			default:
				break;
			case 0:
				if (ShipHandler.getKills() == 5)
				{
					m_scoreBar.setVal(0,10);
					setCooling(15);
					setMaxHealth(150);
					m_healthBar.setMax(150);
					m_level = 1;
					ShipHandler.resetKills();
					m_level = 1;
				}
				break;
			case 1:
				if (ShipHandler.getKills() == 10)
				{
					m_scoreBar.setVal(0,5);
					setCooling(20);
					setMaxHealth(200);
					m_healthBar.setMax(200);
					m_level = 0;
					ShipHandler.resetKills();
					m_level = 0;
				}
				break;
		}
		*/
	}
	
	@Override
	public boolean requestShot(int speed) {
		if(super.requestShot(speed)) 
		{
			shots_fired++; 
			return true;
		} 
		return false;
	}

	public int getKillsNeeded()
	{
		return m_killsNeeded;
	}
	
	public void levelUp()
	{
		m_level++;
		m_killsNeeded += 5;
		incCooling(5);
		incMaxHealth(50);
		setHealthFull();
		m_scoreBar.setVal(0, m_killsNeeded);
		m_healthBar.setMax(getMaxHealth());
	}

	public static void setM_level(int m_level) {
		PlayerShip.m_level = m_level;
	}

	public static void setShots_fired(int shots_fired) {
		PlayerShip.shots_fired = shots_fired;
	}

	public static int getM_level() {
		return m_level;
	}

	public static int getShots_fired() {
		return shots_fired;
	}
	
	
}