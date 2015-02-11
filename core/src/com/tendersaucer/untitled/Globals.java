package com.tendersaucer.untitled;

import java.io.File;

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
	
	public void initLevel(File file) {
		level = new Level(file);
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public State getState() {
		return state;
	}
}
