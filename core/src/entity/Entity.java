package entity;

import com.badlogic.gdx.physics.box2d.Body;

import common.BodyData;
import common.Globals;
import common.IUpdate;

public abstract class Entity implements IUpdate {
	
	protected float width, height;
	protected Body body;
	protected Globals globals = Globals.getInstance();

	public Entity() {		
	}
	
	protected abstract void buildBody(float x, float y);
	
	@Override
	public boolean update() {
		float vx = body.getLinearVelocity().x;
		float vy = body.getLinearVelocity().y;
		body.setLinearVelocity(vx, Math.min(vy, 25));
		
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
	
	public float getLeft() {
		return getCenterX() - (width / 2);
	}
	
	public float getRight() {
		return getCenterX() + (width / 2);
	}
	
	public float getTop() {
		return getCenterY() - (height / 2);
	}
	
	public float getBottom() {
		return getCenterY() + (height / 2);
	}
	
	public float getCenterX() {
		return body.getPosition().x;
	}
	
	public float getCenterY() {	
		return body.getPosition().y;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public Body getBody() {
		return body;
	}
}
