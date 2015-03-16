package core;

import java.awt.geom.Line2D;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import common.Globals;
import common.State;
import entity.floating.LineEntity;

public class Game extends ApplicationAdapter {

	private Globals globals;
	private Level level;
	private Color lineColor = Color.WHITE;
	private Line2D.Float line = new Line2D.Float();
	private SpriteBatch batch = new SpriteBatch();
	private ShapeRenderer shapeRenderer = new ShapeRenderer();

	@Override
	public void create() {
		globals = Globals.getInstance();
		level = globals.getLevel();
		globals.setGame(this);
		globals.setState(State.Running);
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
	
	public void updateLine(float x, float y, boolean isAnchored) {
		if(level.lineIntersectsExisting(line)) {
			lineColor = Color.RED;
		} else {
			lineColor = Color.WHITE;
		}
		
		if(isAnchored) {
			line.x2 = x;
			line.y2 = y;
		} else {
			line.x1 = x;
			line.y1 = y;
			line.x2 = line.x1;
			line.y2 = line.y1;
		}
	}
	
	public void tryBuildLine(float x, float y) {
		// Don't build line if it is intersecting another.
		if(lineColor.r > lineColor.g) {
			return;
		}
			
		updateLine(x, y, true);	
		LineEntity lineEntity = new LineEntity(line.x1, line.y1, line.x2, line.y2);
		lineEntity.setUserData();
		level.addLine(lineEntity);
	}
	
	public void eraseLine() {
		line.setLine(0, 0, 0, 0);
		lineColor = Color.WHITE;
	}
	
	private void update() {
		switch(globals.getState()) {
			case Loading:
				break;
			case Running:
				level.update();
				break;
			case GameOver:
				break;
			case Paused:
				break;
		}
	}
	
	private void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
		switch(globals.getState()) {
			case Loading:
				break;
			case Running:
				level.draw(spriteBatch, shapeRenderer);
				break;
			case GameOver:
				break;
			case Paused:
				break;
		}
	}
}
