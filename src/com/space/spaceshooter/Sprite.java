package com.space.spaceshooter;

import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Canvas;
import android.content.Context;

public abstract class Sprite
{
	public static Context m_context;
	public static Canvas m_canvas;
	public static Rect m_screenSize = new Rect();
	
	public static void setContext(Context c)
	{
		m_context = c;
	}
	
	public static void setCanvas(Canvas c)
	{
		m_canvas = c;
	}
	
	public static Context getContext()
	
	{
		return m_context;
	}
	
	public static Canvas getCanvas()
	{
		return m_canvas;
	}
	
	public static void setScreenSize(int w, int h)
	{
		m_screenSize.left = 0;
		m_screenSize.right = w;
		m_screenSize.top = 0;
		m_screenSize.bottom = h;
	}
	
	public static Rect getScreenSize()
	{
		return m_screenSize;
	}
	
	public static void drawBar(int x, int y, int w, int h, int val, int max)
	{
		Rect rBack = new Rect(x, y, x + w, y + h);
		Rect rFront = new Rect(x, y, (int)((x + w)*(val/(float)max)), y + h);
		Paint back = new Paint();
		Paint front = new Paint();
		back.setStyle(Style.FILL);
		front.setStyle(Style.FILL);
		back.setARGB(255, 50, 50, 50);
		front.setARGB(255, 255, 255, 0);
		Sprite.getCanvas().drawRect(rBack, back);
		Sprite.getCanvas().drawRect(rFront, front);
	}
	
	public static void drawBar(int x, int y, int w, int h, int val)
	{
		drawBar(x,y,w,h,val,100);
	}
	
	public enum BorderBehavior
	{
		Stop,
		Custom
	}
	
	private Drawable m_image;
	private Point m_position;
	private Rect m_borders;
	private BorderBehavior m_borderBehavior;
	
	public Sprite()
	{
		this(0,0);
	}
	
	public Sprite(int x, int y)
	{
		m_position = new Point(x, y);
		m_borders = new Rect(-1, -1, -1, -1);
		m_borderBehavior = BorderBehavior.Stop;
	}
	
	public void SetImage(int id)//, Context context)
	{
		m_image = m_context.getResources().getDrawable(id);
	}
	
	public void SetBorderBehavior(BorderBehavior bb)
	{
		m_borderBehavior = bb;
	}
	
	public void SetBorders(int top, int bottom, int left, int right)
	{
		m_borders.left = left;
		m_borders.top = top;
		m_borders.right = right;
		m_borders.bottom = bottom;
	}
	
	public void SetBorders(Rect borders)
	{
		m_borders = borders;
	}
	
	protected void CustomBorderBehavior(int x_outOfBounds, int y_outOfBounds)
	{
	}
	
	public void BorderCheck()
	{
		boolean hits[] = new boolean[4];
		for(int i = 0;i < 4;i++)
			hits[i] = false;
		int x_outOfBounds = 0, y_outOfBounds = 0;
		if(GetX() < m_borders.left && m_borders.left != -1)
		{
			if(m_borderBehavior == BorderBehavior.Stop)
				SetX(m_borders.left);
			else if(m_borderBehavior == BorderBehavior.Custom)
				x_outOfBounds = GetX() - m_borders.left;
		}
		else if(GetX() + GetWidth() > m_borders.right && m_borders.right != -1)
		{
			if(m_borderBehavior == BorderBehavior.Stop)
				SetX(m_borders.right - GetWidth());
			else if(m_borderBehavior == BorderBehavior.Custom)
				x_outOfBounds = GetX() + GetWidth() - m_borders.left;
		}
		if(GetY() < m_borders.top && m_borders.top != -1)
		{
			if(m_borderBehavior == BorderBehavior.Stop)
				SetY(m_borders.top);
			else if(m_borderBehavior == BorderBehavior.Custom)
				y_outOfBounds = GetY() - m_borders.top;
		}
		else if(GetY() + GetHeight() > m_borders.bottom && m_borders.bottom != -1)
		{
			if(m_borderBehavior == BorderBehavior.Stop)
				SetY(m_borders.bottom - GetHeight());
			else if(m_borderBehavior == BorderBehavior.Custom)
				y_outOfBounds = GetY() + GetHeight() - m_borders.bottom;
		}
		if(m_borderBehavior == BorderBehavior.Custom)
			CustomBorderBehavior(x_outOfBounds, y_outOfBounds);
	}
	
	public void ProcInput(Input in)
	{
	}
	
	public void Update()
	{
		BorderCheck();
	}
	
	public void Draw()//Canvas c)
	{
		m_image.setBounds(m_position.x, m_position.y, GetRight(), GetBottom());
		m_image.draw(m_canvas);
	}
	
	public boolean collides(Sprite s)
	{
		return GetRectangle().intersect(s.GetRectangle());
	}
	
	public Point GetPosition()
	{
		return m_position;
	}
	
	public int GetX()
	{
		return m_position.x;
	}
	
	public int GetY()
	{
		return m_position.y;
	}
	
	public int GetWidth()
	{
		return m_image.getIntrinsicWidth();
	}
	
	public int GetHeight()
	{
		return m_image.getIntrinsicHeight();
	}
	
	public int GetLeft()
	{
		return m_position.x;
	}
	
	public int GetTop()
	{
		return m_position.y;
	}
	
	public int GetRight()
	{
		return m_position.x + GetWidth();
	}
	
	public int GetBottom()
	{
		return m_position.y + GetHeight();
	}
	
	public Rect GetRectangle()
	{
		return new Rect(GetX(), GetY(), GetRight(), GetBottom());
	}
	
	public Rect GetBorders()
	{
		return m_borders;
	}
	
	public void SetPosition(Point p)
	{
		m_position.x = p.x;
		m_position.y = p.y;
	}
	
	public void SetPosition(int x, int y)
	{
		m_position.x = x;
		m_position.y = y;
	}
	
	public void SetX(int x)
	{
		m_position.x = x;
	}
	
	public void SetY(int y)
	{
		m_position.y = y;
	}
}