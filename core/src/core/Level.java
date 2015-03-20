package core;

import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;

import common.BodyData;
import common.CollisionListener;
import common.Globals;
import common.IUpdate;
import common.Line;
import common.Utils;
import entity.Entity;
import entity.EntityFactory;
import entity.TheEntity;
import entity.floating.FatalEntity;
import entity.floating.FloatingEntity;
import entity.floating.LineEntity;
import entity.floating.NeutralEntity;

public class Level implements IUpdate {

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
			
			if(entity == null || body.getUserData() == null) {
				continue;
			}
			
			BodyData bodyData = (BodyData)body.getUserData();
			if(bodyData.needsRemoved()) {
				bodyIter.remove();
				entity.done();
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
	
	public void drawFatals(ShapeRenderer shapeRenderer) {
		Iterator<Body> bodyIter = getBodies().iterator();
		while(bodyIter.hasNext()) {
			Body body = bodyIter.next();
			Entity entity = getEntityFromBody(body);
			
			if(entity == null || body.getUserData() == null) {
				continue;
			}
			
			if(!(entity instanceof FatalEntity)) {
				continue;
			}
			
			((FatalEntity)entity).draw(shapeRenderer);
		}
	}
	
	public void drawFilledPolygons(PolygonSpriteBatch polygonSpriteBatch) {
		Iterator<Body> bodyIter = getBodies().iterator();
		while(bodyIter.hasNext()) {
			Body body = bodyIter.next();
			Entity entity = getEntityFromBody(body);
			
			if(entity == null || body.getUserData() == null) {
				continue;
			}
			
			if(!(entity instanceof NeutralEntity) || entity instanceof FatalEntity) {
				continue;
			}
			
			((NeutralEntity)entity).drawFilledPolygon(polygonSpriteBatch);
		}
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
			levelFloor = floorEntity.getBottom();
		}
		
		float tryWidth;
		float tryHeight;	
		float currX = MathUtils.random(0, screenWidth - MAX_ENTITY_SIZE);
		float currY = levelFloor + MathUtils.random(MAX_ENTITY_SIZE * 2, screenHeight * 0.5f);
		FloatingEntity entity = null;
		while(entityCount <= MAX_ENTITY_COUNT) {
			tryWidth = MathUtils.random(MIN_ENTITY_SIZE, MAX_ENTITY_SIZE);
			tryHeight = MathUtils.random(MIN_ENTITY_SIZE, MAX_ENTITY_SIZE);
			
			entity = EntityFactory.getRandomFloatingEntity(currX, currY, tryWidth, tryHeight);
			entityCount++;

			floorEntity = entity;
			
			float prevY = currY;
			currX = MathUtils.random(MIN_ENTITY_SIZE, screenWidth - MIN_ENTITY_SIZE);
			currY += MathUtils.random(MAX_ENTITY_SIZE, screenHeight * 0.5f);
			
			if(MathUtils.random() < 0.5f) {
				if(currY - prevY > MAX_ENTITY_SIZE) {
					currY -= MIN_ENTITY_SIZE;
				}
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
