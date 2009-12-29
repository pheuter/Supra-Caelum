package com.space.spaceshooter;

import android.view.KeyEvent;

public class Input
{
	public enum ButtonState
	{
		DOWN,
		UP
	}
	
	private ButtonState[] m_buttonStates;
	private ButtonState[] m_updatedButtonStates;
	private ButtonState[] m_prevButtonStates;
	public Input()
	{
		m_buttonStates = new ButtonState[129];
		m_updatedButtonStates = new ButtonState[129];
		m_prevButtonStates = new ButtonState[129];
		for(int i = 0; i < 129;i++)
		{
			m_buttonStates[i] = ButtonState.UP;
			m_updatedButtonStates[i] = ButtonState.UP;
			m_prevButtonStates[i] = ButtonState.UP;
		}
	}
	
	public void clear()//update
	{
		for(int i = 0; i < 129;i++)
		{
			m_prevButtonStates[i] = m_updatedButtonStates[i];
			m_updatedButtonStates[i] = m_buttonStates[i];
		}
	}
	
	public boolean onButtonDown(int keyCode, KeyEvent evt)
	{
		m_buttonStates[evt.getKeyCode()] = ButtonState.DOWN;
                m_updatedButtonStates[evt.getKeyCode()] = ButtonState.DOWN;
		return true;
	}
	
	public boolean onButtonUp(int keyCode, KeyEvent evt)
	{
		m_buttonStates[evt.getKeyCode()] = ButtonState.UP;
		return true;
	}
	
	public boolean buttonDown(int keyCode)
	{
		return m_updatedButtonStates[keyCode] == ButtonState.DOWN;
	}
	
	public boolean buttonUp(int keyCode)
	{
		return m_updatedButtonStates[keyCode] == ButtonState.UP;
	}
	
	public boolean buttonPressed(int keyCode)
	{
		return m_updatedButtonStates[keyCode] == ButtonState.DOWN && m_prevButtonStates[keyCode] == ButtonState.UP;
	}
	
	public boolean buttonReleased(int keyCode)
	{
		return m_updatedButtonStates[keyCode] == ButtonState.UP && m_prevButtonStates[keyCode] == ButtonState.DOWN;
	}
	
};