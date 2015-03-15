package core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import common.Globals;

public class Game extends ApplicationAdapter {

	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;

	@Override
	public void create() {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update();

		batch.begin();
		draw(batch, shapeRenderer);
		batch.end();
	}
	
	private void update() {
		Globals globals = Globals.getInstance();
		switch(globals.getState()) {
			case Loading:
				break;
			case Running:
				globals.getLevel().update();
				break;
			case GameOver:
				break;
			case Paused:
				break;
		}
	}
	
	private void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
		Globals globals = Globals.getInstance();
		switch(globals.getState()) {
			case Loading:
				break;
			case Running:
				globals.getLevel().draw(spriteBatch, shapeRenderer);
				break;
			case GameOver:
				break;
			case Paused:
				break;
		}
	}
}
