package ru.hackday.crabtrip;

import ru.hackday.crabtrip.view.CanvasView;
import android.app.Activity;
import android.os.Bundle;

public class MyActivity extends Activity {
	
	private Thread mOpsThread;
	private CanvasView mView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mView = (CanvasView) findViewById(R.id.gameView);
    }
    
    @Override
    protected void onResume() {
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
    	mOpsThread.interrupt();
    	mOpsThread = null;
    	super.onPause();
    }
}
