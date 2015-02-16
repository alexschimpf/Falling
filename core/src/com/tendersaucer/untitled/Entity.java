package com.tendersaucer.untitled;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class Entity implements IUpdate, IDraw {
	
	protected Sprite sprite;
	protected Body body;

	protected Entity(String filename, float x, float y, float width, float height) {		
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch) {
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
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public Body getBody() {
		return body;
	}
}
