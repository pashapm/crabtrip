package ru.hackday.crabtrip;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundManager {

	private static SoundManager sInstance;
	
	private static int INF_LOOP = 10;
	
	public static synchronized SoundManager getInstance(Context ctx) {
		if (sInstance == null) {
			sInstance = new SoundManager(ctx);
		}
		return sInstance;
	}
	
	private SoundManager(Context ctx) {
		mCtx = ctx;
	}
	
	private Context mCtx;
	private SoundPool mSoundPool = new SoundPool(9, AudioManager.STREAM_MUSIC, 1);
	private Map<String, Integer> mSounds = new HashMap<String, Integer>();
	
	public void loadSounds() {
		int id = mSoundPool.load(mCtx, R.raw.drum_mono, 1);
		mSounds.put("drum", id);
		id = mSoundPool.load(mCtx, R.raw.drum, 1);
		mSounds.put("obstacle", id);
		//id = mSoundPool.load(mCtx, R.raw.pata, 1);
		id = mSoundPool.load(mCtx, R.raw.drum_success_mono, 1);
		mSounds.put("pata", id);
		//id = mSoundPool.load(mCtx, R.raw.pon, 1);
		id = mSoundPool.load(mCtx, R.raw.drum_success_mono, 1);
		mSounds.put("pon", id);
		id = mSoundPool.load(mCtx, R.raw.rotate, 1);
		mSounds.put("turn", id);
		id = mSoundPool.load(mCtx, R.raw.sonar, 1);
		mSounds.put("obstacle", id);
	}
	
	private int idByName(String name) {
		return mSounds.get(name);
	}
	
	float mLeft = 1f;
	float mRight = 1f;
	
	/**
	 * @param direction 1 - from left, 2 - front, 3 - right
	 */
	public void updateDirection(int direction) {
		switch (direction) {
		case 3:
			mLeft = 1f;
			mRight = 0;
			break;
		case 2:
			mLeft = 1f;   
			mRight = 0.5f;
			break;
		case 1:
			mLeft = 0;   
			mRight = 1f;
			break;
		default:
			break;
		}
	}
	
	public void playDrum() {
		mSoundPool.play(idByName("drum"), mLeft, mRight, 1, 0, 1);
	}
	
//	public void stopDrum() {
//		mSoundPool.stop(idByName("drum"));
//	}
	
	public void playCrash() {
		mSoundPool.play(idByName("crash"), 1f, 1f, 1, 0, 1);
	}
	
	/**
	 * 
	 * @param direction 0 - left, 1 - right
	 */
	public void playRotate(int direction) {
		float l = direction == 0 ? 1 : 0;
		float r = direction == 0 ? 0 : 1;
		mSoundPool.play(idByName("rotate"), l, r, 1, 0, 1);
	}
	
	/**
	 * @param direction 1 - from left, 2 - front, 3 - right
	 */
	public void startPlayObstacle(int direction, int distance) {
		int id = idByName("obstacle");
		mSoundPool.stop(id);
		
		float left = 0, right = 0;
		switch (direction) {
		case 3:
			left = 0.5f;
			right = 0;
			break;
		case 2:
			left = 0.5f;   
			right = 0.5f;
			break;
		case 1:
			left = 0;   
			right = 0.5f;
			break;
		default:
			break;
		}
		
//		left *= (Math.pow(0.75, distance));
//		right *= (Math.pow(0.75, distance));
		
		mSoundPool.play(id, left, right, 1, INF_LOOP, 1);
	}

	public void playPata() {
		int id = idByName("pata");
		mSoundPool.play(id, mLeft, mRight, 1, 0, 0);
	}

	public void playPon() { 
		int id = idByName("pon");
		mSoundPool.play(id, mLeft, mRight, 1, 0, 0);
	}
	   
	public void playTurnLeft() {
		int id = idByName("turn");
		mSoundPool.play(id, 0, 1, 1, 0, 0);
	}
	
	public void playTurnRight() {
		int id = idByName("turn");
		mSoundPool.play(id, 1, 0, 1, 0, 0);
	}
}
