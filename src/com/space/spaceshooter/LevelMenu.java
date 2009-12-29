package com.space.spaceshooter;


import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class LevelMenu extends View implements OnTouchListener{
	private Paint paint;
	private Paint paint2;
	
	public LevelMenu(Context context)
	{
		super(context);
		paint = new Paint();
		paint2 = new Paint();
		paint.setARGB(255, 0, 255, 0);
		paint2.setARGB(255, 255, 255, 255);
	}
	
	public void Draw()
	{
		Sprite.getCanvas().drawText("Stats Menu", Sprite.getScreenSize().left+125, Sprite.getScreenSize().top+130, paint);	
		Sprite.getCanvas().drawText("current level: "+PlayerShip.getM_level(), Sprite.getScreenSize().left+120, Sprite.getScreenSize().top+150, paint2);	
		Sprite.getCanvas().drawText("# of total kills: "+ShipHandler.getTotal_kills(), Sprite.getScreenSize().left+120, Sprite.getScreenSize().top+160, paint2);
		Sprite.getCanvas().drawText("# of shots fired: "+PlayerShip.getShots_fired(), Sprite.getScreenSize().left+120, Sprite.getScreenSize().top+170, paint2);	
		Sprite.getCanvas().drawText("# of shots hit: "+PlasmaShot.getShots_hit(), Sprite.getScreenSize().left+120, Sprite.getScreenSize().top+180, paint2);	
		try {
			Sprite.getCanvas().drawText("shot accuracy: "+(int)(((float)PlasmaShot.getShots_hit()/(float)PlayerShip.getShots_fired())*100)+"%", Sprite.getScreenSize().left+120, Sprite.getScreenSize().top+190, paint2);	
		} catch (ArithmeticException e) {
			Log.w("ArithemticException", e.getCause());
		}
	}

	public boolean onTouch(View v, MotionEvent event) {
		Log.w("Level Menu Listener!", "touched in level menu");
		if (event.getY() > 350f && event.getX() > 250f)
		{
			SpaceShooter.getSpaceView().setOnTouchListener(SpaceShooter.getSpaceView());
			SpaceShooter.getSpaceView().getThread().setState(SpaceShooter.getSpaceView().getThread().STATE_RUNNING);
		}
		return false;
	}

}
