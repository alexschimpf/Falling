package common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

import core.Game;
import core.Level;

public class Globals {
	
	private State state;
	private Game game;
	private Level level;
	private OrthographicCamera camera;

	private static Globals instance;

	private Globals() {
		camera = new OrthographicCamera();
		camera.setToOrtho(true, Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight());
		level = new Level();
	}

	public static Globals getInstance() {
		if (instance == null) {
			instance = new Globals();
		}

		return instance;
	}
	
	public Game getGame() {
		return game;
	}
	
	public void setGame(Game game) {
		this.game = game;
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
