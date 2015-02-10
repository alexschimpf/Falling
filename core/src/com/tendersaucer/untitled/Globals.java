package com.tendersaucer.untitled;

import com.badlogic.gdx.physics.box2d.World;

public class Globals {
	
	private It it;
	private World world;
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
	
	public It getIt() {
		return this.it;
	}

	public World getWorld() {
		return this.world;
	}
	
	public Level getLevel() {
		return this.level;
	}
	
	public State getState() {
		return this.state;
	}
}
