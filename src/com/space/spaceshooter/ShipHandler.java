package com.space.spaceshooter;

import java.util.Vector;
import java.util.Random;


class ShipHandler
{
	Vector<EnemyShip> m_ships;
	private static int m_kills;//try to make non-static later
	PlayerShip m_pShip;
	private int m_enemySpeed = 4;
	private int m_enemyCooling = 5;
	public static int total_kills;
	
	public ShipHandler()
	{
		m_ships = new Vector<EnemyShip>();
		m_kills = 0;
		total_kills = 0;
	}
	
	public void setPlayer(PlayerShip ps)//gars... ugly
	{
		m_pShip = ps;
	}
	
	public void requestEnemy(int x, int y)
	{
		EnemyShip temp = new EnemyShip(x,y);
		temp.setSpeed(m_enemySpeed);
		temp.setCooling(m_enemyCooling);
    	temp.SetBorders(0, Sprite.getScreenSize().bottom - 200, 0, Sprite.getScreenSize().right); 
    	temp.SetImage(R.drawable.eship_trans);
		m_ships.add(temp);
	}
	
	public void update()
	{
		m_pShip.Update();
		if(m_kills >= m_pShip.getKillsNeeded()) //changed to static reference cause variable is now static for Restorer
		{
			resetKills();
			m_pShip.levelUp();
			m_enemySpeed += 2;
			m_enemyCooling += 3;
		}
		for(int i = 0;i < m_ships.size();i++)
		{
			m_ships.elementAt(i).Update();
			if(m_ships.elementAt(i).isDead() && m_ships.elementAt(i).getShots().size() == 0)
			{
				m_ships.remove(i);
				total_kills++;
				setKills(1);
				Random r = new Random();
				requestEnemy(r.nextInt(Sprite.getScreenSize().right-33),-100);//kinda ugly, taking size right out of file...
			}
		}
	}
	
	public void draw()
	{
		m_pShip.Draw();
		for(int i = 0;i < m_ships.size();i++)
		{
			m_ships.elementAt(i).Draw();
		}
	}
	
	public Vector<EnemyShip> getVector()
	{
		return m_ships;
	}
	
	public static void setKills(int amt)
	{
		 m_kills += amt;
	}
	
	public static void resetKills()
	{
		m_kills = 0;
	}
	
	public static int getKills()
	{
		return m_kills;
	}

	public static int getTotal_kills() {
		return total_kills;
	}

	public static void setTotal_kills(int total_kills) {
		ShipHandler.total_kills = total_kills;
	}
	
}