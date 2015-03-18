package entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import common.Globals;
import common.State;

public final class TheEntity extends Entity {
	
	private static final float DEFAULT_WIDTH = Globals.VIEWPORT_WIDTH / 40;
	private static final float DEFAULT_HEIGHT = DEFAULT_WIDTH;
	
	public TheEntity() {
		super();
		
		width = DEFAULT_WIDTH;
		height = DEFAULT_HEIGHT;	
		
		float x = MathUtils.random(width, Globals.VIEWPORT_WIDTH - (width * 2));
		float y = MathUtils.random(height, (Globals.VIEWPORT_HEIGHT / 3) - height);
		buildBody(x, y);
	}

	@Override
	public boolean update() {
		super.update();

		if(!isInBounds()) {
			return true;
		}
		
		return false;
	}

	@Override
	public void done() {
		Globals.getInstance().setState(State.GameOver);
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
//		shapeRenderer.setColor(Color.GREEN);
//		shapeRenderer.circle(getX(), getY(), width);
	}
	
	@Override
	protected void buildBody(float x, float y) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x, y);

		World world = Globals.getInstance().getLevel().getWorld();
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
	
	private boolean isInBounds() {
		float left = getX() - (width / 2);
		float right = left + width;
		float top = getY() - (height / 2);
		float bottom = top + height;
		
		return right >= 0 && left <= Globals.VIEWPORT_WIDTH && 
			   top <= Globals.VIEWPORT_HEIGHT && bottom >= 0;
	}
}
