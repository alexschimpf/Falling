package com.tendersaucer.untitled;

import java.io.File;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Level implements IUpdate, IDraw {

	protected float startAngle;
	private It it;
	private World world;
	protected Vector2 startPos;
	protected Vector2 endPos;
	protected LinkedList<DrawLine> drawLines = new LinkedList<DrawLine>();
	
	public Level(File file) {
	}

	@Override
	public boolean update() {
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
}
