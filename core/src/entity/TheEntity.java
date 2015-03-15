package entity;

import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import common.Globals;

public final class TheEntity extends Entity {

	protected static final String SPRITE_FILENAME = "it.png";
	
	protected TheEntity(float x, float y) {
		super();

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x, y);

		World world = Globals.getInstance().getLevel().getWorld();
		body = world.createBody(bodyDef);

		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(10);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circleShape;
		fixtureDef.density = 0.1f;
		fixtureDef.friction = 0.1f;
		fixtureDef.restitution = 0.1f;
		body.createFixture(fixtureDef);

		circleShape.dispose();
	}

	@Override
	public boolean update() {
		if(!isInBounds()) {
			return true;
		}
		
		return false;
	}

	@Override
	public void done() {
	}
	
	private boolean isInBounds() {
		Frustum frustum = Globals.getInstance().getCamera().frustum;
		return frustum.boundsInFrustum(getX(true), getY(true), 0, getWidth(true) / 2, getHeight(true) / 2, 0);
	}
}
