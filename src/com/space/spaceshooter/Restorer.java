/*
 * A medium for gather time-sensitive values and saving them to a bundle
 * This will allow player to resume volatile stats after main Activity is re-opened after interruption.
 */

package com.space.spaceshooter;

import android.os.Bundle;

public class Restorer { 
	private Bundle myBundle;
	private static int currentLevel; //m_level - PlayerShip
	private static int healthPoints; //m_health - Ship
	private static int maxHealthPoints; //m_maxHealth - Ship
	private static int currentKills; //m_kills - ShipHandler
	private static int killsNeeded; //m_killsNeeded - PlayerShip
	private static int playerCooldown; //m_cooling - Ship
	private static int enemySpeed; //m_enemySpeed - ShipHandler
	private static int enemyCooldown; //m_enemyCooling - ShipHandler
	
	public Restorer()
	{
		myBundle = new Bundle();
	}
	
	public void putBundle()
	{
		myBundle.putInt("m_level", currentLevel);
		myBundle.putInt("m_health", healthPoints);
		myBundle.putInt("m_maxHealth", maxHealthPoints);
		myBundle.putInt("m_kills", currentKills);
		myBundle.putInt("m_killsNeeded", killsNeeded);
		myBundle.putInt("m_cooling", playerCooldown);
		myBundle.putInt("m_enemySpeed", enemySpeed);
		myBundle.putInt("m_enemyCooling", enemyCooldown);
	}
	
	public Bundle getBundle()
	{
		return myBundle;
	}
	
	public static void setCurrentLevel(int cL)
	{
		currentLevel = cL;
	}
	
	public static void setHealthPoints(int hP)
	{
		healthPoints = hP;
	}
	
	public static void setMaxHealthPoints(int mHP)
	{
		maxHealthPoints = mHP;
	}
	
	public static void setCurrentKills(int cK)
	{
		currentKills = cK;
	}
	
	public static void setKillsNeeded(int kN)
	{
		killsNeeded = kN;
	}
	
	public static void setPlayerCooldown(int pC)
	{
		playerCooldown = pC;
	}
	
	public static void setEnemySpeed(int eS)
	{
		enemySpeed = eS;
	}
	
	public static void setEnemyCooldown(int eC)
	{
		enemyCooldown = eC;
	}
	
}
