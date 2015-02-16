package com.tendersaucer.untitled;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Globals {
	
	private Level level;
	private State state;

	private static Globals instance;

	private Globals() {
		state = State.Running;
		initLevel("test_level.xml");
	}

	public static Globals getInstance() {
		if (instance == null) {
			instance = new Globals();
		}

		return instance;
	}
	
	public void initLevel(String filename) {
		FileHandle fileHandle = Gdx.files.getFileHandle(filename, FileType.Internal);
		level = new Level(fileHandle);
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
