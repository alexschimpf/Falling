package entity.floating;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import common.Utils;

public class FatalEntity extends NeutralEntity {

	public FatalEntity(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	public void draw(ShapeRenderer shapeRenderer) {
		shapeRenderer.setColor(Color.RED);
		PolygonShape shape = (PolygonShape)body.getFixtureList().get(0).getShape();
		float[] vertices = Utils.getWorldVertices(body, shape);
		shapeRenderer.polygon(vertices);
	}
}
