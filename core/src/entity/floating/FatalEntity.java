package entity.floating;

import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import common.Textures;
import common.Utils;

public class FatalEntity extends NeutralEntity {
	
	protected Timer timer;
	
	public FatalEntity(float x, float y, float width, float height) {
		super(x, y, width, height);
		
		texture = Textures.getInstance().getColorTexture("red");
		
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				TextureRegion dark = Textures.getInstance().getColorTexture("red");
				TextureRegion light = Textures.getInstance().getColorTexture("lightRed");
				
				if(texture.equals(light)) {
					texture = dark;
				} else {
					texture = light;
				}
				
			}	
		}, MathUtils.random(0, 1000), 300);
	}
	
	public void draw(ShapeRenderer shapeRenderer) {
		shapeRenderer.setColor(Color.RED);
		PolygonShape shape = (PolygonShape)body.getFixtureList().get(0).getShape();
		float[] vertices = Utils.getWorldVertices(body, shape);
		shapeRenderer.polygon(vertices);
	}
	
	@Override
	public void done() {
		super.done();
		
		timer.cancel();
	}
}
