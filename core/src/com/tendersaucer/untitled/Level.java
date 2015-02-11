package com.tendersaucer.untitled;

import java.io.File;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Level implements IUpdate, IDraw {

	private static float GRAVITY = -10;
	
	protected float startAngle;
	private It it;
	private World world;
	protected Vector2 startPos;
	protected Vector2 endPos;
	protected LinkedList<DrawLine> drawLines = new LinkedList<DrawLine>();
	
	public Level(File file) {
		world = new World(new Vector2(0, GRAVITY), true); 
	}
	
	// TODO: create constructor for level editor

	@Override
	public boolean update() {
		if(it != null) {
			checkGameState();
		}
		
		for(Body body : getBodies()) {
			Entity entity = getEntityFromBody(body);
			if(entity == null) {
				continue;
			}
			
			if(entity.update()) {
				entity.done();
			}
		}
		
		return false;
	}

	@Override
	public void done() {
	}	
	
	@Override
	public void draw(SpriteBatch spriteBatch) {
		drawEntities(spriteBatch);
		drawDrawLines(spriteBatch);
	}
	
	public void begin() {
		it = (It)Entity.create(It.class, startPos.x, startPos.y);
	}
	
	public Array<Body> getBodies() {
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		return bodies;
	}
	
	public It getIt() {
		return it;
	}

	public World getWorld() {
		return world;
	}
	
	protected void drawEntities(SpriteBatch spriteBatch) {
		for(Body body : getBodies()) {
			Entity entity = getEntityFromBody(body);
			if(entity == null) {
				continue;
			}
			
			entity.draw(spriteBatch);
		}
	}
	
	protected void drawDrawLines(SpriteBatch spriteBatch) {
		for(DrawLine drawLine : drawLines) {
			drawLine.draw(spriteBatch);
		}
	}
	
	protected Entity getEntityFromBody(Body body) {
		BodyData data = (BodyData)body.getUserData();
		if(data == null || data.getEntity() == null) {
			return null;
		}
		
		return ((BodyData)body.getUserData()).getEntity();
	}
	
	protected void checkGameState() {
		float x = it.getX(true);
		float y = it.getY(true);
		float width = it.getSprite().getWidth();
		float height = it.getSprite().getHeight();
		
		if(Utils.getDistance(x, y, endPos.x, endPos.y) < width / 4) {
			Globals.getInstance().setState(State.LevelWon);
		}
		
		if(x < 0 || x + width > Gdx.graphics.getWidth() || 
		   y < 0 || y + height > Gdx.graphics.getHeight()) {
			Globals.getInstance().setState(State.LevelLost);
		}
		
		// TODO: check if it hasn't moved in awhile
	}
}
