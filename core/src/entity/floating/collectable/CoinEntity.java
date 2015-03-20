package entity.floating.collectable;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import common.Globals;

public class CoinEntity extends CollectableEntity {
	
	protected static final float DEFAULT_WIDTH = Globals.VIEWPORT_WIDTH / 35;
	
	protected int value;
	
	public CoinEntity(float x, float y) {
		super();
		
		value = MathUtils.random(1, 3);
		
		width = value * DEFAULT_WIDTH;
		height = value * DEFAULT_WIDTH;

		buildBody(x, y);
	}
	
	@Override
	public void collect() {
		globals.getGame().addBits(value);
	}
	
	public int getValue() {
		return value;
	}
	
	@Override
	protected void buildBody(float x, float y) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.KinematicBody;
		bodyDef.position.set(x, y);

		World world = globals.getLevel().getWorld();
		body = world.createBody(bodyDef);

		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(width);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circleShape;
		fixtureDef.density = 0.1f;
		fixtureDef.friction = 0.1f;
		fixtureDef.restitution = 0.1f;
		body.createFixture(fixtureDef);

		circleShape.dispose();
	}
}
