package core;

import java.awt.geom.Line2D;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import common.BodyData;
import common.IDraw;
import common.IUpdate;
import entity.Entity;
import entity.EntityFactory;
import entity.TheEntity;
import entity.floating.FloatingEntity;
import entity.floating.LineEntity;

public class Level implements IUpdate, IDraw {

	protected static final float GRAVITY = -10;
	protected static final int MAX_ENTITY_COUNT = 25;
	protected static final int MIN_ENTITY_COUNT = 5;
	protected static final float MIN_ENTITY_SIZE = Gdx.graphics.getWidth() / 10;
	protected static final float MAX_ENTITY_SIZE = Gdx.graphics.getWidth() / 3;
	
	protected float speed = 5;
	protected int entityCount = 0;
	protected Entity floorEntity;
	protected TheEntity theEntity;
	protected World world = new World(new Vector2(0, GRAVITY), true);
	protected LinkedList<LineEntity> lines = new LinkedList<LineEntity>();	
	
	public Level() {
		theEntity = new TheEntity();
		theEntity.setUserData();
	}

	@Override
	public boolean update() {
		tryBuild();
		
		for(Body body : getBodies()) {
			Entity entity = getEntityFromBody(body);
			if(entity == null) {
				continue;
			}
			
			if(entity.update()) {
				entity.done();
				
				if(entity instanceof FloatingEntity && !(entity instanceof LineEntity)) {
					entityCount--;
				}
					
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
	
	protected void tryBuild() {
		if(entityCount >= MIN_ENTITY_COUNT) {
			return;
		}
		
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		
		float levelFloor = Gdx.graphics.getHeight();
		if(entityCount > 0) {
			levelFloor = floorEntity.getY(true) + floorEntity.getHeight(true);
		}
		
		float tryWidth = MathUtils.random(MIN_ENTITY_SIZE, MAX_ENTITY_SIZE);
		float tryHeight;	
		float currX = MathUtils.random(0, screenWidth - MAX_ENTITY_SIZE);
		float currY = levelFloor + MathUtils.random(MIN_ENTITY_SIZE * 2, screenHeight * 0.75f);
		while(entityCount <= MAX_ENTITY_COUNT) {	
			tryHeight = MathUtils.random(MIN_ENTITY_SIZE, MAX_ENTITY_SIZE);
			
			FloatingEntity entity = EntityFactory.getRandomFloatingEntity(currX, currY, tryWidth, tryHeight);
			entity.setUserData();
			
			floorEntity = entity;
			
			float actualWidth = entity.getWidth(true);
			float actualHeight = entity.getHeight(true);		
			if(MathUtils.random() < 0.2f) {
				currY += MathUtils.random(0, actualHeight);
				
				float newX;
				float leftSpace = currX;
				float rightSpace = screenWidth - (currX + actualWidth);				
				if(leftSpace > rightSpace) {
					newX = currX - MathUtils.random(leftSpace / 4, leftSpace);
				} else {
					newX = currX + actualWidth + MathUtils.random(rightSpace / 4, rightSpace);
				}
				
				float space = Math.abs(currX - newX);
				tryWidth = MathUtils.random(space / 4, space / 2);
				
				currX = newX;
			} else {
				tryWidth = MathUtils.random(MIN_ENTITY_SIZE, MAX_ENTITY_SIZE);
				currX = MathUtils.random(0, screenWidth - tryWidth);
				currY += MathUtils.random(actualHeight * 2, screenHeight * 0.75f);
			}
			
			tryWidth = Math.max(MIN_ENTITY_SIZE, tryWidth);
			tryHeight = Math.max(MIN_ENTITY_SIZE, tryHeight);
			
			entityCount++;
		}
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
