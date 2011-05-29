package ru.hackday.crabtrip;

import ru.hackday.crabtrip.model.Direction;
import ru.hackday.crabtrip.model.Model;
import ru.hackday.crabtrip.view.CanvasView;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;

public class MyActivity extends Activity implements OnTouchListener {
	private static final int TICKS_PER_STEP = 40;
	
	
	private Thread mRenderThread;
	private CanvasView mView;
	private SoundManager mSoundManager;
	private Vibrator mVibrator;
	
	private Model mModel;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        mView = (CanvasView) findViewById(R.id.gameView);
        mSoundManager = SoundManager.getInstance(getApplicationContext());
        findViewById(R.id.up).setOnTouchListener(this);
        findViewById(R.id.down).setOnTouchListener(this);
        
        mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        
        Handler drumHandler = new Handler(new Callback() {
			
			@Override
			public boolean handleMessage(Message msg) {
				mSoundManager.playDrum();
				return true;
			}
		});
        EventBus.getInstance().mDrumHandler = drumHandler;
        
        Handler mTapHandler = new Handler(new Callback() {
			
			@Override
			public boolean handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					mSoundManager.playPata();
					break;
				case 1:
					mSoundManager.playPon();
					break;
				case 2:
					mModel.move(Direction.LEFT);
					mView.moveLeft();
					mVibrator.vibrate(100);
					Log.d("CRAAAAAAAAB", "PATA PATA PATA PON!");					
					break;
				case 3:
					mModel.move(Direction.RIGHT);
					mView.moveRight();
					mVibrator.vibrate(100);					
					Log.d("CRAAAAAAAAB", "PON PON PATA PON!");
					break;
				default:
					break;
				}
				
				checkGameOver();
				return true;
			}			
		});
        EventBus.getInstance().mTapHandler = mTapHandler;
        
        mModel = new Model();
    }
    
    @Override
    protected void onResume() {
    	mRenderThread =  new Thread(new Runnable() {
            public void run() {
            	            	
            	int ticks = 0;
            	
                while (! Thread.interrupted()) {
                	mView.postInvalidate();
                	try { 
						Thread.sleep(50);
					} catch (InterruptedException e) {
						return;
					}
					
					if (ticks++ % TICKS_PER_STEP == 0) {//TODO test
						ticks = 1;
						mModel.move(Direction.FORWARD);
						checkGameOver();
					}
                }
            }
        }) ;
    	mRenderThread.start();
    	EventBus.getInstance().start();
    	super.onResume();
    }
    
    @Override
    protected void onPause() {
    	mRenderThread.interrupt();
    	mRenderThread = null;
    	EventBus.getInstance().stop();
    	super.onPause();
    }

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		if (arg1.getAction() != MotionEvent.ACTION_DOWN) {
			return true;
		}
		
		switch (arg0.getId()) {
		case R.id.up:
			EventBus.getInstance().postPatapon(0);
			break;
		case R.id.down:
			EventBus.getInstance().postPatapon(1);
			break;
		default:
			break;
		}
		return true;
	}
	
	private void checkGameOver() {
		if (mModel.isGameOver()) {
			Log.d("CRAAAAAAAAB", "Game Over. Distance = " + mModel.getDistance());
			mModel.reset();
		}
	}
}