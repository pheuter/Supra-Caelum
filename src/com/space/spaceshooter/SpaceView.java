package com.space.spaceshooter;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
//import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;
//import android.widget.Toast;


class SpaceView extends SurfaceView implements SurfaceHolder.Callback, OnTouchListener{
    class SpaceThread extends Thread {

    	/*
         * State-tracking constants
         */
        public static final int STATE_LOSE = 1;
        public static final int STATE_PAUSE = 2;
        public static final int STATE_READY = 3;
        public static final int STATE_RUNNING = 4;
        public static final int STATE_WIN = 5;
        public static final int STATE_STATS = 6;

        private int mCanvasHeight = 1;
        private int mCanvasWidth = 1;
		
        private int mMode;
        private boolean mRun = false;
        private long mLastTime;
        Random r = new Random();
        
        private Input sInput;
		private Star[] sStars;
		private PlayerShip ship;
		//private EnemyShip eShip;
		private ShipHandler m_sHandler;
        private SurfaceHolder mSurfaceHolder;
        private Handler mHandler;
        private LevelMenu levelMenu;
        private Paint paint;

        
        	
        public SpaceThread(SurfaceHolder surfaceHolder, Context context,
                Handler handler) {
            mSurfaceHolder = surfaceHolder;
            mHandler = handler;
            mContext = context;
            sInput = new Input();
            Sprite.setContext(context);
        	//myRestorer = new Restorer(); //Handle volative values when restoring and saving
        	levelMenu = new LevelMenu(context);      	
        	paint = new Paint();
        	paint.setARGB(255, 255, 255, 255);
        }


        /**
         * Starts the game, setting parameters for the current difficulty.
         */
        public void doStart() {
            synchronized (mSurfaceHolder) {  
    			mCanvasWidth = mSurfaceHolder.getSurfaceFrame().width();
                mCanvasHeight = mSurfaceHolder.getSurfaceFrame().height();
                Sprite.setScreenSize(mCanvasWidth, mCanvasHeight);
                sStars = new Star[10];
                
            	ship = new PlayerShip(mCanvasWidth/2,mCanvasHeight - 50);
            	ship.SetBorders(mCanvasHeight-32, mCanvasHeight, 0, mCanvasWidth);
            	ship.SetImage(R.drawable.ship_trans);
            	
            	
            	m_sHandler = new ShipHandler();
            	m_sHandler.setPlayer(ship);
            	m_sHandler.requestEnemy(200, -100);
            	
            	PlasmaShot.setPlayer(ship);
            	PlasmaShot.setShipHandler(m_sHandler);
                
    			for (int i = 0; i < sStars.length; i++)
    			{
    				sStars[i] = new Star(r.nextInt(mCanvasWidth), r.nextInt(mCanvasHeight),3);
    				sStars[i].SetBorders(0, mCanvasHeight, 0, mCanvasWidth);
    				sStars[i].SetImage(R.drawable.star);
    			}
    					
    			mLastTime = System.currentTimeMillis() + 100;
                setState(STATE_RUNNING);
                
                /*Clear Stats*/
                PlayerShip.setM_level(0);
        		ShipHandler.setTotal_kills(0);
        		PlayerShip.setShots_fired(0);
        		PlasmaShot.setShots_hit(0);
            }
        }

        /**
         * Pauses the physics update & animation.
         */
        public void pause() {
        	synchronized (mSurfaceHolder) {
                if (mMode == STATE_RUNNING) setState(STATE_PAUSE);
            }
        }
        
        public synchronized void restoreState(Bundle savedState) {
            synchronized (mSurfaceHolder) {
                setState(STATE_PAUSE);
                //Restore saved valued from savedState
                
            }
        }

        @Override
        public void run() {
            while (mRun) {
                Canvas c = null;
                try {
                    c = mSurfaceHolder.lockCanvas(null);
                    Sprite.setCanvas(c);
                    synchronized (mSurfaceHolder) 
                    {
                    	if (System.currentTimeMillis() - mLastTime >= 20) // 25
                    		updatePhysics();                 
                    	doDraw(c);                                         
                    }
                  
                } finally {
                    // do this in a finally so that if an exception is thrown
                    // during the above, we don't leave the Surface in an
                    // inconsistent state
                    if (c != null) {
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }

        public void setRunning(boolean b) {
            mRun = b;
        }

        public void setState(int mode) {
            synchronized (mSurfaceHolder) {
                setState(mode, null);
            }
        }

        public void setState(int mode, CharSequence message) {
            synchronized (mSurfaceHolder) {
                mMode = mode;
                
            }
        }

        /* Callback invoked when the surface dimensions change. */
        public void setSurfaceSize(int width, int height) {
            // synchronized to make sure these all change atomically
            synchronized (mSurfaceHolder) {
                mCanvasWidth = width;
                mCanvasHeight = height;

            }
        }

        /**
         * Resumes from a pause.
         */
        public void unpause() {
        	synchronized (mSurfaceHolder) {
                mLastTime = System.currentTimeMillis() + 100;
            }
            setState(STATE_RUNNING);
        }

        boolean doKeyDown(int keyCode, KeyEvent msg) {
        	synchronized (mSurfaceHolder) {
                return sInput.onButtonDown(keyCode, msg);
            }
        }

        boolean doKeyUp(int keyCode, KeyEvent msg) {

        	return sInput.onButtonUp(keyCode, msg);
        }

        private void doDraw(Canvas canvas) {
        	if (mMode == STATE_RUNNING)
        	{
        		canvas.drawARGB(255, 0, 0, 0);
        		canvas.drawText("Stats", mCanvasWidth-25, mCanvasHeight-5, paint);
        		for (int i = 0; i < sStars.length; i++)
        			sStars[i].Draw();
        		
	        	//ship.Draw();
	        	m_sHandler.draw();
	        	//PlasmaShot.DrawPlasmaShots();
        	}
        	if (mMode == STATE_STATS)
        	{
        		canvas.drawARGB(255, 128, 128, 128);   
        		canvas.drawText("Back", mCanvasWidth-25, mCanvasHeight-5, paint);
        		levelMenu.Draw();
        	}
        }

        private void updatePhysics() {
        	if (mMode == STATE_RUNNING)
        	{
        		long now = System.currentTimeMillis();        	
        		if (mLastTime > now) return;
        	
        		mLastTime = System.currentTimeMillis();                                
        		ship.ProcInput(sInput);
        		sInput.clear();
        	
        		for (int i = 0; i < sStars.length; i++)
        			sStars[i].Update();
        	
        		//ship.Update();
        		m_sHandler.update();
        		//PlasmaShot.UpdatePlasmaShots();//problem
        	
        		mLastTime = now;
        	}
        }
    }
    private Context mContext;

    private TextView mStatusText;

    private SpaceThread thread;

    public SpaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        setOnTouchListener(this);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        thread = new SpaceThread(holder, context, new Handler() {
            @Override
            public void handleMessage(Message m) {
                /*mStatusText.setVisibility(m.getData().getInt("viz"));
                mStatusText.setText(m.getData().getString("text"));*/
            }
        });

        setFocusable(true); // make sure we get key events
    }

    public SpaceThread getThread() {
        return thread;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent msg) {
        return thread.doKeyDown(keyCode, msg);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent msg) {
        return thread.doKeyUp(keyCode, msg);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (!hasWindowFocus) thread.pause();
    }

    public void setTextView(TextView textView) {
        mStatusText = textView;
    }

    /* Callback invoked when the surface dimensions change. */
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
        thread.setSurfaceSize(width, height);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    
    public void surfaceDestroyed(SurfaceHolder holder) {

        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }
    
    public LevelMenu getlevelMenu()
    {
    	return thread.levelMenu;
    }

	public boolean onTouch(View v, MotionEvent event) {
		if (event.getY() > 400f && event.getX() > 300f)
		{
			setOnTouchListener(thread.levelMenu);
			thread.setState(SpaceThread.STATE_STATS);
		}
		
		return false;
	}
}