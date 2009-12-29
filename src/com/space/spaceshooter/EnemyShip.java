package com.space.spaceshooter;

class EnemyShip extends Ship
{
	Bar m_healthBar;
	
	public EnemyShip(int x, int y)
	{
		super(x,y);
		SetBorderBehavior(BorderBehavior.Custom);
		setMoveX(1);
		setMoveY(1);
		m_healthBar = new Bar();
		m_healthBar.setBackColor(50, 50, 50);
		m_healthBar.setFrontColor(255, 0, 0);
		m_healthBar.setRect(GetX(), GetY()-30,50,10);
		m_healthBar.setVal(getHealth(),100);
	}
	
	public void setSpeed(int speed)
	{
		setMoveX(-speed);
	}
	
	public void Update()
	{
		super.Update();
		//Log.w("Enemy info:",(String)(GetX() + "," + GetY() + "   movey:"+getMoveY()));
		if(getOverheat() == 0)
		{
			requestShot(20);
		}
		m_healthBar.setRect(GetX(), GetY()-10,30,5);
		m_healthBar.setVal(getHealth());
	}
	
	public void Draw()
	{
		super.Draw();
		if(!isDead())
			m_healthBar.draw();
	}
	
	protected void CustomBorderBehavior(int x_outOfBounds, int y_outOfBounds)
	{
		if(x_outOfBounds != 0)
		{
			setMoveX(-getMoveX());
		}
		if(y_outOfBounds != 0)
		{
			if(GetBottom() <= GetBorders().bottom && getMoveY() > 0);
			else setMoveY(-getMoveY());
		}
	}
}