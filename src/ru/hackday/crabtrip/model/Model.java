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
		
		if (mRiver.getStone(0) == mShip.getPosition()) {
			mShip.setLife(mShip.getLife() - 1);
		}
//		Log.d(TAG, mRiver.toString() + mShip.toString());
		return success; 		
	}
	
	public boolean isGameOver() {
		return mShip.getLife() == 0;
	}
	
	public int getNearestStonePosition() {
		int stoneDistance = getNearestStoneDistance();
		
		return stoneDistance > 0 ? mRiver.getStone(stoneDistance) : -1;
	}
	
	public int getNearestStoneDistance() {
		int stoneDistance = -1;
		for (int i = 1; i < River.LENGTH; i++) {
			final int currentStonePosition = mRiver.getStone(i);
			if (currentStonePosition > 0) {
				stoneDistance = i;
				break;
			}			
		}
		return stoneDistance;
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
