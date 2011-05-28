package ru.hackday.crabtrip;

import ru.hackday.crabtrip.view.CanvasView;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

public class MyActivity extends Activity implements OnTouchListener {
	
	private Thread mRenderThread;
	private CanvasView mView;
	private SoundManager mSoundManager;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mView = (CanvasView) findViewById(R.id.gameView);
        mSoundManager = SoundManager.getInstance(getApplicationContext());
        findViewById(R.id.up).setOnTouchListener(this);
        findViewById(R.id.down).setOnTouchListener(this);
        
        Handler drumHandler = new Handler(new Callback() {
			
			@Override
			public boolean handleMessage(Message msg) {
				Log.d("CRAAAAAAB", "play drum");
				mSoundManager.playDrum();
				return true;
			}
		});
        EventBus.getInstance().mDrumHandler = drumHandler;
        
        Handler mTapHandler = new Handler(new Callback() {
			
			@Override
			public boolean handleMessage(Message msg) {
				boolean pata = msg.what == 0;
				Log.d("CRAAAAAAAAB", pata ? "pata" : "pon");
				if (pata) {
					mSoundManager.playPata();
				} else {
					mSoundManager.playPon();
				}
				return true;
			}
		});
        EventBus.getInstance().mTapHandler = mTapHandler;
    }
    
    @Override
    protected void onResume() {
    	mRenderThread =  new Thread(new Runnable() {
            public void run() {
                while (! Thread.interrupted()) {
                	mView.postInvalidate();
                	try { 
						Thread.sleep(50);
					} catch (InterruptedException e) {
						return;
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
}
