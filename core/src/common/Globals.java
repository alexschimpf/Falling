package common;

import com.badlogic.gdx.graphics.OrthographicCamera;

import core.Game;
import core.Level;
import entity.TheEntity;

public final class Globals {
	
	public static final float VIEWPORT_WIDTH = 60.0f;
	public static final float VIEWPORT_HEIGHT = 100.0f;
	
	private State state;
	private Game game;
	private Level level;
	private OrthographicCamera camera;

	private static Globals instance;

	private Globals() {
		resetCamera();
	}

	public static Globals getInstance() {
		if (instance == null) {
			instance = new Globals();
		}

		return instance;
	}
	
	public void initLevel() {
		level = new Level();
		level.initTheEntity();
	}
	
	public void resetCamera() {
		camera = new OrthographicCamera();
		camera.setToOrtho(true, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
	}
	
	public void updateCamera() {	
		if(state == State.Running) {
			TheEntity theEntity = level.getTheEntity();
			float theEntitySpeed = theEntity.getBody().getLinearVelocity().y / 47;
			camera.translate(0, Math.max(theEntitySpeed, level.getSpeed()));
		}

		camera.update();
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
		
		if(state == State.GameOver && level != null) {
			game.setNeedsLevelReset();
		}
	}
}
