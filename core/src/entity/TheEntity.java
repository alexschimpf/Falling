package entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import common.Globals;

public final class TheEntity extends Entity {
	
	private static final float DEFAULT_WIDTH = 10;
	private static final float DEFAULT_HEIGHT = 10;
	
	public TheEntity() {
		super();
		
		width = DEFAULT_WIDTH;
		height = DEFAULT_HEIGHT;	
		x = MathUtils.random(width, Gdx.graphics.getWidth() - (width * 2));
		y = MathUtils.random(height, (Gdx.graphics.getHeight() / 2) - height);
		
		buildBody();
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
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
		shapeRenderer.circle(x, y, width);
	}
	
	@Override
	protected void buildBody() {
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
		Frustum frustum = Globals.getInstance().getCamera().frustum;
		return frustum.boundsInFrustum(getX(true), getY(true), 0, getWidth(true) / 2, getHeight(true) / 2, 0);
	}
}
