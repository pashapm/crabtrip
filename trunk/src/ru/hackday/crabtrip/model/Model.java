package ru.hackday.crabtrip.model;

import android.util.Log;

public class Model {
	private static final String TAG = "Model";
	
	private River mRiver;
	private Ship mShip;
	
	private int mDistance;
	
	public Model() {
		mRiver = new River();
		mShip = new Ship();
				
		reset();
	}
	
	public void reset() {
		mRiver.init();
		mShip.init(0, River.WIDTH + 1);
		
		mDistance = 0;
	}
	
	public boolean move(final Direction direction) {
		mDistance++;
		
		mRiver.shift();
		boolean success = mShip.move(direction);
		Log.d(TAG, mRiver.toString() + mShip.toString());
		return success; 		
	}
	
	public boolean isGameOver() {
		return mRiver.getStone(0) == mShip.getPosition();
	}
	
	public int getNearestStonePosition() {
		int stonePosition = -1;
		for (int i = 1; i < River.LENGTH; i++) {
			final int currentStonePosition = mRiver.getStone(i);
			if (currentStonePosition > 0) {
				stonePosition = currentStonePosition;
				break;
			}			
		}
		return stonePosition;
	}
	
	public int getDistance() {
		return mDistance;
	}
	
	public void setDistance(final int distance) {
		mDistance = distance;
	}

    public River getmRiver() {
        return mRiver;
    }

    public Ship getmShip() {
        return mShip;
    }
}
