package entity.floating;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import common.BodyEditorLoader;
import common.Globals;
import common.Textures;
import common.Utils;

public class NeutralEntity extends FloatingEntity {

	protected Vector2 origin;
	protected TextureRegion texture;
	protected Sprite sprite;
	
	public NeutralEntity(float x, float y, float width, float height) {
		super();
		
		sprite = new Sprite(Textures.getInstance().getRockTexture());
		this.width = width * 2;
		this.height = this.width * (sprite.getHeight() / sprite.getWidth());
			
		sprite.setSize(this.width, this.height);
		sprite.flip(false, true);
		
		buildBody(x, y);
	}

	public void drawFilledPolygon(PolygonSpriteBatch polygonSpriteBatch) {	
//		float[] vertices = Utils.getLocalVertices((PolygonShape)getBody().getFixtureList().get(0).getShape());
//		short[] triangles = new short[] { 0, 1, 2, 0, 2, 3 };
//		
//		PolygonRegion polyReg = new PolygonRegion(texture, vertices, triangles);
//		
//		PolygonSprite sprite = new PolygonSprite(polyReg);
//		sprite.setOrigin(body.getLocalCenter().x, body.getLocalCenter().y);
//		sprite.setRotation(MathUtils.radiansToDegrees * body.getAngle());
//		sprite.setX(getCenterX());
//		sprite.setY(getCenterY());
//		sprite.draw(polygonSpriteBatch);
	}
	
	public void draw(SpriteBatch batch) {
		Vector2 pos = body.getPosition().sub(origin);	 
	    sprite.setPosition(pos.x, pos.y);
	    sprite.setOrigin(origin.x, origin.y);
	    sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
	    sprite.draw(batch);
	}
	
	@Override 
	protected void buildBody(float x, float y) {		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.KinematicBody;
		bodyDef.position.set(x, y);

		World world = Globals.getInstance().getLevel().getWorld();
		body = world.createBody(bodyDef);

//		final float[] vertices = getRandomVertices(body, x, y);
//		PolygonShape shape = new PolygonShape();
//		shape.set(vertices);

		FixtureDef fixtureDef = new FixtureDef();
		//fixtureDef.shape = shape;
		fixtureDef.density = 1;
		fixtureDef.friction = 0.1f;
		fixtureDef.restitution = 0.3f;
		//body.createFixture(fixtureDef);
		
		Textures.getInstance().getLoader().attachFixture(body, "space_rock", fixtureDef, width);
		origin = Textures.getInstance().getLoader().getOrigin("space_rock", width).cpy();

		if(MathUtils.random() < 0.3f) {
			float angularVelocity = MathUtils.random(0.3f, 0.7f);
			body.setAngularVelocity(angularVelocity);
		}

//		shape.dispose();
	}
	
//	protected float[] getRandomVertices(Body body, float x, float y) {
//		PolygonShape shape = new PolygonShape();
//		shape.setAsBox(width / 2, height / 2);
//		
//		float[] vertices = Utils.getLocalVertices(shape);
//		for(int i = 0; i < vertices.length; i++) {
//			vertices[i] += MathUtils.random(0, 2);
//		}
//		
//		return vertices;
//	}
}
