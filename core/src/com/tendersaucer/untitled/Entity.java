package com.tendersaucer.untitled;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class Entity implements IUpdate {
	
	protected Sprite sprite;
	protected Body body;
	
	public Entity(float x, float y) {
		
	}
	
	public Sprite getSprite() {
		return this.sprite;
	}
	
	public Body getBody() {
		return this.body;
	}
}
