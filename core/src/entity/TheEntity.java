package entity;

import com.badlogic.gdx.Gdx;
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
import common.Utils;

public final class TheEntity extends Entity {
	
	private static final float DEFAULT_WIDTH = Gdx.graphics.getWidth() / 40;
	private static final float DEFAULT_HEIGHT = DEFAULT_WIDTH;
	
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
		
		x = Utils.convertToPixels(body.getPosition().x);
		y = Utils.convertToPixels(body.getPosition().y);
		
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
		shapeRenderer.setColor(Color.BLUE);
		shapeRenderer.circle(x, y, width);
	}
	
	@Override
	protected void buildBody() {
		float x = Utils.convertToMeters(this.x);
		float y = Utils.convertToMeters(this.y);
		float width = Utils.convertToMeters(this.width);
		
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
		float left = x - (width / 2);
		float right = left + width;
		float top = y - (height / 2);
		float bottom = top + height;
		
		return right >= 0 && left <= Gdx.graphics.getWidth() && 
			   top <= Gdx.graphics.getHeight() && bottom >= 0;
	}
}
