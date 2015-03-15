package entity.floating;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import common.Globals;

import entity.Entity;

public abstract class FloatingEntity extends Entity {

	protected float vx;
	protected float vy;
	
	public FloatingEntity() {
		super();
	}
	
	@Override
	public boolean update() {
		body.setLinearVelocity(vx, vy);
		
		return getY(true) + getHeight(true) <= 0;
	}
	
	@Override 
	public void done() {
		
	}
	
	protected void setStraightVelocity() {
		vx = 0;
		vy = Globals.getInstance().getLevel().getSpeed();
	}
	
	protected void setRandomVelocity() {
		float speed = Globals.getInstance().getLevel().getSpeed();
	    float targetX = MathUtils.random() * Gdx.graphics.getWidth();
	    float targetY = MathUtils.random() * Gdx.graphics.getHeight();
	    float angle = MathUtils.atan2(targetY, targetX);
	    vx = speed * MathUtils.cos(angle);
	    vy = speed * MathUtils.sin(angle);
	}
}
