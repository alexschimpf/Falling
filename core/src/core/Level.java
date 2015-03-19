package core;

import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;

import common.BodyData;
import common.CollisionListener;
import common.Globals;
import common.IDraw;
import common.IUpdate;
import common.Line;
import common.Utils;
import entity.Entity;
import entity.EntityFactory;
import entity.TheEntity;
import entity.floating.FloatingEntity;
import entity.floating.LineEntity;

public class Level implements IUpdate, IDraw {

	protected static final float GRAVITY = 8;
	protected static final float DEFAULT_SPEED = 0.28f;
	protected static final int MAX_ENTITY_COUNT = 25;
	protected static final int MIN_ENTITY_COUNT = 5;
	protected static final float MIN_ENTITY_SIZE = Globals.VIEWPORT_WIDTH / 18;
	protected static final float MAX_ENTITY_SIZE = Globals.VIEWPORT_WIDTH / 3;
	
	protected float speed = DEFAULT_SPEED;
	protected int entityCount = 0;
	protected Body leftWall;
	protected Body rightWall;
	protected Entity floorEntity;
	protected TheEntity theEntity;
	protected World world = new World(new Vector2(0, GRAVITY), true);
	protected LinkedList<LineEntity> lines = new LinkedList<LineEntity>();	
	
	public Level() {
		world.setContactListener(new CollisionListener());
		
		leftWall = buildSideWallBody(true);
		rightWall = buildSideWallBody(false);
	}

	@Override
	public boolean update() {
		leftWall.setTransform(leftWall.getPosition().x, Utils.getCameraTop(), leftWall.getAngle());
		rightWall.setTransform(rightWall.getPosition().x, Utils.getCameraTop(), rightWall.getAngle());
		
		world.step(1 / 45.0f, 5, 5);
		
		tryBuild();
		
		Iterator<Body> bodyIter = getBodies().iterator();
		while(bodyIter.hasNext()) {
			Body body = bodyIter.next();
			Entity entity = getEntityFromBody(body);
			if(entity == null) {
				continue;
			}
			
			if(entity.update()) {
				entity.done();
				
				if(entity instanceof FloatingEntity && !(entity instanceof LineEntity)) {
					entityCount--;
				} else if(entity instanceof LineEntity) {
					lines.remove((LineEntity)entity);
				}
				
				bodyIter.remove();
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
	}
	
	public void reset() {
		world.dispose();
		lines.clear();
		entityCount = 0;
		speed = DEFAULT_SPEED;
		
		Globals.getInstance().resetCamera();
		
		world = new World(new Vector2(0, GRAVITY), true);
		world.setContactListener(new CollisionListener());
		
		leftWall = buildSideWallBody(true);
		rightWall = buildSideWallBody(false);
		
		floorEntity = null;
		initTheEntity();
	}
	
	public void initTheEntity() {
		theEntity = new TheEntity();
		theEntity.setUserData();
		theEntity.getBody().setLinearVelocity(0, 10);
	}
	
	public void addLine(LineEntity line) {
		lines.add(line);
	}
	
	public boolean isLineValid(Line line) {
		Iterator<Body> bodyIter = getBodies().iterator();
		while(bodyIter.hasNext()) {
			Body body = bodyIter.next();
			Entity entity = getEntityFromBody(body);
			if(entity == null) {
				continue;
			}
			
			if(!(entity instanceof LineEntity) && line.intersectsEntity(entity)) {
				return false;
			}
		}
		
		return true;
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
		
		float screenWidth = Globals.VIEWPORT_WIDTH;
		float screenHeight = Globals.VIEWPORT_HEIGHT;
		
		float levelFloor = MathUtils.random(screenHeight / 2, screenHeight - MAX_ENTITY_SIZE);
		if(entityCount > 0) {
			levelFloor = floorEntity.getY() + floorEntity.getHeight();
		}
		
		boolean lastPlacedClose = false;
		float tryWidth = MathUtils.random(MIN_ENTITY_SIZE, MAX_ENTITY_SIZE);
		float tryHeight;	
		float currX = MathUtils.random(0, screenWidth - MAX_ENTITY_SIZE);
		float currY = levelFloor + MathUtils.random(MAX_ENTITY_SIZE * 2, screenHeight * 0.5f);
		FloatingEntity entity = null;
		while(entityCount <= MAX_ENTITY_COUNT) {	
			tryHeight = MathUtils.random(MIN_ENTITY_SIZE, MAX_ENTITY_SIZE);
			
			entity = EntityFactory.getRandomFloatingEntity(currX, currY, tryWidth, tryHeight);
			entity.setUserData();
			entityCount++;

			floorEntity = entity;
			
			float actualWidth = entity.getWidth();
			float actualHeight = entity.getHeight();	
	
			if(!lastPlacedClose && MathUtils.random() < 0.4f) {
				lastPlacedClose = true;
				currY += MathUtils.random(actualHeight, actualHeight * 2);
				
				float newX;
				float leftSpace = currX;
				float rightSpace = screenWidth - (currX + actualWidth);				
				if(leftSpace > rightSpace) {
					newX = MathUtils.random(MAX_ENTITY_SIZE, leftSpace / 2);
				} else {
					newX = Globals.VIEWPORT_WIDTH - MathUtils.random(MAX_ENTITY_SIZE, rightSpace / 2);
				}
				
				float space = Math.abs(currX - newX);
				tryWidth = MathUtils.random(space / 4, space / 2);
				
				currX = Math.max(newX, MIN_ENTITY_SIZE);
			} else {
				lastPlacedClose = false;
				tryWidth = MathUtils.random(MIN_ENTITY_SIZE, MAX_ENTITY_SIZE);
				currX = MathUtils.random(MIN_ENTITY_SIZE, screenWidth - tryWidth);
				currY += MathUtils.random(actualHeight + MIN_ENTITY_SIZE, screenHeight * 0.5f);
			}
			
			tryWidth = Math.max(MIN_ENTITY_SIZE, tryWidth);
			tryHeight = Math.max(MIN_ENTITY_SIZE, tryHeight);
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
	
	protected Entity getEntityFromBody(Body body) {
		BodyData data = (BodyData)body.getUserData();
		if(data == null || data.getEntity() == null) {
			return null;
		}
		
		return ((BodyData)body.getUserData()).getEntity();
	}

	protected Body buildSideWallBody(boolean left) {
		float x = left ? -5.5f : Globals.VIEWPORT_WIDTH + 5.5f; 
	
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(x, 0);

		Body body = world.createBody(bodyDef);
		body.setBullet(true);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(5, Globals.VIEWPORT_HEIGHT);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0;
		fixtureDef.friction = 0;
		fixtureDef.restitution = 0.5f;
		body.createFixture(fixtureDef);

		return body;
	}
}
