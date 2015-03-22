package entity.floating;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import common.Globals;
import common.Textures;
import common.Utils;

public class NeutralEntity extends FloatingEntity {

	protected TextureRegion texture;
	
	public NeutralEntity(float x, float y, float width, float height) {
		super(x, y, width, height);
		texture = Textures.getInstance().getRandomNeutralTexture();	
	}

	public void drawFilledPolygon(PolygonSpriteBatch polygonSpriteBatch) {	
		float[] vertices = Utils.getLocalVertices((PolygonShape)getBody().getFixtureList().get(0).getShape());
		short[] triangles = new short[] { 0, 1, 2, 0, 2, 3 };
		
		PolygonRegion polyReg = new PolygonRegion(texture, vertices, triangles);
		
		PolygonSprite sprite = new PolygonSprite(polyReg);
		sprite.setOrigin(body.getLocalCenter().x, body.getLocalCenter().y);
		sprite.setRotation(MathUtils.radiansToDegrees * body.getAngle());
		sprite.setX(getCenterX());
		sprite.setY(getCenterY());
		sprite.draw(polygonSpriteBatch);
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
		fixtureDef.restitution = 0.3f;
		body.createFixture(fixtureDef);
		
		if(MathUtils.random() < 0.3f) {
			float angularVelocity = MathUtils.random(0.3f, 0.7f);
			body.setAngularVelocity(angularVelocity);
		}

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
