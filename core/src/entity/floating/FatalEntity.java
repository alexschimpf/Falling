package entity.floating;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import common.Globals;
import common.Utils;

public class FatalEntity extends FloatingEntity {

	public FatalEntity(float x, float y, float width, float height) {
		super(x, y, width, height);
		
		setStraightVelocity();
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
		shapeRenderer.setColor(Color.RED);
		PolygonShape shape = (PolygonShape)body.getFixtureList().get(0).getShape();
		float[] vertices = Utils.getWorldVertices(body, shape);
		shapeRenderer.polygon(vertices);
	}
	
	@Override 
	protected void buildBody(float x, float y) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.KinematicBody;
		bodyDef.position.set(x, y);

		World world = Globals.getInstance().getLevel().getWorld();
		body = world.createBody(bodyDef);

		final float[] vertices = getRandomVertices(body, x, y);
		PolygonShape shape = new PolygonShape();
		shape.set(vertices);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.friction = 0.1f;
		fixtureDef.restitution = 0.1f;
		body.createFixture(fixtureDef);

		shape.dispose();
	}
	
	protected float[] getRandomVertices(Body body, float x, float y) {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2, height / 2);
		
		float[] vertices = Utils.getLocalVertices(shape);
		for(int i = 0; i < vertices.length; i++) {
			vertices[i] += MathUtils.random(0, 2);
		}
		
		return vertices;
	}
}
