package entity.floating;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import common.Globals;
import common.Utils;

public class NeutralEntity extends FloatingEntity {

	public NeutralEntity(float x, float y, float width, float height) {
		super(x, y, width, height);
		
		setStraightVelocity();
		
		buildBody();
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
		shapeRenderer.box(x, y, 0, width, height, 0);
	}
	
	@Override
	public boolean update() {
		x = Utils.convertToPixels(body.getPosition().x);
		y = Utils.convertToPixels(body.getPosition().y);
		
		return super.update();
	}
	
	@Override 
	protected void buildBody() {
		float x = Utils.convertToMeters(this.x);
		float y = Utils.convertToMeters(this.y);
		float width = Utils.convertToMeters(this.width);
		float height = Utils.convertToMeters(this.height);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.KinematicBody;
		bodyDef.position.set(x, y);

		World world = Globals.getInstance().getLevel().getWorld();
		body = world.createBody(bodyDef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2, height / 2);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.friction = 0.1f;
		fixtureDef.restitution = 0.1f;
		body.createFixture(fixtureDef);

		shape.dispose();
	}
}
