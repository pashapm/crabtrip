package ru.hackday.crabtrip.model;

import java.util.LinkedList;
import java.util.Random;

public class River {
	private static final String TAG = "River";
	
	public static final int LENGTH = 3;
	public static final int WIDTH = 3;
	
	private final LinkedList<Integer> mField = new LinkedList<Integer>();
	
	private Random mRand = new Random();
	
	public void init() {
		mField.clear();
		
		mField.add(mRand.nextInt(WIDTH) + 1);
		for (int i = 0; i < LENGTH - 1; i++) {
			mField.add(0);
		}
	}
	
	public int shift() {
		int nextStone = mField.remove(0);
		int newStone = 0;
		if (mField.get(LENGTH - 2) == 0) {
			newStone = mRand.nextInt(WIDTH + 1);
		}
		mField.add(newStone);
		
		return nextStone;
	}
	
	public int getStone(final int distance) {
		int stonePosition;
		
		if (distance > mField.size()) {
			stonePosition = mField.get(mField.size() - 1);
		} else {
			stonePosition = mField.get(distance);
		}
		
		return stonePosition;
	}
	
	public String toString() {
		StringBuilder map = new StringBuilder();
		for (int i = mField.size() - 1; i > -1; i--) {
			int row = mField.get(i);
			switch (row) {
			case 0:
				map.append("***\n");
				break;
			case 1:
				map.append("x**\n");
				break;
			case 2:
				map.append("*x*\n");
				break;
			case 3:
				map.append("**x\n");
				break;
			}			
		}
		
		return map.toString();
	}
}
