package com.space.spaceshooter;

import java.util.Vector;

class Weapon<E>
{
	private Vector<E> m_shots = new Vector<E>();
	private int m_speed;
	protected final int maxOverheat = 100;
	private int m_overheat;
	
	public boolean fire()
	{
		if(m_overheat > 0)
			return false;
		//issue... projectile class?
		/*PlasmaShot temp = new PlasmaShot(GetX()+GetWidth()/2,GetY(),speed);
		temp.SetImage(R.drawable.plasma);
		m_shots.add(temp);
		m_overheat = maxOverheat;*/
		return true;
	}
	
	public int getOverheat()
	{
		return m_overheat;
	}
	
	public void setSpeed(int speed)
	{
		m_speed = speed;
	}
	
	public int getSpeed()
	{
		return m_speed;
	}
	
	public Vector<E> getVector()
	{
		return m_shots;
	}
}