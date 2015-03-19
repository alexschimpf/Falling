package entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;

import common.BodyData;
import common.Globals;
import common.IDraw;
import common.IUpdate;

public abstract class Entity implements IUpdate, IDraw {
	
	protected float width, height;
	protected Body body;
	protected Globals globals = Globals.getInstance();

	public Entity() {		
	}
	
	protected abstract void buildBody(float x, float y);
	
	@Override
	public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
	}
	
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
	
	public float getX() {
		return body.getPosition().x;
	}
	
	public float getY() {	
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
