package core;

import java.awt.geom.Line2D;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import common.Globals;
import entity.floating.LineEntity;

public class Game extends ApplicationAdapter {

	private Color lineColor = Color.WHITE;
	private Line2D.Float line = new Line2D.Float();
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
	
	public void updateLine(float x, float y, boolean isAnchored) {
		if(Globals.getInstance().getLevel().lineIntersectsExisting(line)) {
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
		Globals.getInstance().getLevel().addLine(lineEntity);
	}
	
	public void eraseLine() {
		line.setLine(0, 0, 0, 0);
		lineColor = Color.WHITE;
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
