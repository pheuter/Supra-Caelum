package com.space.spaceshooter;

import java.util.Random;

public class Star extends Sprite
{
	private int m_speed;
	
	public Star(int x, int y, int speed)
	{
		super(x, y);
		SetBorderBehavior(BorderBehavior.Custom);
		m_speed = speed;
	}
	
	public void SetSpeed(int speed)
	{
		m_speed = speed;
	}
	
	public int GetSpeed()
	{
		return m_speed;
	}
	
	@Override
	protected void CustomBorderBehavior(int x_outOfBounds, int y_outOfBounds)
	{
		if(y_outOfBounds > GetHeight())
		{
			Random r = new Random();
			SetY(GetBorders().top - 10);
			SetX(r.nextInt(GetBorders().right) + GetBorders().left);
		}
	}
	
	@Override
	public void Update()
	{
		super.Update();
		SetY(GetY() + m_speed);
		//BorderCheck();
	}
}