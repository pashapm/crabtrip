package ru.hackday.crabtrip;

import android.os.Handler;
import android.util.Log;

public class EventBus {

	private static final String TAG = "CRAAAAAAAAB";
	private DrumThread mDrumThread;
	
	private static final float DRUM_FREQ = 1.5f;
	private static final long DELTA = (long) ((1000/DRUM_FREQ) / 4);  // +-20%
	
	private static final long DRUM_BEAT_OFFSET = 180;
	
	public Handler mDrumHandler;
	public Handler mTapHandler;
	
	private long mLastDrum = 0;
	
	private static EventBus sInstance = new EventBus();
	private EventBus() {
		Log.d(TAG, "delta: "+DELTA+"ms");
	}
	
	public static EventBus getInstance() {
		return sInstance;
	}
	
	// 0 - pata, 1 - pon
	public void postPatapon(int pp) {
		long time = System.currentTimeMillis() - DRUM_BEAT_OFFSET;
		Log.d(TAG, mLastDrum%10000 + " " + time%10000 + " " + (time - mLastDrum)%10000);
		boolean justBefore = time > mLastDrum + 3*DELTA;
		boolean justAfter = time - mLastDrum < DELTA;
		if (justBefore || justAfter) {
			mTapHandler.sendEmptyMessage(pp);
		}
	}
	
	public void start() {
		stop();
		mDrumThread = new DrumThread();
		mDrumThread.start();
	}
	
	public void stop() {
		if (mDrumThread != null) {
			mDrumThread.interrupt();
		}
		mDrumThread = null;
	}
	
	class DrumThread extends Thread {
		@Override
		public void run() {
	        while (! Thread.interrupted()) {
	        	try { 
	        		Thread.sleep((long) (1000/DRUM_FREQ));
	        		if (mDrumHandler != null) {
						mDrumHandler.sendEmptyMessage(0);
					}
	        		mLastDrum = System.currentTimeMillis();
				} catch (InterruptedException e) {
					return;
				}
	        }
		}
	}
	
}
