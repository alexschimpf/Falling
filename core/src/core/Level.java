package core;

import java.awt.geom.Line2D;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import common.BodyData;
import common.IDraw;
import common.IUpdate;
import entity.Entity;
import entity.TheEntity;
import entity.floating.LineEntity;

public class Level implements IUpdate, IDraw {

	protected static final float GRAVITY = -10;
	
	protected float speed = 5;
	protected float startAngle;
	protected TheEntity theEntity;
	protected World world;
	protected Vector2 startPos;
	protected Vector2 endPos;
	protected LinkedList<LineEntity> lines = new LinkedList<LineEntity>();
	
	public Level() {
		world = new World(new Vector2(0, GRAVITY), true); 	
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
	public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
		drawEntities(spriteBatch, shapeRenderer);
		drawLines(spriteBatch, shapeRenderer);
	}
	
	public void addLine(LineEntity line) {
		lines.add(line);
	}
	
	public boolean lineIntersectsExisting(Line2D line) {
		for(LineEntity lineEntity : lines) {
			if(lineEntity.intersectsLine(line)) {
				return true;
			}
		}
		
		return false;
	}
	
	public Array<Body> getBodies() {
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		return bodies;
	}
	
	public TheEntity getTheEntity() {
		return theEntity;
	}

	public World getWorld() {
		return world;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	protected void drawEntities(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
		for(Body body : getBodies()) {
			Entity entity = getEntityFromBody(body);
			if(entity != null) {
				entity.draw(spriteBatch, shapeRenderer);
			}
		}
	}
	
	protected void drawLines(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
		for(LineEntity line : lines) {
			line.draw(spriteBatch, shapeRenderer);
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
