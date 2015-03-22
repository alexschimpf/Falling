package entity.floating.collectable.modifier;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.TimeUtils;

import common.Globals;
import background.Background;
import entity.floating.collectable.CollectableEntity;

public abstract class ModifierEntity extends CollectableEntity {

	protected static final float DEFAULT_DURATION = 10000;
	
	protected Long startTime;
	protected float duration = DEFAULT_DURATION;
	
	public ModifierEntity(float x, float y) {
		super();
		
		width = Globals.VIEWPORT_WIDTH / 20;
		height = Globals.VIEWPORT_WIDTH / 20;
		
		buildBody(x, y);
	}
	
	public boolean modify() {
		if(startTime == null) {
			startTime = TimeUtils.millis();
		}
		
		return TimeUtils.timeSinceMillis(startTime) > duration;
	}
	
	public void modifyDone() {
	}
	
	@Override
	public void collect() {
		globals.getGame().setModifier(this);
	}
	
	@Override
	protected void buildBody(float x, float y) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
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
