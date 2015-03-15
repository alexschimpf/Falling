package entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;

import common.BodyData;
import common.IDraw;
import common.IUpdate;
import common.Utils;

public abstract class Entity implements IUpdate, IDraw {
	
	protected Sprite sprite;
	protected Body body;

	public Entity() {		
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
		sprite.draw(spriteBatch);
	}
	
	@Override
	public boolean update() {
		float x = Utils.convertToPixels(body.getPosition().x);
		float y = Utils.convertToPixels(body.getPosition().y);
		sprite.setPosition(x, y);
		sprite.setRotation(MathUtils.radiansToDegrees * body.getAngle());
		
		return false;
	}
	
	@Override
	public void done() {		
	}
	
	public void setUserData() {
		BodyData bodyData = new BodyData(this);
		body.setUserData(bodyData);
	}
	
	public float getX(boolean usePixels) {
		if(usePixels) {
			return sprite.getX();
		} 
		
		return body.getPosition().x;
	}
	
	public float getY(boolean usePixels) {
		if(usePixels) {
			return sprite.getY();
		} 
		
		return body.getPosition().y;
	}
	
	public float getWidth(boolean usePixels) {
		float width = sprite.getWidth();
		if(usePixels) {
			return width;
		} 
		
		return Utils.convertToMeters(width);
	}
	
	public float getHeight(boolean usePixels) {
		float height = sprite.getHeight();
		if(usePixels) {
			return height;
		} 
		
		return Utils.convertToMeters(height);
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public Body getBody() {
		return body;
	}
}
