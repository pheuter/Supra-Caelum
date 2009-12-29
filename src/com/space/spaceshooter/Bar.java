package com.space.spaceshooter;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;

class Bar
{
	private Paint m_frontPaint = new Paint();
	private Paint m_backPaint = new Paint();
	private Rect m_rect;
	int m_val;
	int m_max;
	
	public void setVal(int val, int max)
	{
		m_val = val;
		m_max = max;
		m_frontPaint.setStyle(Style.FILL);
		m_backPaint.setStyle(Style.FILL);
	}
	
	public void setVal(int val)
	{
		m_val = val;
	}
	
	public void setMax(int max)
	{
		m_max = max;
	}
	
	public void setRect(Rect rect)
	{
		m_rect = rect;
	}
	
	public void setRect(int x, int y, int width, int height)
	{
		m_rect = new Rect(x, y, x + width, y + height);
	}
	
	public void setFrontColor(int r, int g, int b)
	{
		m_frontPaint.setARGB(255, r, g, b);
	}
	
	public void setBackColor(int r, int g, int b)
	{
		m_backPaint.setARGB(255, r, g, b);
	}
	
	public void draw()
	{
		int right = (int)(m_rect.left + (m_rect.right - m_rect.left)*(m_val/(float)m_max));
		Rect frontRect = new Rect(m_rect.left, m_rect.top,(right < 0 ? 0 : right),m_rect.bottom);
		Sprite.getCanvas().drawRect(m_rect, m_backPaint);
		Sprite.getCanvas().drawRect(frontRect, m_frontPaint);
	}
}