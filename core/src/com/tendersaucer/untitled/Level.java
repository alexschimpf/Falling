package com.tendersaucer.untitled;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public class Level implements IUpdate, IDraw {

	protected static final float GRAVITY = -10;
	protected static final float MAX_START_SPEED = 10;
	
	protected float startAngle;
	protected It it;
	protected World world;
	protected Vector2 startPos;
	protected Vector2 endPos;
	protected LinkedList<DrawLine> drawLines = new LinkedList<DrawLine>();
	
	public Level(FileHandle file) {
		world = new World(new Vector2(0, GRAVITY), true); 	
		buildLevelFromFile(file);
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

	public void begin(float power) {
		it = new It(startPos.x, startPos.y);
		it.start(power * MAX_START_SPEED, startAngle);
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

	protected void buildLevelFromFile(FileHandle file) {
		try {
    		XmlReader reader = new XmlReader();
    		Element root = reader.parse(file).getChildByName("level");
    		startAngle = root.getFloat("startAngle");
    		startPos = new Vector2(root.getInt("startX"), root.getInt("startY"));
    		endPos = new Vector2(root.getInt("endX"), root.getInt("endY"));
    		
    		Element entitiesRoot = root.getChildByName("entities");
    		for(Element entity : entitiesRoot.getChildrenByName("entity")) {
    			String objClass = entity.get("class");
    			int x = entity.getInt("x");
    			int y = entity.getInt("y");
    			int width = entity.getInt("width");
    			int height = entity.getInt("height");
    			
    		}
		} catch(Exception e) {
			Gdx.app.error("tendersaucer.level", "Error building level from file!", e);
		}
	}
}
