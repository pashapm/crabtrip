package ru.hackday.crabtrip;

import java.util.ArrayList;
import java.util.Arrays;

import android.os.Handler;
import android.util.Log;

public class EventBus {

	private static final String TAG = "CRAAAAAAAAB";
	private DrumThread mDrumThread;
	
	private static final float DRUM_FREQ = 1.5f; // in HZ
	private static final float DELTA_DELIM = 4f; // what part of the period is the delta
	private static final long DELTA = (long) ((1000/DRUM_FREQ) / DELTA_DELIM);  
	
	private static final long DRUM_BEAT_OFFSET = 100; //experimental
	
	public Handler mDrumHandler;
	public Handler mTapHandler;
	
	private long mLastDrum = 0;
	private long mLastPatapon = 0;
	
	private ArrayList<Integer> mRhytm = new ArrayList<Integer>(4);
	
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
		
		// broke the rhytm when there are no commands...
		if ((time - mLastPatapon) > (1000/DRUM_FREQ + DELTA)) {
			brokeRhytm();
		}
		mLastPatapon = time;
		
		Log.d(TAG, mLastDrum%10000 + " " + time%10000 + " " + (time - mLastDrum)%10000);
		boolean justBefore = time > mLastDrum + (DELTA_DELIM-1)*DELTA;
		boolean justAfter = time - mLastDrum < DELTA;
		if (justBefore || justAfter) {
			mTapHandler.sendEmptyMessage(pp);
			checkForRhythm(pp);
		} else {
			brokeRhytm();
		}
	}
	
	private void brokeRhytm() {
		mRhytm.clear();
	}

	private void checkForRhythm(int pp) {
		mRhytm.add(pp);
		Log.d(TAG, mRhytm.size()+" mRhytm.size");
		if (mRhytm.size() == 4) {
			Integer[] leftPattern = {0,0,0,1};
			Integer[] rightPattern = {1,1,0,1};
			boolean leftMove = Arrays.deepEquals(mRhytm.toArray(), leftPattern);
			boolean rightMove = Arrays.deepEquals(mRhytm.toArray(), rightPattern);
			
			if (leftMove) {
				mTapHandler.sendEmptyMessage(2);
			} else if (rightMove) {
				mTapHandler.sendEmptyMessage(3);
			}
			
			brokeRhytm();
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
