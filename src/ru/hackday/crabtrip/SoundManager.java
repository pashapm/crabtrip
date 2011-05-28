package ru.hackday.crabtrip;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundManager {

	private static SoundManager sInstance;
	
	public synchronized SoundManager getInstance(Context ctx) {
		if (sInstance == null) {
			sInstance = new SoundManager();
		}
		return sInstance;
	}
	
	private Context mCtx;
	private SoundPool mSoundPool = new SoundPool(9, AudioManager.STREAM_MUSIC, 1);
	private Map<String, Integer> mSounds = new HashMap<String, Integer>();
	
	
	private void loadSounds() {
		
	}
	
	private int idByName(String name) {
		return mSounds.get(name);
	}
	
	public void startDrum() {
		mSoundPool.play(idByName("drum"), 0.75f, 0.75f, 1, -1, 1);
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
		float left = 0, right = 0;
		switch (direction) {
		case 0:
			left = 1;
			right = 0;
			break;
		case 1:
			left = 0;   
			right = 1 ;
			break;
		case 2:
			left = 1;   
			right = 1;
			break;
		default:
			break;
		}
		
//		left *= (Math.pow(0.75, distance));
//		right *= (Math.pow(0.75, distance));
		
		int id = idByName("obstacle");
		mSoundPool.stop(id);
		mSoundPool.play(id, left, right, 1, -1, distance);
	}
	   
	
}
