package entity.floating;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

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
		super.update();
		
		body.setLinearVelocity(vx, vy);
		
		return getY(true) + getHeight(true) <= 0;
	}
	
	@Override 
	public void done() {
		
	}
	
	// TODO: REMOVE THIS!
	@Override
	protected void buildBody() {
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
