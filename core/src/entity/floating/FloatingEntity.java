package entity.floating;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

import common.Globals;
import entity.Entity;

public abstract class FloatingEntity extends Entity {

	protected float vx;
	protected float vy;
	
	public FloatingEntity(float x, float y, float width, float height) {
		super();
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public FloatingEntity() {
		super();
	}
	
	@Override
	public boolean update() {
		body.setLinearVelocity(vx, -vy);
		
		return super.update() || getY(true) + getHeight(true) <= 0;
	}
	
	@Override
	protected void buildBody() {
	}
	
	protected void setStraightVelocity() {
		vx = 0;
		vy = Globals.getInstance().getLevel().getSpeed();
	}
	
	protected void setRandomVelocity() {
		float speed = Globals.getInstance().getLevel().getSpeed();
	    float targetX = MathUtils.random(0, Gdx.graphics.getWidth() - width);
	    float targetY = -height;
	    float angle = MathUtils.atan2(targetY, targetX);
	    vx = speed * MathUtils.cos(angle);
	    vy = Math.abs(speed * MathUtils.sin(angle));
	}
}
