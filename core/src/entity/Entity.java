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

	public Entity() {		
	}
	
	protected abstract void buildBody(float x, float y);
	
	@Override
	public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
		//shapeRenderer.setColor(Color.GREEN);
		//shapeRenderer.box(x, y, 0, width, height, 0);
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
