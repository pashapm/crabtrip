package ru.hackday.crabtrip;

import ru.hackday.crabtrip.view.CanvasView;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MyActivity extends Activity implements OnClickListener {
	
	private Thread mOpsThread;
	private CanvasView mView;
	private SoundManager mSoundManager;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mView = (CanvasView) findViewById(R.id.gameView);
        mSoundManager = SoundManager.getInstance(getApplicationContext());
        findViewById(R.id.up).setOnClickListener(this);
        findViewById(R.id.down).setOnClickListener(this);
    }
    
    @Override
    protected void onResume() {
//    	mSoundManager.startDrum();
    	mOpsThread =  new Thread(new Runnable() {
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
    	mOpsThread.start();
    	super.onResume();
    }
    
    @Override
    protected void onPause() {
    	mSoundManager.stopDrum();
    	mOpsThread.interrupt();
    	mOpsThread = null;
    	super.onPause();
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.up:
			mSoundManager.startPlayObstacle(1, 1);
			break;
		case R.id.down:
			mSoundManager.startPlayObstacle(1, 2);
			break;
		default:
			break;
		}
	}
}
