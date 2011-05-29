package ru.hackday.crabtrip.model;

public class Ship {
	private static final int INIT_POSITION = 2;
	private static final int INIT_SPEED = 1;
	private static final int INIT_LIFE = 2;
	
	private int mPosition;
	private int mSpeed;	
	private int mLife;
	
	private int mLeftCoast;
	private int mRightCoast;
	
	public void init(final int leftCoast, final int rightCoast) {
		mPosition = INIT_POSITION;
		mSpeed = INIT_SPEED;
		mLife = INIT_LIFE;
		
		mLeftCoast = leftCoast;
		mRightCoast = rightCoast;
	}
	
	public boolean move(Direction direction) {
		boolean success = true;
		switch (direction) {
		case LEFT:
			if (mPosition - mSpeed > mLeftCoast) {
				mPosition -= mSpeed;
			} else {
				success = false;
			}
			break;
		case RIGHT:
			if (mPosition + mSpeed < mRightCoast) {
				mPosition += mSpeed;
			} else {
				success = false;
			}
			break;
		}
		
		return success;
	}
		
	public int getPosition() {
		return mPosition;
	}
	
	public String toString() {
		StringBuilder positionLog = new StringBuilder();
		
		switch (mPosition) {
		case 1:
			positionLog.append("^--");
			break;
		case 2:
			positionLog.append("-^-");
			break;
		case 3:
			positionLog.append("--^");
			break;
		}	
		
		return positionLog.toString();
	}
	
	public int getLife() {
		return mLife;
	}
	
	public void setLife(int life) {
		mLife = life;
	}
}
