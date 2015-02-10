package com.tendersaucer.untitled;

import java.lang.reflect.Constructor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class Entity implements IUpdate, IDraw {
	
	protected Sprite sprite;
	protected Body body;
	
	protected Entity(float x, float y) {		
	}
	
	public static Entity create(Class<?> classObj, float x, float y) {
		Entity entity = null;
		try {
			Constructor<?> constructor = classObj.getConstructor(Float.class, Float.class);
			entity = (Entity)constructor.newInstance(new Object[] { x, y });
			entity.setUserData();
		} catch(Exception e) {
			Gdx.app.error("entity", "Error constructing entity from class!", e);
		}
		
		return entity;
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch) {
		sprite.draw(spriteBatch);
	}
	
	@Override
	public boolean update() {
		float x = this.body.getPosition().x;
		float y = this.body.getPosition().y;
		this.sprite.setPosition(x, y);
		this.sprite.setRotation(MathUtils.radiansToDegrees * this.body.getAngle());
		
		return false;
	}
	
	@Override
	public void done() {		
	}
	
	public void setUserData() {
		BodyData bodyData = new BodyData(this);
		this.body.setUserData(bodyData);
	}
	
	public Sprite getSprite() {
		return this.sprite;
	}
	
	public Body getBody() {
		return this.body;
	}
}
