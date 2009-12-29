package com.space.spaceshooter;

import android.util.Log;

//import java.util.Vector;

class PlasmaShot extends Sprite
{
	private int m_speed;
	private boolean m_destroy = false;
	private static ShipHandler m_eHandler;
	private static PlayerShip m_pShip;
	public static int shots_hit;
	
	public static void setShipHandler(ShipHandler eh)
	{
		m_eHandler = eh;
	}
	
	public static void setPlayer(PlayerShip ps)
	{
		m_pShip = ps;
	}
	
	public PlasmaShot(int x, int y, int speed)
	{
		SetPosition(x,y);
		m_speed = speed;
		
		SetBorderBehavior(BorderBehavior.Custom);
		SetBorders(Sprite.getScreenSize());
		
	}
	
	public void CustomBorderBehavior(int x_outOfBounds, int y_outOfBounds)
	{
		if(y_outOfBounds != 0 && y_outOfBounds - GetHeight() != 0)
		{
			m_destroy = true;
		}
	}
	
	public void Update()
	{
		super.Update();
		SetY(GetY() + m_speed);
	}
	
	public boolean toDestroy()
	{
		return m_destroy;
	}
	
	public void checkCollisions(Ship parent)
	{
		for(int i = 0;i < m_eHandler.getVector().size();i++)
		{
			EnemyShip temp = m_eHandler.getVector().elementAt(i);
			if(temp == parent)
				continue;
			if(temp.collides(this))
			{
				SpaceShooter.getSound().playSound(1);
				shots_hit++;
				Log.w("Collision","Vector/EShip collision");
				temp.getDamaged(10);
				m_destroy = true;
			}
		}
		if(parent != m_pShip)
			if(m_pShip.collides(this))
			{
				SpaceShooter.getSound().playSound(2);
				Log.w("Collision","Vector/PShip collision");
				m_pShip.getDamaged(10);
				m_destroy = true;
			}
	}

	public static int getShots_hit() {
		return shots_hit;
	}

	public static void setShots_hit(int shots_hit) {
		PlasmaShot.shots_hit = shots_hit;
	}
};