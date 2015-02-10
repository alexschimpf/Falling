package com.tendersaucer.untitled;

public class Globals {
	
	private Level level;
	private State state;

	private static Globals instance;

	private Globals() {
		// TODO: initialize stuff
	}

	public static Globals getInstance() {
		if (instance == null) {
			instance = new Globals();
		}

		return instance;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public Level getLevel() {
		return this.level;
	}
	
	public State getState() {
		return this.state;
	}
}
