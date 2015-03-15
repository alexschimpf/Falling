package common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

import core.Level;

public class Globals {
	
	private State state;
	private Level level;
	private OrthographicCamera camera;

	private static Globals instance;

	private Globals() {
		state = State.Running;
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	public static Globals getInstance() {
		if (instance == null) {
			instance = new Globals();
		}

		return instance;
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
}
