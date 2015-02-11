package com.tendersaucer.untitled;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter {

	private SpriteBatch batch;

	@Override
	public void create() {
		batch = new SpriteBatch();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update();

		batch.begin();
		draw(batch);
		batch.end();
	}
	
	private void update() {
		Globals globals = Globals.getInstance();
		switch(globals.getState()) {
			case Running:
				globals.getLevel().update();
				break;
			case Building:
				break;
			case LevelLost:
				break;
			case LevelWon:
				break;
		}
	}
	
	private void draw(SpriteBatch spriteBatch) {
		Globals globals = Globals.getInstance();
		switch(globals.getState()) {
			case Running:
				globals.getLevel().draw(spriteBatch);
				break;
			case Building:
				break;
			case LevelLost:
				break;
			case LevelWon:
				break;
		}
	}
}
