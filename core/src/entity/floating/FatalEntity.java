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

public class FatalEntity extends NeutralEntity {

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
}
