package ru.hackday.crabtrip.model;

public enum Direction {
	LEFT(-1),
	FORWARD(0),
	RIGHT(1);	
	
	private int id;
	
	private Direction(final int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}
