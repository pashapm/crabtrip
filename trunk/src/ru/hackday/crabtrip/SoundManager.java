package ru.hackday.crabtrip;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundManager {

	private static SoundManager sInstance;
	
	private static int INF_LOOP = 20;
	
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
		int id = mSoundPool.load(mCtx, R.raw.helmet2, 1);
		mSounds.put("drum", id);
		id = mSoundPool.load(mCtx, R.raw.helmet2, 1);
		mSounds.put("obstacle", id);
	}
	
	private int idByName(String name) {
		return mSounds.get(name);
	}
	
	public void startDrum() {
		mSoundPool.play(idByName("drum"), 0.75f, 0.75f, 1, INF_LOOP, 1);
	}
	
	public void stopDrum() {
		mSoundPool.stop(idByName("drum"));
	}
	
	public void playCrash() {
		mSoundPool.play(idByName("crash"), 1f, 1f, 1, 0, 1);
	}
	
	
	public void playCommandSound(int command) {
		
	}
	
	/**
	 * @param direction 0 - from left, 1 - front, 2 - right
	 * @param distance 0, 1, 2
	 */
	public void startPlayObstacle(int direction, int distance) {
		int id = idByName("obstacle");
		mSoundPool.stop(id);
		
		float left = 0, right = 0;
		switch (direction) {
		case 0:
			left = 1;
			right = 0;
			break;
		case 1:
			left = 1;   
			right = 1 ;
			break;
		case 2:
			left = 0;   
			right = 1;
			break;
		default:
			break;
		}
		
//		left *= (Math.pow(0.75, distance));
//		right *= (Math.pow(0.75, distance));
		
		mSoundPool.play(id, left, right, 1, INF_LOOP, distance);
	}
	   
	
}
