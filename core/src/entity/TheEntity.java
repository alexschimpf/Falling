package entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import common.Globals;
import common.State;
import common.Utils;

public final class TheEntity extends Entity {
	
	private static final float DEFAULT_RADIUS = Globals.VIEWPORT_WIDTH / 40;
	
	public TheEntity() {
		super();
		
		width = DEFAULT_RADIUS * 2;
		height = DEFAULT_RADIUS * 2;	
		
		float x = MathUtils.random(width, Globals.VIEWPORT_WIDTH - (width * 2));
		float y = MathUtils.random(height, (Globals.VIEWPORT_HEIGHT / 3) - height);
		buildBody(x, y);
	}

	@Override
	public boolean update() {
		super.update();

		if(getBottom() < Utils.getCameraTop()) {
			return true;
		}
		
		return false;
	}

	@Override
	public void done() {
		globals.setState(State.GameOver);
	}

	@Override
	protected void buildBody(float x, float y) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x, y);

		World world = globals.getLevel().getWorld();
		body = world.createBody(bodyDef);

		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(width / 2);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circleShape;
		fixtureDef.density = 0.1f;
		fixtureDef.friction = 0.1f;
		fixtureDef.restitution = 0.1f;
		body.createFixture(fixtureDef);

		circleShape.dispose();
	}
}
