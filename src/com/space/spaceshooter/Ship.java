package com.space.spaceshooter;

import java.util.Vector;

abstract class Ship extends Sprite
{
	private Vector<PlasmaShot> m_shots;
	private int m_moveX;
	private int m_moveY;
	private boolean m_dead = false;
	
	private int m_overheat = 0;
	protected final int maxOverheat = 100;
	private int m_cooling = 10;
	private int m_maxHealth = 100;
	private int m_health = m_maxHealth;
	
	public Ship(int x, int y)
	{
		super(x,y);
		m_shots = new Vector<PlasmaShot>();
	}
	
	public void setHealthFull()
	{
		m_health = m_maxHealth;
	}
	
	public void setMaxHealth(int max)
	{
		m_maxHealth = max;
		//m_health = m_maxHealth;
	}
	
	public int getMaxHealth()
	{
		return m_maxHealth;
	}
	
	public void incMaxHealth(int inc)
	{
		m_maxHealth += inc;
	}
	
	public void setCooling(int cool)
	{
		m_cooling = cool;
	}
	
	public void incCooling(int inc)
	{
		m_cooling += inc;
	}
	
	public void Update()
	{
		for(int i = 0;i < m_shots.size();i++)
		{
			m_shots.elementAt(i).Update();
			m_shots.elementAt(i).checkCollisions(this);
			if(m_shots.elementAt(i).toDestroy())
			{
				m_shots.remove(i);
			}	
		}
		if(m_dead)
			return;
		super.Update();
		SetX(GetX() + m_moveX);
		SetY(GetY() + m_moveY);
		if(m_overheat > 0)
		{
			m_overheat -= m_cooling;
			if(m_overheat < 0)
				m_overheat = 0;
		}
		
		if(m_health <= 0)
			m_dead = true;
		
	}
	
	public void Draw()
	{
		for(int i = 0;i < m_shots.size();i++)
			m_shots.elementAt(i).Draw();
		if(!m_dead)
			super.Draw();
		
	}
	
	public void getDamaged(int dmg)
	{
		m_health -= dmg;
		if(m_health <= 0)
			m_health = 0;
	}
	
	public boolean requestShot(int speed)
	{
		if(m_overheat > 0)
			return false;
		else {
			PlasmaShot temp = new PlasmaShot(GetX()+GetWidth()/2,GetY(),speed);
			temp.SetImage(R.drawable.plasma);
			m_shots.add(temp);
			m_overheat = maxOverheat;
			return true;
		}
	}
	
	public void setMoveX(int movex)
	{
		m_moveX = movex;
	}
	
	public void setMoveY(int movey)
	{
		m_moveY = movey;
	}
	
	public int getMoveX()
	{
		return m_moveX;
	}
	
	public int getMoveY()
	{
		return m_moveY;
	}
	
	public int getOverheat()
	{
		return m_overheat;
	}
	
	public int getHealth()
	{
		return m_health;
	}
	
	public boolean isDead()
	{
		return m_dead;
	}
	
	public Vector<PlasmaShot> getShots()
	{
		return m_shots;
	}
}