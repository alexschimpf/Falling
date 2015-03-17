package entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import common.BodyData;
import common.Globals;
import common.IDraw;
import common.IUpdate;
import common.Utils;

public abstract class Entity implements IUpdate, IDraw {
	
	protected float x, y;
	protected float width, height;
	protected Body body;

	public Entity() {		
	}
	
	protected abstract void buildBody();
	
	@Override
	public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
		shapeRenderer.setColor(Color.GREEN);
		shapeRenderer.box(x, y, 0, width, height, 0);
	}
	
	@Override
	public boolean update() {
		return false;
	}
	
	@Override
	public void done() {		
		Globals.getInstance().getLevel().getWorld().destroyBody(body);
	}
	
	public void setUserData() {
		BodyData bodyData = new BodyData(this);
		body.setUserData(bodyData);
	}
	
	public float getX(boolean usePixels) {
		if(usePixels) {
			return x;
		} 
		
		return body.getPosition().x;
	}
	
	public float getY(boolean usePixels) {
		if(usePixels) {
			return y;
		} 
		
		return body.getPosition().y;
	}
	
	public float getWidth(boolean usePixels) {
		if(usePixels) {
			return width;
		} 
		
		return Utils.convertToMeters(width);
	}
	
	public float getHeight(boolean usePixels) {
		if(usePixels) {
			return height;
		} 
		
		return Utils.convertToMeters(height);
	}
	
	public Body getBody() {
		return body;
	}
}
